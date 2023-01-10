package igorurisman.algorithms.tree

import scala.util.Random
/*
sealed abstract class RandomTree[C](content: C) {
  /** This elem's value */
  val get: C = content

  /** Get n-th element in depth first order. 0-based. */
  private def get(ix: Int): Option[RandomTree[C]] = {
    if (ix == 0) Some(this)
    else {
      this match {
        case Leaf(_) => None
        case Node(_, children) =>
          for ((c, i) <- children.zipWithIndex) {
            c.get(ix - i - 1) match {
              case Some(elem) => return Some(elem)
              case None =>
            }
          }
          None
      }
    }

  }

  /** Random element's value from the tree rooted in this elem */
  def getRand: RandomTree[C] = this match {
    case Leaf(_) => this
    case Node(_,_) =>
      val ix = Random.nextInt(size)
      get(ix).get

  }

  def size: Int = this match {
    case Leaf(_) => 1
    case Node(_, children) => children.foldLeft(1) {
      case (acc, elem) => acc + elem.size
    }
  }
}
object RandomTree {

  def main(args: Array[String]): Unit = {
    val t = Node(
      1,
      Seq(Leaf(2), Leaf(3))
    )
    println(t.getRand)
  }
}
case class Leaf[C](private val content: C)
  extends RandomTree[C](content)

case class Node[C](private val content: C, children: Seq[RandomTree[C]])
  extends RandomTree[C](content)

*/