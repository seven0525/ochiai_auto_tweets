package domain.tweet

class Tweet {

  var body: String = _
  var senderId: Long = _

  def getBody(): String = {
    return body
  }

  def getSenderId(): Long = {
    return senderId
  }
}

object Tweet {
  def apply(body: String, senderId: Long): Tweet = {
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