package application.tweet

import domain.tweet.{ITweetClient, Tweet, TweetsService}
import javax.inject.{Inject, Singleton}

@Singleton
class TweetUseCase @Inject()(twitterClient: ITweetClient, tweetsService: TweetsService) {
  def generateTweet(token: String, secret: String, twitterId: String) = {
    var tweets: List[Tweet] = Nil
    if (twitterId == None) {

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
