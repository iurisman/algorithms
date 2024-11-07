package igorurisman.algorithms.matrix

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.chaining._
class SkipIndexTest extends AnyWordSpec with Matchers {

  "SkipIndex" should {

    "do well" in {
      val six = new SkipIndex(5, 0)
      six.toString mustBe "0, 1, 2, 3, 4"
      six.length mustBe 5
      six.skip(3)
      six.toString mustBe "0, 1, 2, 4, -1"
      six.length mustBe 4
      six.skip(2)
      six.toString mustBe "0, 1, 4, -1, -1"
      six.length mustBe 3
      six.skip(2)
      six.toString mustBe "0, 1, -1, -1, -1"
      six.length mustBe 2

      intercept[Exception] {
        six.skip(2)
      } pipe {
        ex => ex.getMessage mustBe "Bad index 2"
      }

      intercept[Exception] {
        six.skip(-1)
      } pipe {
        ex => ex.getMessage mustBe "Bad index -1"
      }

      six.skip(1)
      six.toString mustBe "0, -1, -1, -1, -1"
      six.length mustBe 1

      six.skip(0)
      six.toString mustBe "-1, -1, -1, -1, -1"
      six.length mustBe 0
    }

    "do well with offset" in {
      val six = new SkipIndex(3, 1)
      intercept[Exception] {
        six.skip(0)
      } pipe {
        ex => ex.getMessage mustBe "Bad index 0"
      }
      six.skip(1)
      six.length mustBe 2
      six.toString mustBe "2, 3, -1"
    }

    "do well with offmultiple skips" in {
      val six = new SkipIndex(3, 1)
      six.skip(2)
      six.length mustBe 2
      six.toString mustBe "1, 3, -1"
      six.skip(1)
      six.length mustBe 1
      six.toString mustBe "3, -1, -1"
    }

  }
}
