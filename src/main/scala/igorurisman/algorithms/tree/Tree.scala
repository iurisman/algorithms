package igorurisman.algorithms.tree

import scala.util.Random

sealed abstract class Tree[C](val content: C) {

  def iterator: Iterator[C] = new Iterator[C]() {

    private var currNode: Option[Tree[C]] = None
    private var nextNode: Option[Tree[C]] = Some(Tree.this)
    private var path = collection.mutable.Stack.empty[Node[C]]

    override def hasNext: Boolean = nextNode.isDefined

    override def next(): C = {
      if (nextNode.isEmpty) throw new NoSuchElementException()
      currNode = nextNode
      nextNode = computeNextNode()
      currNode.get.content
    }

    private def computeNextNode(): Option[Tree[C]] = {
      var result = Option.empty[Tree[C]]
      currNode.get match {
        case leaf @ Leaf(_) =>
          // Back up the path until we find a node with unvisited children.
          var child: Tree[C] = leaf
          while (path.nonEmpty && result.isEmpty) {
            val parent = path.pop()
            parent.children.dropWhile(_ != child).drop(1).headOption match {
              case Some(nextChild) =>
                result = Some(nextChild)
                path.push(parent)
              case None =>
                // All children have been visited. Move up the path.
                child = parent
            }
          }
        case node @ Node(_, children) =>
          // Add current node to path and return first child.
          path.push(node)
          result = Some(children.head)
      }
      result
    }
  }

  lazy val size: Int = iterator.size

  override def toString: String = {
    Tree.toStringRec(0, this)
  }
}

case class Leaf[C](override val content: C) extends Tree[C](content) {
  override def equals(other: Any): Boolean = super.equals(other)
}

case class Node[C](override val content: C, children: Seq[Tree[C]]) extends Tree[C](content) {
  override def equals(other: Any): Boolean = super.equals(other)
}

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
      // In case we allocated more children than we need:
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
    val size = 10000000
    val t = Tree.fill(size, 5)(Random.nextPrintableChar())
    // println(t)
    println(t.iterator.size)
    println(t.iterator.drop(7).isEmpty)
    println(t.iterator.drop(size-1).isEmpty)
    println(t.iterator.drop(size).isEmpty)
  }
}