package client

import java.util
import java.util.stream.Collectors

import domain.tweet.{ITweetClient, Tweet}
import javax.inject.Singleton
import twitter4j._
import twitter4j.auth.AccessToken

import scala.collection.JavaConverters._
import scala.collection.mutable.Buffer


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
  def updateStatus(token: String, secret: String, tweet: Tweet): Tweet = {
    val twitter: Twitter = createAuthorizedTwitterInstance(token, secret)
    try {
      val status: Status = twitter.updateStatus(tweet.getBody())
      return Tweet(status.getText, status.getUser.getScreenName)
    } catch {
      case e:TwitterException => throw e
    }
  }

  /**
    * ユーザのTweetを取得する。
    * @param token
    * @param secret
    * @return
    */
  override def getUserTimeline(token: String, secret: String, twitterId: String): Buffer[Tweet] = {
    val twitter: Twitter = createAuthorizedTwitterInstance(token, secret)
    val paging: Paging = new Paging()
    paging.setCount(200)
    //    paging.setSinceId(sinceId)
    try {
      val statuses: ResponseList[Status] = twitter.getUserTimeline(twitterId, paging)
      val tweets: Buffer[Tweet] = statuses.asScala
        .filter(s => s.getText.startsWith("RT"))
        .map(s => Tweet(s.getText, s.getUser.getScreenName))

      return tweets
    } catch {
      case e: TwitterException => throw e
    }
  }

  def createAuthorizedTwitterInstance(token: String, secret: String): Twitter = {
    val accessToken: AccessToken = new AccessToken(token, secret)
    return (new TwitterFactory()).getInstance(accessToken)
  }
}