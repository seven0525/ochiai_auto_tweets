package controllers.tweet

import application.common.TwitterSession
import application.tweet.TweetUseCase
import domain.tweet.Tweet
import javax.inject.Inject
import play.api.cache.SyncCacheApi
import play.api.data.Forms._
import play.api.data._
import play.api.mvc.{AbstractController, ControllerComponents}
import play.filters.csrf.{CSRFAddToken, CSRFCheck}

class TweetController @Inject()(cc: ControllerComponents, tweetUseCase: TweetUseCase, cacheApi: SyncCacheApi, addToken: CSRFAddToken, checkToken: CSRFCheck) extends AbstractController(controllerComponents = cc){
  val newTweetForm = Form(
    mapping(
      "twitterId" -> text
    )(NewTweetForm.apply)(NewTweetForm.unapply)
  )

  val sendTweetForm = Form(
    mapping(
      "tweetText" -> text
    )(SendTweetForm.apply)(SendTweetForm.unapply)
  )

  def show = checkToken{
    Action { implicit request =>
      val twitterSession: Option[TwitterSession] = cacheApi.get("twitter_user")
      twitterSession match {
        case Some(twitterSession) => {
          val newTweetData = newTweetForm.bindFromRequest().get
          val tweet: Tweet = tweetUseCase.generateTweet(twitterSession.getToken(), twitterSession.getSecret(), newTweetData.twitterId)
          Ok(views.html.new_tweet(tweetText = tweet.getBody(), message = ""))
        }
        case _ => Redirect("/")
      }
    }
  }

  def post = Action{ implicit request =>
    val tSession: Option[TwitterSession] =  cacheApi.get("twitter_user")
    tSession match {
      case Some(session) => {
        val sendTweetData = sendTweetForm.bindFromRequest().get
        tweetUseCase.postTweet(session.getToken(), session.getSecret(), sendTweetData.tweetText)
      }
      case _ => Redirect("/")
    }
    Redirect("/")
  }
}
