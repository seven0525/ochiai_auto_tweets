package controllers

import application.common.TwitterSession
import javax.inject._
import play.api.mvc._
import play.cache.SyncCacheApi
import twitter4j.auth.{AccessToken, RequestToken}
import twitter4j.{Twitter, TwitterFactory, User}

@Singleton
class TwitterController @Inject()(cc: ControllerComponents, cacheApi: SyncCacheApi) extends AbstractController(cc) {

  /**
    * twitterの認証ページへリダイレクトさせるAction
    * @return
    */
  def login = Action {implicit request =>
    val twitter: Twitter = (new TwitterFactory()).getInstance()
    val requestToken: RequestToken = twitter.getOAuthRequestToken("http://127.0.0.1:9000/twitterOAuthCallback")

    cacheApi.set("twitter", twitter, 120)
    cacheApi.set("requestToken", requestToken, 120)

    Redirect(requestToken.getAuthorizationURL)
  }

  /**
    * twitterの認証ページからCallBackされるAction
    * @return
    */
  def twitterOAuthCallback = Action { implicit request =>
    // 承認可否を精査（deniedがあったら承認キャンセル）
    request.queryString.get("denied") match {

      // Twitterのアプリケーション承認キャンセル時
      case Some(denied) => {
        Ok
      }//Redirect(routes.TwitterController.twitterLogout)

      // Twitterのアプリケーション承認時
      case _ => {

        // TwitterのオブジェクトをCacheから取得
        val getTwitter     : Option[Twitter] = Option(cacheApi.get("twitter"))

        // 取得できない場合はトップ画面へ
        getTwitter match {
          case Some(twitter) => {

            // RequestTokenのオブジェクトをCacheから取得
            val getRequestToken: Option[RequestToken] = Option(cacheApi.get("requestToken"))

            // 取得できない場合はトップ画面へ
            getRequestToken match {
              case Some(requestToken) => {

                // AuthTokenを取得する
                var authToken: String = request.queryString.get("oauth_token").get.head
                var authVerifier: String = request.queryString.get("oauth_verifier").get.head

                // AccessTokenを取得する
                 val accessToken: AccessToken = twitter.getOAuthAccessToken(requestToken, authVerifier)
                 cacheApi.set("accessToken", accessToken, 4320)

                // Twitterオブジェクトの認証
                twitter.verifyCredentials()

                // TwitterのUserオブジェクトを取得
                val user: User = twitter.showUser(twitter.getId())

                val twitterSession: TwitterSession =
                  new TwitterSession(accessToken.getToken, accessToken.getTokenSecret, user.getName)

                // Cacheに設定(3日間有効)
                cacheApi.set("twitter_user", twitterSession, 4320)

                // Cacheから削除
                cacheApi.remove("twitter")
                cacheApi.remove("requestToken")

              }
              case _ =>
            }
          }
          case _ =>
        }

        // リターン
        Redirect("/")
      }
    }
  }

  def logout = Action {implicit request =>
    cacheApi.remove("accessToken")
    cacheApi.remove("twitter_user")
    Redirect("/")
  }

}
