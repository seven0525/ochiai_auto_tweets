package client

import domain.tweet.{ITweetClient, Tweet}
import javax.inject.{Inject, Singleton}
import play.api.libs.oauth
import play.api.libs.oauth.{ConsumerKey, RequestToken}
import play.api.libs.ws.{WSClient, WSResponse}

import scala.collection.mutable
import scala.concurrent.Future

@Singleton
class TweetScratchClient @Inject()(ws: WSClient) extends ITweetClient {
  private val apiBaseUrl: String = "https://api.twitter.com/1.1/"
  override def updateStatus(token: String, secret: String, tweet: Tweet): Tweet = {
    val requestToken: RequestToken = new RequestToken(token,secret)
    val apiUrl = apiBaseUrl + "statuses/update.json"
    val wsResponse: Future[WSResponse] = ws.url(apiUrl)
      .withQueryStringParameters(("status", tweet.getBody()))
      .sign(new oauth.OAuthCalculator(new ConsumerKey("",""), requestToken))
      .get()

    return new Tweet
  }

  override def getUserTimeline(token: String, secret: String, twitterId: String): mutable.Buffer[Tweet] = ???
}
