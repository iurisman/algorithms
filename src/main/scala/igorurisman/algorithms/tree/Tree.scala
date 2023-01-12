package igorurisman.algorithms.tree

import scala.util.Random

sealed abstract class Tree[C](val content: C) {

  def iterator: Iterator[C] = new Iterator[C]() {

    private var currNode = Tree.this
    private var path = collection.mutable.Stack.empty[Node[C]]

    override def hasNext: Boolean = nextNode(false).isDefined

    override def next(): C = {
      currNode = nextNode(true).getOrElse(throw new NoSuchElementException())
      currNode.content
    }

    private def nextNode(hard: Boolean): Option[Tree[C]] = {

      var result = Option.empty[Tree[C]]
      val nextPath = path.clone()

      currNode match {
        case Leaf(_) =>
          // Back up the path until we find a node with unvisited children.
          var child = currNode
          while (nextPath.nonEmpty && result.isEmpty) {
            val parent = nextPath.pop()
            parent.children.dropWhile(_ != child).drop(1).headOption match {
              case Some(nextChild) => result = Some(nextChild)
              case None =>
                // All children have been visited. Move up the path.
                child = parent
            }
          }
        case node @ Node(_, children) =>
          // Add current node to path and return first child.
          nextPath.push(node)
          result = Some(children.head)
      }

      if (hard) {
        path = nextPath
      }
      result
    }
  }

  lazy val size: Int = iterator.size

  override def toString: String = {
    Tree.toStringRec(0, this)
  }
}

case class Leaf[C](override val content: C) extends Tree[C](content)

case class Node[C](override val content: C, children: Seq[Tree[C]])
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
    //val t = Tree.fill(10, 5)(Random.nextPrintableChar())
    val t = Node(
      'O',
      Seq(Leaf('T'), Leaf('o'), Leaf('N'))
    )
    println(t)
    t.iterator.foreach(println)
    //println(t.size)
//    println(t.content)
//    println(t.iterator.drop(7))
  }

}