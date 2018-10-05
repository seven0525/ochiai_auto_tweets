package domain.tweet

import scala.collection.mutable

trait ITweetsService {
  def generateTweet(tweets: mutable.Buffer[Tweet]): Tweet
}
