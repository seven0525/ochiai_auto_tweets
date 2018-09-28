package application.tweet

import client.TweetApiClient
import domain.tweet.{ITweetClient, Tweet, TweetsService}
import javax.inject.{Inject, Singleton}

@Singleton
class TweetUseCase @Inject()(twitterClient: TweetApiClient, tweetsService: TweetsService) {
  def generateTweet(token: String, secret: String, twitterId: String) = {
    var tweets: List[Tweet] = Nil
    if (twitterId == None) {
      //TODO 落合Tweetをダウンロード
    } else {
      tweets = twitterClient.getUserTimeline(token: String, secret: String)
    }
    tweetsService.generateTweet(tweets)
  }

  def postTweet(token: String, secret: String, tweetText:String) = {
    val tweet: Tweet = Tweet.createNewTweet(tweetText)
    twitterClient.updateStatus(token, secret, tweet)
  }
}
