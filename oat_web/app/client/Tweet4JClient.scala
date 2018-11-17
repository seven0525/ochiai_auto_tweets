package client

import domain.tweet.{ITweetClient, Tweet}
import javax.inject.Singleton
import twitter4j._
import twitter4j.auth.AccessToken

import scala.collection.JavaConverters._
import scala.util.Try


/**
  * TwitterAPIを使用するためのClient.
  */
@Singleton
class Tweet4JClient extends ITweetClient{
  /**
    * TweetをPostする.
    * @param accessToken
    * @param tweetText
    * @throws TwitterException
    * @return
    */
  def updateStatus(token: String, secret: String, tweet: Tweet): Try[Tweet] = {
    val twitter: Twitter = createAuthorizedTwitterInstance(token, secret)
    Try {
      val status: Status = twitter.updateStatus(tweet.getBody())
      Tweet(status.getText, status.getUser.getScreenName)
    }
  }

  /**
    * ユーザのTweetを取得する。
    * @param token
    * @param secret
    * @return
    */
  override def getUserTimeline(token: String, secret: String, twitterId: String): Try[Seq[Tweet]] = {
    val twitter: Twitter = createAuthorizedTwitterInstance(token, secret)
    val paging: Paging = new Paging()
    paging.setCount(200)
    //    paging.setSinceId(sinceId)
    Try {
      val statuses: ResponseList[Status] = twitter.getUserTimeline(twitterId, paging)
      statuses.asScala
        .filterNot{s => s.getText.startsWith("RT")}
        .map{s => Tweet(s.getText, s.getUser.getScreenName)}
    }
  }

  def createAuthorizedTwitterInstance(token: String, secret: String): Twitter = {
    val accessToken: AccessToken = new AccessToken(token, secret)
    return (new TwitterFactory()).getInstance(accessToken)
  }
}