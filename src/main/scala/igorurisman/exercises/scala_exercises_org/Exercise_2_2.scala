package igorurisman.exercises.scala_exercises_org

object Exercise_2_2 extends App {

  /** Tail-recursive isSorted */
  def isSorted[T](xs: Iterable[T])(implicit ordering: Ordering[T]): Boolean = {
    @annotation.tailrec
    def loop(xs: Iterable[T], prev: T, prevDir: Int = 0): Boolean = {
      if (xs.isEmpty) true
      else {
        val next = xs.head
        val comparison = ordering.compare(next, prev)
        if (prevDir == 0) {
          loop(xs.tail, next, comparison)
        } else {
          if (comparison != 0 && comparison != prevDir) {
            false
          } else {
            loop(xs.tail, next, prevDir)
          }
        }
      }
    }
    xs.headOption match {
      case None => true
      case Some(head) => loop(xs.tail, head)
    }
  }

  Seq(
    (Seq(1,2,3), true),
    (Seq(1,1,2,3), true),
    (Seq(5,4,3,3,0), true),
    (Seq(1,2,3,2), false),
  )
    .foreach {
      case (seq, sorted) => println(seq); assert(isSorted(seq) == sorted)
    }
}

