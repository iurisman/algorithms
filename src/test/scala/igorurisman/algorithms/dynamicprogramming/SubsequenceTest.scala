package igorurisman.algorithms.dynamicprogramming

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.time.{Duration, Instant}
import scala.util.Random

class SubsequenceTest extends AnyWordSpec with Matchers {


  s"$Subsequence" should {

    "compute the length of longest increasing subsequence" in {

      Subsequence.lisLen(
        Seq(5, 3, 11)
      ) mustBe 2

      Subsequence.lisLen(
        Seq("a", "c", "b", "d", "b")
      ) mustBe 3

      Subsequence.lisLen(
        Seq("1", "2", "8", "30", "77")
      ) mustBe 4

      Subsequence.lisLen(
        Seq("1", "2", "8", "30", "77")
      )(Ordering.by[String, Int](_.toInt)) mustBe 5

      val start = Instant.now
      Subsequence.lis(Array.fill(100000)(Random.nextInt()))
      println(Duration.between(start, Instant.now))
    }
  }
}
