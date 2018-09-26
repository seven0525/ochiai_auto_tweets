package domain.tweet

import javax.inject.Singleton
import twitter4j.auth.AccessToken

@Singleton
trait ITweetClient {
  def updateStatus(token: String, secret: String, tweet: Tweet): Tweet

  def getUserTimeline(token: String, secret: String): List[Tweet]

}
