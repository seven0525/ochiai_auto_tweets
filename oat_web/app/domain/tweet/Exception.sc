import scala.util.Failure

sealed abstract class

case object CannotGetTweetFailure extends Failure

case object CannotPostTweetException extends RuntimeException

case object CannotGenerateTweetException extends RuntimeException