package domain.tweet

import java.io.PrintWriter
import java.nio.file.{Files, Paths}

import javax.inject.{Inject, Singleton}

@Singleton
class TweetsService @Inject()(tweetGeneratorClient: ITweetGeneratorClient) {
  def generateTweet(tweets: List[Tweet]) = {
    val file = Paths.get(tweets.head.getSenderId() + "tweet.txt")
    if(Files.notExists(file)) Files.createFile(file)

    val fileInputWriter = new PrintWriter(tweets.head.getSenderId() + "tweet.txt")
    tweets.foreach(tweet => fileInputWriter.write(tweet.getBody() + "¥n"))
    fileInputWriter.close()

    tweetGeneratorClient.generateTweet(tweets.head.getSenderId())

  }
}
