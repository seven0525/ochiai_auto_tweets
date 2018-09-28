package client

import domain.tweet.{ITweetClient, Tweet}
import javax.inject.Singleton
import twitter4j._
import twitter4j.auth.AccessToken

import scala.collection.JavaConverters._


/**
  * TwitterAPIを使用するためのClient.
  */
@Singleton
class TweetApiClient extends ITweetClient{
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
      return Tweet(status.getText, status.getUser.getId)
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
  def getUserTimeline(token: String, secret: String): List[Tweet] = {
    val twitter: Twitter = createAuthorizedTwitterInstance(token, secret)
    val paging: Paging = new Paging()
    paging.setCount(200)
    //    paging.setSinceId(sinceId)
    try {
      val statuses: ResponseList[Status] = twitter.getUserTimeline(paging)
      statuses.get(0).getUser.getName
      val tweets: List[Tweet] = statuses.asScala
        .filterNot(status => status.isRetweetedByMe)
        .map(status => Tweet(status.getText, status.getUser.getId))
        .toList
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