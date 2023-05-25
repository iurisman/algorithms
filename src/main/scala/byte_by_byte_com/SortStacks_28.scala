package byte_by_byte_com

import scala.reflect.ClassTag
import scala.collection.mutable
import math.Ordered.orderingToOrdered
import math.Ordering.Implicits.infixOrderingOps
import scala.util.Random

object SortStacks_28 extends App {

  def sort(stack: mutable.Stack[Int]): Unit = {
    val temp = mutable.Stack.empty[Int]
    if (stack.size > 1) {
      temp.push(stack.pop())
    }
    while (stack.nonEmpty) {
      val next = stack.pop()
      if (next > temp.top) {
        temp.push(next)
      } else {
        var count = 0
        while (temp.nonEmpty && next < temp.top) {
          stack.push(temp.pop())
          count += 1
        }
        temp.push(next)
        for (_ <- 1 to count) {
          temp.push(stack.pop())
        }
      }
    }
    stack.pushAll(temp)
  }

  val stack = mutable.Stack.fill(10000)(Random.nextInt())
  println(stack)
  sort(stack)
  println(stack)
}