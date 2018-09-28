package application.common

class TwitterSession (token: String, secret: String, twitterId: Long){
  def getToken(): String = return token

  def getSecret(): String = return secret

  def getTwitterId(): Long = return twitterId
}
