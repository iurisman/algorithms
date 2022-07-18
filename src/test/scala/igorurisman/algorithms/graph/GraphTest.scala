package igorurisman.algorithms.graph

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GraphTest extends AnyWordSpec with Matchers {

/*
  "UndirectedGraph" should {

    "create graph" in {

      implicit val graph = Graph.fill(
        Map(
          1 -> Set(2,3),
          2 -> Set(3)
        ) { _

        }
      )

      graph(1) mustBe Some("1")
      graph(0) mustBe None
      graph(2) mustBe Some("2")
      graph(3) mustBe Some("3")
      graph(4) mustBe None

      graph.neighborsOf(1) mustBe Set(2,3)
      graph.neighborsOf(2) mustBe Set(3,1)
      graph.neighborsOf(3) mustBe Set(1,2)

      graph.edgesOf(1) mustBe Set(Edge(1,2), Edge(1,3))
      graph.edgesOf(2) mustBe Set(Edge(1,2), Edge(2,3))
      graph.edgesOf(3) mustBe Set(Edge(1,3), Edge(2,3))

    }
  }

  "find min path" in {

    implicit val graph = Graph.fill(
      Map(
        1 -> Set(2, 3),
        2 -> Set(3)
      )
    ) { v => String.valueOf(v) }

    println(graph.minPath(1,2))
  }
*/
}
