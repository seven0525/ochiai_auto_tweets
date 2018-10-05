package application.tweet

import client.Tweet4JClient
import domain.tweet.{ITweetClient, Tweet, TweetsService}
import javax.inject.{Inject, Singleton}

@Singleton
class TweetUseCase @Inject()(twitterClient: Tweet4JClient, tweetsService: TweetsService) {
  def generateTweet(token: String, secret: String, twitterId: String) = {
    val tweets = twitterClient.getUserTimeline(token: String, secret: String, twitterId)
    tweetsService.generateTweet(tweets)
  }

  def postTweet(token: String, secret: String, tweetText:String) = {
    val tweet: Tweet = Tweet.createNewTweet(tweetText)
    twitterClient.updateStatus(token, secret, tweet)
  }
}
