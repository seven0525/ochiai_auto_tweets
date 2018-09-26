package application.common

class TwitterSession (token: String, secret: String, twitterId: String){
  def getToken(): String = return token

  def getSecret(): String = return secret

  def getTwitterId(): String = return twitterId
}
