package igorurisman.algorithms.tree

import scala.util.Random

sealed abstract class Tree[C](content: C) {
  /** This elem's value */
  val get: C = content

  /** Get n-th element in depth first order. 0-based. */
  private def get(ix: Int): Option[Tree[C]] = {
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

  def size: Int = this match {
    case Leaf(_) => 1
    case Node(_, children) => children.foldLeft(1) {
      case (acc, elem) => acc + elem.size
    }
  }

  override def toString: String = {

    Tree.toStringRec(0, this)
  }
}

case class Leaf[C](private val content: C)
  extends Tree[C](content)

case class Node[C](private val content: C, children: Seq[Tree[C]])
  extends Tree[C](content)

object Tree {
  /** Generate a tree of total given size with the random node degree
   * between 1 and given maxDegree (inclusive) */
  def fill[C](size: Int, maxDegree: Int)(value: => C): Tree[C] = {
    assert(size > 0)
    if (size == 1) {
      Leaf(value)
    } else {
      var degree = 1 + Random.nextInt(maxDegree)
      var children = new Array[Tree[C]](degree)
      // each child should have roughly equal size
      val childSizes = Array.fill(degree)(0)
      for (i <- 0 until size) childSizes(i % degree) += 1
      // In case we allocated more children then we need:
      degree = childSizes.count(_ > 0)
      children = children.slice(0, degree)
      for (i <- 0 until degree) {
        children(i) = fill(childSizes(i), maxDegree)(value)
      }
      Node(value, children)
    }
  }
  def main(args: Array[String]): Unit = {
    val t = Tree.fill(10, 2)(Random.nextPrintableChar())
    println(t)
  }

  private def toStringRec[C](indent: Int, tree: Tree[C]): String = {
    val result = tree match {
      case Leaf(c) => "| " * indent + "∟-" + c
      case Node(c, children) =>
        "| " * indent + "∟-" + c + "\n" +
          children.map(toStringRec(indent + 1, _)).mkString("\n")
    }
    result
  }
}