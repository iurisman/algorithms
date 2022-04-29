package igorurisman.algorithms.dynamicprogramming

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CoinChangeTest extends AnyWordSpec with Matchers {


  s"$CoinChange" should {

    "compute the number of ways to give change" in {

      CoinChange.ways(
        7,
        Array(5, 3, 11)
      ) mustBe 0

      CoinChange.ways(
        10,
        Array(5, 2, 7)
      ) mustBe 2

      CoinChange.ways(
        10,
        Array(5, 2, 10)
      ) mustBe 3

      CoinChange.ways(
        250,
        Array(41, 34, 46, 9, 37, 32, 42, 21, 7, 13, 1, 24, 3, 43, 2, 23, 8, 45, 19, 30, 29, 18, 35, 11)
      ) mustBe 15685693751L

    }

    "compute the min number of coins in change" in {

      CoinChange.minCoins(
        12,
        Array(10, 11)
      ) mustBe None

      CoinChange.minCoins(
        10,
        Array(5, 2)
      ) mustBe Some(2)

      CoinChange.minCoins(
        15,
        Array(1, 5, 2)
      ) mustBe Some(3)

      CoinChange.minCoins(
        16,
        Array(1, 5, 2)
      ) mustBe Some(4)

      CoinChange.minCoins(
        250,
        Array(41, 34, 46, 9, 37, 32, 42, 21, 7, 13, 1, 24, 3, 43, 2, 23, 8, 45, 19, 30, 29, 18, 35, 11)
      ) mustBe Some(6)

    }
  }
}
