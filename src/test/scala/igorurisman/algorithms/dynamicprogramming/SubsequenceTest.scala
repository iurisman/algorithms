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
        Seq("a", "c", "b", "c", "e", "d")
      ) mustBe 4

      Subsequence.lisLen(
        Seq("1", "2", "8", "30", "77")
      ) mustBe 4

      Subsequence.lisLen(
        Seq("1", "2", "8", "30", "77")
      )(Ordering.by[String, Int](_.toInt)) mustBe 5

      val rand = new Random(0)
      Subsequence.lisLen(Array.fill(10000)(rand.nextInt(1000))) mustBe 180

    }

    "compute a longest increasing subsequence" in {

      Subsequence.lis(
        Seq(5, 3, 11)
      ) mustBe Seq(5, 11)

      Subsequence.lis(
        Seq("a", "c", "b", "d", "b")
      ) mustBe Seq("a", "c", "d")

      Subsequence.lis(
        Seq("a", "c", "b", "c", "e", "d")
      ) mustBe Seq("a", "b", "c", "e")

      Subsequence.lis(
        Seq("1", "2", "8", "30", "77")
      ) mustBe Seq("1", "2", "30", "77")

      Subsequence.lis(
        Seq("1", "2", "8", "30", "77")
      )(Ordering.by[String, Int](_.toInt)) mustBe Seq("1", "2", "8", "30", "77")

      val rand = new Random(0)
      Subsequence.lis(Array.fill(100)(rand.nextInt(1000))) mustBe
        Seq(29, 53, 77, 95, 143, 223, 245, 375, 387, 408, 451, 457, 551, 580, 662, 773, 789, 901, 916, 957)
    }

    "compute the length of longest common subsequence" in {

      Subsequence.lcsLen(
        Seq("m","o","t","h","e","r"),
        Seq("d","o","e","s"),
      ) mustBe 2

      Subsequence.lcsLen(
        Seq(5, 3, 11),
        Seq(3)
      ) mustBe 1

      Subsequence.lcsLen(
        Seq("m","o","t","h","e","r"),
        Seq("t","e","t","h","e","r"),
      ) mustBe 4

      Subsequence.lcsLen(
        Seq("d","a","u","g","h","t","e","r"),
        Seq("s","o","n"),
      ) mustBe 0

    }

    "compute a longest common subsequence" in {

      Subsequence.lcs(
        Seq("m","o","t","h","e","r"),
        Seq("d","o","e","s"),
      ) mustBe Seq("o", "e")

      Subsequence.lcs(
        Seq(5, 3, 11),
        Seq(3)
      ) mustBe Seq(3)

      Subsequence.lcs(
        Seq("m","o","t","h","e","r"),
        Seq("t","e","t","h","e","r"),
      ) mustBe Seq("t","h","e","r")

      Subsequence.lcs(
        Seq("d","a","u","g","h","t","e","r"),
        Seq("s","o","n"),
      ) mustBe Seq()

      val rand = new Random(0)
      Subsequence.lcs(Array.fill(1000)(rand.nextInt(1000)),Array.fill(100)(rand.nextInt(1000))) mustBe
        Seq(773, 490, 471, 626, 130, 224, 559, 378, 981, 500, 163, 32, 756, 538, 472, 361)
    }
  }
}
