package domain.tweet

class Tweet {

  private var body: String = _
  private var senderId: String = _

  def getBody(): String = {
    return body
  }

  def getSenderId(): String = {
    return senderId
  }
}

object Tweet {
  def apply(body: String, senderId: String): Tweet = {
    val tweet: Tweet = new Tweet()
    tweet.body = body
    tweet.senderId = senderId
    return  tweet
  }

  def createNewTweet(body:String): Tweet = {
    val tweet: Tweet = new Tweet()
    tweet.body = body
    return tweet
  }
}