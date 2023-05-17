import scala.collection.mutable

object Scratch extends App {

  val s = Set(1,2,3)
  println(combinations(s, 2))

  def combinations[T](set: Set[T], size: Int): Set[Set[T]] = {
    if (size > set.size) {
      throw new IllegalArgumentException(s"Size $size is too large")
    } else if (size < 1) {
      throw new IllegalArgumentException(s"Size $size is too small")
    }
    else if (size == 1) {
      set.map(Set(_))
    }
    else {
      combinations(set, size-1)
        .flatMap(combIminus1 =>
          for (e <- set.diff(combIminus1)) yield {
            combIminus1 + e
          }
        )
    }
  }

}
