name := "tweet_instead_of_you"
 
version := "1.0" 
      
lazy val `tweet_instead_of_you` = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(PlayService)
  .enablePlugins(RoutesCompiler)
  .settings(scalaVersion := "2.12.6")


resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(guice, akkaHttpServer, logback, openId, ws, ehcache, cacheApi)

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.0"

libraryDependencies += "com.typesafe.play" %% "play-iteratees" % "2.6.1"

libraryDependencies += "com.typesafe.play" %% "play-iteratees-reactive-streams" % "2.6.1"

// https://mvnrepository.com/artifact/org.twitter4j/twitter4j-core
libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.4"

// https://mvnrepository.com/artifact/org.postgresql/postgresql
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.2"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.6.3",
  "org.webjars" % "bootstrap" % "4.1.3",
  "org.webjars.bower" % "jquery" % "3.3.1",
  "org.webjars" % "popper.js" % "1.14.3"
)

routesGenerator := StaticRoutesGenerator