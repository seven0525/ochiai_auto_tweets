package controllers

import application.common.TwitterSession
import application.tweet.TweetUseCase
import domain.tweet.Tweet
import javax.inject.Inject
import play.api.cache.SyncCacheApi
import play.api.mvc.{AbstractController, ControllerComponents}

class TweetController @Inject()(cc: ControllerComponents, tweetUseCase: TweetUseCase, cacheApi: SyncCacheApi) extends AbstractController(controllerComponents = cc){
  val newTweetForm = Form(
    mapping(
        "twitterId" -> text
    ).apply(NewTweetForm.apply).unapply(NewTweetForm.unapply)
  )

  val sendTweetForm = Form(
    mapping(
        "tweetText" -> text
    ).apply(SendTweetForm.apply).unapply(SendTweetForm.unapply)
  )

  def show() = Action{
    val twitterSession: Option[TwitterSession] =  cacheApi.get("twitter_user")
    twitterSession match {
      case Some(twitterSession) => {
        val tweet: Tweet = tweetUseCase.generateTweet(twitterSession.getToken(), twitterSession.getSecret(), newTweetForm.twitterId)
        Ok(views.html.tweet_show(tweetText = tweet.getBody(), message = ""))
      }
      case _ => Redirect("/")
    }
  }

  def post(tweetText: String) = Action{
    val twitterSession: Option[TwitterSession] =  cacheApi.get("twitter_user")
    twitterSession match {
      case Some(twitterSession) => {
        tweetUseCase.postTweet(twitterSession.getToken(), twitterSession.getSecret(), sendTweetForm.tweetText)
      }
      case _ => Redirect("/")
    }
    Ok(views.html.tweet_show(tweetText = "aaa", message = "ツイートを送信しました"))
  }
}
