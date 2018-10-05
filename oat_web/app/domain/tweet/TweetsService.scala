package domain.tweet

import java.io.FileWriter
import java.nio.file.{Files, Paths}

import javax.inject.{Inject, Singleton}

import scala.collection.mutable

@Singleton
class TweetsService @Inject()(tweetGeneratorClient: ITweetGeneratorClient) extends ITweetsService {
  override def generateTweet(tweets: mutable.Buffer[Tweet]) = {
    val file = Paths.get("/Users/kuratadaisuke/ochiai_auto_tweets/tmp/" + tweets.head.getSenderId() + "tweet.txt")
    if(Files.notExists(file)) Files.createFile(file)


    val fileInputWriter = new FileWriter(file.toFile)
    tweets.foreach(tweet => fileInputWriter.write(tweet.getBody().replace(System.getProperty("line.separator"), "") + System.getProperty("line.separator")))
    fileInputWriter.close()

    tweetGeneratorClient.generateTweet(tweets.head.getSenderId())
  }
}