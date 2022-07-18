package igorurisman.algorithms.graph
/*
class Graph[K, V] private(edges: Map[K, Set[K]], vertices: Map[K, V]) {

  implicit val _this = this

  def apply(k: K): Option[V] = vertices.get(k)

  def neighborsOf(k: K): Set[K] = edges(k)

  def edgesOf(k: K): Set[Edge[K]] = edges(k).map(other => Edge(k, other))

  def minPath(from: K, to: K): List[Edge[K]] = {

    val visitedVertices = collection.mutable.Set(from)

    def allPaths(k1: K, k2: K): Set[List[Edge[K]]] = {
      val edges: Set[Edge[K]] =
        edgesOf(k1)
          .filterNot(edge => visitedVertices.contains(edge.oppositeVertexOf(k1)))

      if (edges.isEmpty) {
        // No new vertices left to visit. k2 is unreachable from k1.
        Set()
      } else {
        edges.flatMap { edge =>
          if (edge.oppositeVertexOf(k1) == k2) {
            Set(List(edge))
          } else {
            allPaths(edge.oppositeVertexOf(k1), k2)
              .filter(_.nonEmpty)
              .map(path => edge +: path)
          }
        }
      }
    }

    allPaths(from, to)
      .reduce { (l1, l2) =>
        val weight1 = l1.foldLeft(0){case (acc, edge) => acc + edge.weight}
        val weight2 = l2.foldLeft(0){case (acc, edge) => acc + edge.weight}
        if (weight1 > weight2) l1 else l2
      }
  }
}

object Graph {


  /** Fill with the default edge weight **/
  def fill[K, V](edges: Map[K, Set[K]])(f: K => V): Graph[K, V] = {

    val allKeys: Set[K] = edges.keys.toSet ++ edges.values.toSet.reduce(_++_)

    val allEdges: Map[K, Set[K]] =
      allKeys.map { key =>
        key -> (edges.getOrElse(key, Set.empty) ++ edges.filter(_._2.contains(key)).map(_._1).toSet)
      }.toMap

    val vertices = allKeys.map(k => k -> f(k)).toMap

    new Graph(allEdges, vertices)
  }

  /** Fill with the given edge weight **/
  def fill[K, V](edges: Map[K, Set[(K, Int)]])(f: K => V): Graph[K, V] = {

    val allKeys: Set[K] = edges.keys.toSet ++ edges.values.toSet.map(
      _.map(_._1)).reduce(_++_)

    val allEdges: Map[K, Set[K]] =
      allKeys.map { key =>
        key -> (edges.getOrElse(key, Set.empty) ++ edges.filter(_._2.contains(key)).map(_._1).toSet)
      }.toMap

    val vertices = allKeys.map(k => k -> f(k)).toMap

    new Graph(allEdges, vertices)
  }

}

 */