package domain.tweet

import java.io.{FileWriter, PrintWriter}
import java.nio.file.{Files, Paths}

import client.TweetGeneratorClient
import javax.inject.{Inject, Singleton}

@Singleton
class TweetsService @Inject()(tweetGeneratorClient: TweetGeneratorClient) {
  def generateTweet(tweets: List[Tweet]) = {
    val file = Paths.get("/Users/kuratadaisuke/ochiai_auto_tweets/tmp/" + tweets.head.getSenderId() + "tweet.txt")
    if(Files.notExists(file)) Files.createFile(file)


    val fileInputWriter = new FileWriter(file.toFile)
    tweets.foreach(tweet => fileInputWriter.write(tweet.getBody().replace(System.getProperty("line.separator"), "") + System.getProperty("line.separator")))
    fileInputWriter.close()

    tweetGeneratorClient.generateTweet(tweets.head.getSenderId())

  }
}