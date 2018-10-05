package domain.tweet

import javax.inject.Singleton

import scala.collection.mutable.Buffer

trait ITweetClient {
  def updateStatus(token: String, secret: String, tweet: Tweet): Tweet

  def getUserTimeline(token: String, secret: String, twitterId: String): Buffer[Tweet]

}
