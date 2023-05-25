package byte_by_byte_com

import scala.reflect.ClassTag
//import scala.collection.mutable

object Permutations_12 extends App {

  val set = Set(1,2,3,4)
  println(permutations(set, 3))
  def permutations[T](set: Set[T], size: Int)(implicit ct: ClassTag[T]): Set[Set[T]] = {
    if (size > set.size) throw new Exception(s"Size $size is too large")
    if (size < 1) throw new Exception(s"Size $size is too small")
    val basisSet = set.map(t => Set(t))
    var result: Set[Set[T]] = Set.empty ++ basisSet;
    for (len <- 1 to size) {
      result =
        (
          for {
            r <- result
            b <- basisSet
          } yield {
            b ++ r
          }
        ).filter(_.size == len)
    }
    result
  }
}