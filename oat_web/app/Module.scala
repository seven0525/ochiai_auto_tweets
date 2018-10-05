import com.google.inject.AbstractModule
import client.{Tweet4JClient, TweetGeneratorClient}
import domain.tweet.{ITweetClient, ITweetGeneratorClient, ITweetsService, TweetsService}

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module extends AbstractModule {

  override def configure() = {
    //SingletonのTraitをInjectionするときにどの実装クラスをInjectionするか指定
    bind(classOf[ITweetClient]).to(classOf[Tweet4JClient])
    bind(classOf[ITweetGeneratorClient]).to(classOf[TweetGeneratorClient])
    bind(classOf[ITweetsService]).to(classOf[TweetsService])
  }

}
