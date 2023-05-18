package byte_by_byte_com

import scala.collection.mutable
import scala.util.Random

object ThreeSum_43 extends App {

  def threeSum(list: List[Int]): Set[List[Int]] = {
    val result = mutable.HashSet.empty[List[Int]]
    val listZippedWithIndex = list.zipWithIndex
    for {
      x <- listZippedWithIndex
      y <- listZippedWithIndex if y._2 > x._2
      z <- listZippedWithIndex if z._2 > y._2
    } {
      val triplet = List(x,y,z).map(_._1)
      if (triplet.sum == 0) {
        result += triplet
      }
    }
    result.toSet
  }
  val list = List.tabulate(10)(_ => Random().nextInt(10)-5)
  println(list)
  println(threeSum(list))

}