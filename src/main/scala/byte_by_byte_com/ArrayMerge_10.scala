package byte_by_byte_com

import java.util.concurrent.atomic.AtomicInteger
import scala.util.Random

object ArrayMerge_10 extends App {

  val a1 = Array(1,3,5,0,0,0)
  val a2 = Array(2,4,6);

  def merge(a1: Array[Int], a2: Array[Int]): Unit = {
    var firstZeroIx: Int = a1.zipWithIndex.find{case (n, _) => n == 0}.get._2
    for (elem <- a2) {
      val ix = a1.search(elem, 0, firstZeroIx)
      insert(a1, elem, ix.insertionPoint)
      firstZeroIx += 1
    }
  }

  def insert[T](a: Array[T], t: T, at: Int): Unit = {
    if (at < 0) throw new RuntimeException(s"at $at is too small")
    if (at >= a.length) throw new RuntimeException(s"at $at is too large")
    var next = t
    for(i <- at until a.length) {
      val temp = a(i)
      a(i) = next
      next = temp
    }
  }
  merge(a1, a2)
  print(a1.mkString(","))
}