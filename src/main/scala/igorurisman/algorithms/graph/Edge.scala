package igorurisman.algorithms.graph
/*
case class Edge[K] private(vertices: Set[K], weight: Int = 0)(implicit graph: Graph[K,_]) {

  if (vertices.size != 2)
    throw new IllegalArgumentException(s"Edge cannot have ${vertices.size} vertices")

  if (!graph.neighborsOf(vertices.head).contains(vertices.last))
    throw new IllegalArgumentException(s"Vertice ${vertices.head} is not a neighbor of ${vertices.last}")

  def oppositeVertexOf(k: K) = if (k == vertices.head) vertices.last else vertices.head
}

object Edge {

  def apply[K](k1: K, k2: K)(implicit graph: Graph[K,_]):Edge[K] = apply(Set(k1,k2))

  def apply[K](k1: K, k2: K, weight: Int)(implicit graph: Graph[K,_]):Edge[K] = apply(Set(k1,k2), weight)

}

*/