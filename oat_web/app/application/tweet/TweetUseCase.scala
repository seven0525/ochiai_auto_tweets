package application.tweet

import domain.tweet.{ITweetClient, ITweetsService, Tweet}
import javax.inject.{Inject, Singleton}

import scala.util.Try

@Singleton
class TweetUseCase @Inject()(twitterClient: ITweetClient, tweetsService: ITweetsService) {
  def generateTweet(token: String, secret: String, twitterId: String): Try[Tweet] = {
    val tweets = twitterClient.getUserTimeline(token: String, secret: String, twitterId)
    tweets.map{ src =>
      tweetsService.generateTweet(src)
    }
  }

  def postTweet(token: String, secret: String, tweetText:String) = {
    val tweet: Tweet = Tweet.createNewTweet(tweetText)
    twitterClient.updateStatus(token, secret, tweet)
  }
}
