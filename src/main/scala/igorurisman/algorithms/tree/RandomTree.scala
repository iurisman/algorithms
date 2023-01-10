package igorurisman.algorithms.tree

import scala.util.Random
import scala.util.chaining._
import scala.language.implicitConversions

class RandomTree[C](tree: Tree[C]) {

  def get: C = tree.get

  /** Random element's value from the tree rooted in this elem */
  def getRand: RandomTree[C] = {
    (tree match {
      case Leaf(_) => tree
      case Node(_,_) =>
        val ix = Random.nextInt(tree.size)
        tree.get(ix).get

    }) pipe { new RandomTree(_) }
  }
}
object RandomTree {

  def main(args: Array[String]): Unit = {
    val t = Tree.fill(100000000, 25)(Random.nextPrintableChar())
    println(t.size)
    val rt = new RandomTree(t)
    for (_ <- 0 until 10) println(rt.getRand.get)
  }
}
