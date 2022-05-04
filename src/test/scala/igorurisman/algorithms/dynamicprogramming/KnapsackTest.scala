package igorurisman.algorithms.dynamicprogramming

import igorurisman.algorithms.dynamicprogramming.Knapsack.Item
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random

class KnapsackTest extends AnyWordSpec with Matchers {

  s"$Knapsack" should {

    "compute the max value of the sack" in {

      Knapsack.maxValue(
        10,
        Seq(Item(1,3), Item(2,3), Item(5,7))
      ) mustBe 15

    }
  }
}
