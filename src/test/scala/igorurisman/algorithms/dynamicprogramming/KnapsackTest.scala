package igorurisman.algorithms.dynamicprogramming

import igorurisman.algorithms.dynamicprogramming.Knapsack.Item
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random

class KnapsackTest extends AnyWordSpec with Matchers {

  s"$Knapsack" should {

    "compute the max value of the sack" in {

      Knapsack.maxValue(
        0,
        Seq(Item(2,5), Item(1,2), Item(5,7))
      ) mustBe 0

      Knapsack.maxValue(
        10,
        Seq()
      ) mustBe 0

      Knapsack.maxValue(
        10,
        Seq(Item(2,5), Item(1,2), Item(5,7))
      ) mustBe 25

      Knapsack.maxValue(
        20,
        Seq(Item(7,11), Item(1,3), Item(2,3), Item(5,7))
      ) mustBe 60

    }
  }
}
