import scala.collection.mutable

object Scratch extends App {
  for (i <- 1 to 100) {
    println(fib(i))
  }

  def fib(i: Int): Long = {
    if (i == 1 || i == 2) {
      1
    } else {
      fib(i-1) + fib(i-2)
    }
  }
}
