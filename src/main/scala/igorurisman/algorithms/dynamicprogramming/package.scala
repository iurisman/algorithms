package igorurisman.algorithms

import scala.reflect.ClassTag

package object dynamicprogramming {

  case class TwoRowFrame[T](length: Int, init: T)(implicit tag: ClassTag[T]) {

    private val rows = Array.fill(2, length)(init)
    private var (prevRow, currRow) = (0, 1)

    def current = rows(currRow)

    def previous = rows(prevRow)

    def advance(): Unit = {
      prevRow = (prevRow + 1) % 2
      currRow = (currRow + 1) % 2
    }
  }

}
