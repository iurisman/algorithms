package igorurisman.algorithms.dynamicprogramming

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class LevenshteinTest extends AnyWordSpec with Matchers {


  s"$Levenshtein" should {

    "compute distance" in {

      println(Levenshtein.lev("brother", "sister"))

    }

  }
}
