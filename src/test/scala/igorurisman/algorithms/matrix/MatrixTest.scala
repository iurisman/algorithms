package igorurisman.algorithms.matrix

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random
import scala.util.chaining.*

class MatrixTest extends AnyWordSpec with Matchers {

  "Matrix determinant" should {

    "do well" in {
      val m1 = Matrix.fill(2, 3)((i, j) => Random.nextInt(100) - 50)
      m1.isSquare mustBe false
      m1.rows mustBe 2
      m1.columns mustBe 3
      val m2 = Matrix.fill(3)((i, j) => Random.nextInt(100) - 50)
      m2.isSquare mustBe true
      m2.rows mustBe 3
      m2.columns mustBe 3
      println(m2.determinant())
      println(
        m2(1, 1) * m2(2, 2) * m2(3, 3) + m2(1, 2) * m2(2, 3) * m2(3, 1) + m2(2, 1) * m2(1, 3) * m2(3, 2)
          - m2(3, 1) * m2(2, 2) * m2(1, 3) - m2(1, 1) * m2(2, 3) * m2(3, 2) - m2(2, 1) * m2(1, 2) * m2(3, 3)
      )
    }
  }
}
