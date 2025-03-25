import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random


class ScratchTest extends AnyWordSpec with Matchers {
  "Scratch" should {
    "Create basic tree" in {
      val tree = new BinSearchTree[Int, String]();
      tree.size mustBe 0
      tree.insert(1 -> "one") mustBe None
      tree.size mustBe 1
      tree.insert(1 -> "one") mustBe Some("one")
      tree.size mustBe 1
      tree.insert(3 -> "three") mustBe None
      tree.size mustBe 2
    }

    "Create fully populated tree" in {
      val tree = new BinSearchTree[Int, String]();
      for (_ <- 1 until 20000) {
        val key = Random.nextInt(20)
        tree.insert(key -> key.toString)
      }
      tree.size mustBe 20

      //println(tree.toString)
//      for (key <- 0 until 20) {
//        tree.delete(key) mustBe key.toString
//      }

    }
  }
}