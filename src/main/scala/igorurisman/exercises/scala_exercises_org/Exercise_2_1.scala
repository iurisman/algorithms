package igorurisman.exercises.scala_exercises_org

import scala.io.Source

object Exercise_2_1 extends App {

  /** Tail-recursive factorial */
  def fac(n: Int): BigInt = {
    @annotation.tailrec
    def loop(n: Int, acc: BigInt = 1): BigInt = {
      if (n == 1) acc
      else loop(n - 1, acc * n)
    }
    if (n <= 0) throw new Exception(s"Non-positive parameter $n")
    loop(n)
  }

  assert(fac(1) == 1)
  assert(fac(2) == 2)
  assert(fac(3) == 6)
  assert(fac(4) == 24)
  assert(fac(5) == 120)


  /** Tail-recursive fibonacci */
  def fib(n: Int): BigInt = {
    //@annotation.tailrec
    def loop(n: Int, accPPrev: BigInt, accPrev: BigInt): BigInt = {
      if (n == 1) {
        accPPrev
      } else if (n == 2) {
        accPrev
      } else {
        loop(n-1, accPrev, accPPrev + accPrev)
      }
    }
    if (n <= 0) throw new Exception(s"Non-positive parameter $n")
    else loop(n, 1, 1)
  }

  assert(fib(5) == 5)
  assert(fib(6) == 8)
  assert(fib(7) == 13)

  println((1 to 500000).map(BigInt(_)).reduce(_*_))
}

