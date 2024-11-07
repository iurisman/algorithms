package igorurisman.algorithms.dynamicprogramming

import java.util
import scala.collection.mutable
object Fibonacci extends App {

  /**
   * Simple iterative Fibonacci with memoization.
   */
  def fib1(index: Int): BigInt = {
    val cache = mutable.HashMap.empty[Int, BigInt];
    def fr(index: Int): BigInt = {
      cache.getOrElseUpdate(
        index,
        index match {
          case 1 | 2 => BigInt(1)
          case _ => fr(index - 2) + fr(index - 1)
        }
      )
    }
    fr(index)
  }
}
