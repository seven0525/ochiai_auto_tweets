package domain.tweet

trait ITweetGeneratorClient {
  def generateTweet(twitterId: String): Tweet
}
