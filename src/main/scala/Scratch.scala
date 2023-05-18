import scala.collection.mutable

object Scratch extends App {

}

case class Coordinate(x: Int, y: Int) {
  def distanceFrom(other: Coordinate): Double = {
    math.sqrt(math.pow(x-other.x, 2) + math.pow(y-other.y, 2))
  }
}
case class Tower(coord: Coordinate, radius: Double) {
  def inRange(c: Coordinate): Boolean = coord.distanceFrom(c) <= radius

  def overlap(other: Tower): Boolean = {
    ???
  }
}
case class Network(towers: Set[Tower]) {

  def towerFor(c: Coordinate): Option[Tower] = {
    towers.find(_.inRange(c))
  }
  def toGraph(): Graph = {

    val towersAsArray = towers.toArray
    val matrix:Array[Array[Boolean]] = Array.tabulate(towersAsArray.length, towersAsArray.length) {
      (i,j) => towersAsArray(i).overlap(towersAsArray(j))
    }
    Graph(towersAsArray, matrix)
  }
  def reachable(a: Coordinate, b: Coordinate): Boolean = {
    (towerFor(a), towerFor(b)) match {
      case (Some(ta), Some(tb)) => toGraph().pathExists(ta, tb)
      case _ => false
    }
  }
}

case class Graph(towers: Array[Tower], flags: Array[Array[Boolean]]) {
  def pathExists(t1: Tower, t2: Tower): Boolean = {
    ???
  }
}
