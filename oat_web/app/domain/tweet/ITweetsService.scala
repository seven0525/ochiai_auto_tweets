package domain.tweet

trait ITweetsService {
  def generateTweet(tweets: Seq[Tweet]): Tweet
}
