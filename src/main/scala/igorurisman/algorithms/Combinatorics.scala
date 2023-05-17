package igorurisman.algorithms

object Combinatorics {

  /** Compute combinations of given size from a given set of a greater or equal size.
   */
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
      combinations(set, size - 1)
        .flatMap(combIminus1 =>
          for (e <- set.diff(combIminus1)) yield {
            combIminus1 + e
          }
        )
    }
  }

  def main(args: Array[String]): Unit = {
    assert(combinations(Set(1,2,3),1) == Set(Set(1),Set(2),Set(3)))
    assert(combinations(Set(1,2,3,4,5), 2) == Set(Set(3, 1), Set(2, 4), Set(4, 1), Set(5, 4), Set(2, 1), Set(5, 3), Set(2, 3), Set(5, 1), Set(2, 5), Set(4, 3)))
  }
}
