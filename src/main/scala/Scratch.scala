import scala.concurrent.Future

object Scratch extends App {

  /*

  Inputs:
    Stream of test results  /testresult/id/yes
    Stream of CC notifications /cc/?id1=...&id2=...


  TR -> Send
  Domain: Employees {
    list of close contacts
    test Result
  }















  */


  /*
  implicit val ec = concurrent.ExecutionContext.global

  // contentID: user's preference on content type, from most to least preferred.
  case class User(id: String, contentIds: List[Int])

  case class Container(contentIds: List[Int])

  def func(userId: String, container: Container): Future[Container] = {
    getUserFromDb(userId)
      .map { user =>
        val usersContentIds = user.contentIds.toSet
        val (inUser, notInUser) = container.contentIds.partition {usersContentIds.contains(_)}
        val containerContentIds = inUser.toSet
        val newContentIds = user.contentIds.filter(containerContentIds.contains(_)) ++ notInUser
        container.copy(newContentIds)
      }
  }

  def getUserFromDb(id: String): Future[User] = {
    Future.successful(User("1", List(1,2,3)))
  }
*/
  /*
  req: GET /?uid=:uid&cids=:cid1,:cid2,...,:cidN

  resp:
  {
    contentIds: [....]
  }





  */
/*
  // Given the following:

  sealed trait HttpRequest {
    def xForwardedFor: Option[String]
  }

  abstract class RateLimiter(limitPerSecond: Int) {
    def shouldLimit(req: HttpRequest): Boolean
  }

  // Implement a rate limiter, which accepts an http request, and decides whether we should apply rate limit on it.

  case class IgorsRateLimiter(limitPerSecond: Int) extends RateLimiter(limitPerSecond) {

    val cache = collection.mutable.HashMap.empty[String, (Long, Int)]

    override def shouldLimit(req: HttpRequest): Boolean = req.xForwardedFor match {

      case None => false

      case Some(ipAddress) =>

        val now = System.currentTimeMillis() / 1000
println(now)
        val count = cache.get(ipAddress) match {
          case None => 1
          case Some((second, cnt)) =>
            if (second == now) cnt + 1 else 1
        }
        cache(ipAddress) = (now, count)
println(cache(ipAddress))
        if (count > limitPerSecond) true else false

    }
  }

  val rl = IgorsRateLimiter(1)

  List(Some("127.0.0.1"), None, Some("127.0.0.1"))
    .foreach { ip =>
      val httpReq = new HttpRequest {
        override def xForwardedFor: Option[String] = ip
      }

      println(s"$ip: ${rl.shouldLimit(httpReq)}")

    }*/

}
