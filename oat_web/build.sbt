name := "oat_web"
 
version := "1.0" 
      
lazy val `oat_web` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.7"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice , cacheApi)
// https://mvnrepository.com/artifact/org.twitter4j/twitter4j-core
libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.4"
// https://mvnrepository.com/artifact/org.webjars/webjars-play
libraryDependencies += "org.webjars" %% "webjars-play" % "2.6.3"
libraryDependencies += "org.webjars" % "vue" % "2.1.3"

unmanagedResourceDirectories in Test +=  baseDirectory( _ /"target/web/public/test" ).value

routesGenerator := InjectedRoutesGenerator

      