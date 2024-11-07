package igorurisman.algorithms.matrix

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random
import scala.util.chaining.*

class MatrixTest extends AnyWordSpec with Matchers {

  "Matrix determinant" should {

    "Create" in {
      val m = Matrix.fill(100, 300)((i, j) => Random.nextInt(100) - 50)
      m.isSquare mustBe false
      m.rows mustBe 100
      m.columns mustBe 300
    }

    "Drop" in {
      val m = Matrix.fill(100, 300)((i, j) => Random.nextInt(100) - 50)
      val m1 = m.drop(1,1)
      m1.isSquare mustBe false
      m1.rows mustBe 99
      m1.columns mustBe 299
      m1(1,1) mustBe m(2,2)
      m1(99,299) mustBe m(100,300)
    }

    "Compute det" in {

      for (_ <- 1 to 1000) {
        val m = Matrix.fill(2)((i, j) => Random.nextInt(100) - 50)
        val det = m(1, 1) * m(2, 2) - m(1, 2) * m(2, 1)
        if (m.det != det) {
          println(m)
          println(s"Expected:  $det")
          println(s"Actual: ${m.det}")
          fail("Test failed")
        }
      }

      for (_ <- 1 to 1000) {
        val m = Matrix.fill(3)((i, j) => Random.nextInt(100) - 50)
        val det = m(1, 1) * m(2, 2) * m(3, 3) + m(1, 2) * m(2, 3) * m(3, 1) + m(2, 1) * m(1, 3) * m(3, 2)
          - m(3, 1) * m(2, 2) * m(1, 3) - m(1, 1) * m(2, 3) * m(3, 2) - m(2, 1) * m(1, 2) * m(3, 3)

        if (m.det != det) {
          println(m)
          println(s"Expected:  $det")
          println(s"Actual: ${m.det}")
          fail("Test failed")
        }
      }
    }
  }
}
