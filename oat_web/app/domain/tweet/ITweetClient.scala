package domain.tweet

import scala.util.Try

trait ITweetClient {
  def updateStatus(token: String, secret: String, tweet: Tweet): Try[Tweet]

  def getUserTimeline(token: String, secret: String, twitterId: String): Try[Seq[Tweet]]

}