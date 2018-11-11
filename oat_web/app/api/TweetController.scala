package api

import application.common.TwitterSession
import application.tweet.TweetUseCase
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import domain.tweet.Tweet
import javax.inject.Inject
import play.api.cache.SyncCacheApi
import play.api.data.Forms._
import play.api.data._
import play.api.libs
import play.api.libs.json
import play.api.mvc.{AbstractController, ControllerComponents, Result}
import play.libs.Json
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, JsValue, Writes}

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
      twitterSession match {
        case Some(twitterSession) => {
          val newTweetData = newTweetForm.bindFromRequest().get
          Future{
            val tweet: Tweet = tweetUseCase.generateTweet(twitterSession.getToken(), twitterSession.getSecret(), newTweetData.twitterId)
            val json = libs.json.Json.parse(s"""{"tweetText":"${tweet.getBody()}"}""")
            Ok(json)
          }
        }
        case _ => Future{Redirect("/")}
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
