package igorurisman.algorithms.tree

import scala.util.Random

sealed abstract class Tree[C](content: C) {
  /** This elem's value */
  val get: C = content

  def foreach(f: Tree[C] => Unit): Unit = {
    f(this)
    this match {
      case Leaf(_) =>
      case Node(_, children) => children.foreach(_.foreach(f))
    }
  }
  /** Get n-th element in depth first order. 0-based. */
  def get(ix: Int): Option[Tree[C]] = {
    var count = 0
    foreach {
      node =>
        if (ix == count) return Some(node)
        else count += 1
    }
    None
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
    if (size == 1) {
      Leaf(value)
    } else if (size > 1) {
      var degree = 1 + Random.nextInt(maxDegree)
      // each child should have roughly equal size
      val childSizes = Array.fill(degree)(0)
      for (i <- 0 until (size - 1)) childSizes(i % degree) += 1
      // In case we allocated more children then we need:
      degree = childSizes.count(_ > 0)
      val children = new Array[Tree[C]](degree)
      for (i <- 0 until degree) {
        children(i) = fill(childSizes(i), maxDegree)(value)
      }
      Node(value, children)
    } else {
      throw new Exception(s"Size must be > 0, but was $size")
    }
  }

  private def toStringRec[C](indent: Int, tree: Tree[C]): String = {
    tree match {
      case Leaf(c) => " " * indent + s"Leaf($c)"
      case Node(c, children) =>
        " " * indent + s"Node($c," + "\n" +
          children.map(toStringRec(indent + 1, _)).mkString("",",\n","\n") +
        " " * indent + ")"
    }
  }

  def main(args: Array[String]): Unit = {
    val t = Tree.fill(10, 5)(Random.nextPrintableChar())
    //println(t)
    //println(t.size)
    //println(t.get)
    //t.foreach(t => println(t.get))
    //println(t.get(8).map(_.get).getOrElse("None"))
  }

}