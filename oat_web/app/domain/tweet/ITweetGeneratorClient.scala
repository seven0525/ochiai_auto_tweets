package domain.tweet

trait ITweetGeneratorClient {
  def generateTweet(twitterId: Long): Tweet
}
