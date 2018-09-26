package domain.tweet

class Tweet(body: String, senderId: String) {
  def this() {
    this(null, null)
  }

  def getBody(): String = {
    return body
  }

  def getSenderId(): String = {
    return senderId
  }
}

object Tweet {
  def createNewTweet(body: String): Tweet = {
    new Tweet(body, null)
  }
}