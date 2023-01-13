package igorurisman.algorithms.tree

import scala.util.Random

class RandomTree[C](tree: Tree[C]) {

  /** Random element's value from the tree rooted in this elem */
  def randomElement: C = {
    tree match {
      case Leaf(_) => tree.content
      case Node(_,_) =>
        val ix = Random.nextInt(tree.size)
        tree.iterator.drop(ix).next

    }
  }
}
object RandomTree {

  def main(args: Array[String]): Unit = {
    val t = Tree.fill(10000000, 25)(Random.nextPrintableChar())
    println(t.size)
    val rt = new RandomTree(t)
    for (_ <- 0 until 10) println(rt.randomElement)
  }
}
