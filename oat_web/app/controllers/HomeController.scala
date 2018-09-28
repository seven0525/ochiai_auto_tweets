package controllers

import application.common.TwitterSession
import javax.inject._
import play.api.cache.SyncCacheApi
import play.api.mvc._
import play.filters.csrf.CSRFAddToken

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, syncCacheApi: SyncCacheApi, addToken: CSRFAddToken) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = addToken (
    Action { implicit request =>
      val session: Option[TwitterSession] = syncCacheApi.get("twitter_user")
      Ok(views.html.top(message = "", twitterSession = session))
    }
  )
}
