package api

import application.common.TwitterSession
import application.tweet.TweetUseCase
import javax.inject.Inject
import play.api.cache.SyncCacheApi
import play.api.data.Forms._
import play.api.data._
import play.api.libs
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class TweetController @Inject()(cc: ControllerComponents, tweetUseCase: TweetUseCase, cacheApi: SyncCacheApi)(implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents = cc){
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

  def show = Action.async { implicit request =>
      val twitterSession: Option[TwitterSession] = cacheApi.get("twitter_user")
    case class TweetJsonResponse(tweetText: String, status: Int)
      twitterSession match {
        case Some(twitterSession) => {
          val newTweetData = newTweetForm.bindFromRequest().get
          Future {
            val tweet = tweetUseCase.generateTweet(twitterSession.getToken(), twitterSession.getSecret(), newTweetData.twitterId)
            val twitterJsonResponse = {
              if (tweet.isSuccess){
                new TweetJsonResponse(tweet.get.getBody(), 200)
              } else {
                new TweetJsonResponse("", 500)
              }
            }
            // TODO return status code
            val json = libs.json.Json.parse(s"""{"tweetText":"${tweet.map{t => t.getBody()}.getOrElse("")}"}""")
            Ok(json)
          }
        }
        case _ => Future{Redirect("/")}
      }
  }

  def post = Action.async { implicit request =>
    val tSession: Option[TwitterSession] =  cacheApi.get("twitter_user")
    tSession match {
      case Some(session) => {
        val sendTweetData = sendTweetForm.bindFromRequest().get
        Future {
          val postedTweet = tweetUseCase.postTweet(session.getToken(), session.getSecret(), sendTweetData.tweetText)

          // TODO return status code
          val json = libs.json.Json.parse(s"""{"tweetText":"${postedTweet.map{t => t.getBody()}.getOrElse("")}"}""")
          Ok(json)
        }
      }
      case _ => Future{ Redirect("/") }
    }
  }
}
