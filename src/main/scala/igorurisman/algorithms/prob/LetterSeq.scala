package igorurisman.algorithms.prob

import scala.collection.immutable.ListMap
import scala.util.Random

object LetterSeq extends App {
  for (_ <- 1 to 2000) print(Letters.next)
}

object Letters {
  private val rand = new Random()
  def next: Char = {
    val nextPct = rand.nextFloat() * 100
    var sum = 0d
    var result = 'A'
    for ((c, pct) <- probabilities if sum <= nextPct) {
      sum += pct
      result = c
    }
    result
  }

  val probabilities = ListMap(
    'A' -> 8.4966,
    'B' -> 2.0720,
    'C' -> 4.5388,
    'D' -> 3.3844,
    'E' -> 11.1607,
    'F' -> 1.8121,
    'G' -> 2.4705,
    'H' -> 3.0034,
    'I' -> 7.5448,
    'J' -> 0.1965,
    'K' -> 1.1016,
    'L' -> 5.4893,
    'M' -> 3.0129,
    'N' -> 6.6544,
    'O' -> 7.1635,
    'P' -> 3.1671,
    'Q' -> 0.1962,
    'R' -> 7.5809,
    'S' -> 5.7351,
    'T' -> 6.9509,
    'U' -> 3.6308,
    'V' -> 1.0074,
    'W' -> 1.2899,
    'X' -> 0.2902,
    'Y' -> 1.7779,
    'Z' -> 0.2722,
  )


}

