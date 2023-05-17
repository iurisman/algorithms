package byte_by_byte_com

import java.util.concurrent.atomic.AtomicInteger
import scala.util.Random

/** Implement a simple binary tree which supports a `getRandom()` method
 * returning a node of this tree selected at random with a constant probability
 * distribution of 1/size of the tree. This method should have complexity O(log(size)).
 */
sealed trait BinTree[T] {
  val size: Int
  val content: T
  def randomNode(): BinTree[T] = {
    val random = Random()
    def randomNodeRec(tree: BinTree[T]): BinTree[T] = {
      tree match {
        case Leaf(_) => tree
        case Node(_, left, right, size) =>
          val median = random.nextInt(size)
          if (median == 0) {
            tree
          } else {
            (left, right) match {
              case (None, None) => throw new RuntimeException("Childless node found");
              case (Some(l), None) => randomNodeRec(l)
              case (None, Some(r)) => randomNodeRec(r)
              case (Some(l), Some(r)) =>
                if (median <= l.size) randomNodeRec(l)
                else randomNodeRec(r)
            }
          }
      }
    }
    randomNodeRec(this)
  }
//  override def toString(): String = {
//    def toStringRec(indent: Int, tree: BinTree[T]): String = {
//      tree match {
//      case Leaf(content) =>
//        " "*indent + s"Leaf($content)"
//      case node@Node(content, left, right, size) =>
//        " "*indent + s"Node(\n" +
//          " "*(indent+2) + content + "\n" +
//          " "*indent + node.left.map(l => toStringRec(indent + 2, l)).getOrElse("  —") + "\n" +
//          " "*indent + "  ," + "\n" +
//          " "*indent + node.right.map(r => toStringRec(indent + 2, r)).getOrElse("  —") + "\n" +
//          " "*indent + s"  )"
//      }
//
//    }
//    toStringRec(0, this)
//  }
}

case class Node[T](content: T, left: Option[BinTree[T]], right: Option[BinTree[T]], override val size: Int) extends BinTree[T]

case class Leaf[T](content: T) extends BinTree[T] {
  override val size: Int = 1;
}

object BinTree {

  /** Build a binary tree of given size and content
   * Even though the topology of the tree is computed randomly,
   * repeated calls will generate identical trees. If you want
   * a different topology of the same size, pass `seed` explicitly.
   */
  def fill[T](size: Int, content: () => T): BinTree[T] = {
    val random = Random(size)
    if (size <= 0) {
      throw new IllegalArgumentException(s"Size $size is too small")
    } else if (size == 1) {
      Leaf(content())
    } else {
      val sizeOnLeft = random.nextInt(size)
      if (sizeOnLeft == 0) {
        Node(content(), None, Some(fill(size-1, content)), size)
      } else if (sizeOnLeft == size-1) {
        Node(content(), Some(fill(size-1, content)), None, size)
      } else {
        Node(content(), Some(fill(sizeOnLeft, content)), Some(fill(size-sizeOnLeft-1, content)), size)
      }
    }
  }
}

object Main extends App {
  val count = new AtomicInteger(0)
  val tree = BinTree.fill(10000, () => count.addAndGet(1))
  val map = scala.collection.mutable.Map.empty[Int, Int]
  for (_ <- 0 to 1000) {
    val ix = tree.randomNode().content
    val counter = map.getOrElseUpdate(ix, 0) + 1
    map.put(ix, counter)
  }

  println(map.values)
}