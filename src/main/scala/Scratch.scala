import scala.util.chaining._

object Scratch extends App {}

class Node[K,V](private var data: (K,V),
                private var left: Option[Node[K,V]] = None,
                private var right: Option[Node[K,V]] = None)
               (implicit ordering: Ordering[K]) {

  private var _size = 1

  def size = _size

  def insert(newData: (K,V)): Option[V] = {
    if (ordering.compare(newData._1, data._1) < 0)  {
      val result = left match {
        case None =>
          left = Some(Node(newData))
          None
        case Some(child) =>
          child.insert(newData)
      }
      if (result.isEmpty) _size = _size + 1
      result
    }
    else if (ordering.compare(newData._1, this.data._1) > 0) {
      val result = right match {
        case None =>
          right = Some(Node(newData))
          None
        case Some(child) =>
          child.insert(newData)
      }
      if (result.isEmpty) _size = _size + 1
      result
    }
    else {
      val oldValue = data._2
      this.data = newData
      Some(oldValue)
    }
  }

  /**
   * @return Left instructs the caller to replace this child with the wrapped optional node and return this child's data.
   *         Right means that some recursive call down the stack figured out the result and the caller is to propagate
   *         it up the stack.
   *
  def delete(key: K): Either[Option[Node[K,V]], Option[V]] = {
    if (ordering.compare(key, data._1) < 0) {
      left match {
        case None =>
          None
        case Some(child) =>
          if (ordering.compare(key, child.data._1) >

      }
    }
    else if (ordering.compare(newData._1, this.data._1) > 0) {
      right match {
        case None =>
          right = Some(Node(newData))
          None
        case Some(child) =>
          child.insert(newData)
      }
    }
    else /* == 0 This is the node we're after */ {
      // It doesn't matter which child we pick

      Left(Some(this))
    }
  }
  */
  override def toString: String = {
    new StringBuilder()
      .append("{")
      .append(data).append(", ")
      .append(left.map(_.toString).getOrElse("{}")).append(", ")
      .append(right.map(_.toString).getOrElse("{}")).append(", ")
      .append(size)
      .append("}")
      .toString()
  }

}

object Node {
  def apply[K,V](data: (K,V))(implicit ordering: Ordering[K]): Node[K,V] = new Node(data)
}

class BinSearchTree[K,V] private (var rootOpt: Option[Node[K,V]])(implicit foo: Ordering[K]) {

  def this()(implicit foo: Ordering[K]) = this(None)

  def size: Int = rootOpt.map(_.size).getOrElse(0)

  /** Returns Some of the replaced value or None if this key did not already exist */
  def insert(data: (K,V)): Option[V] = {
    rootOpt match {
      case None =>
        rootOpt = Some(Node(data))
        None
      case Some(root) =>
        root.insert(data)
    }
  }

//  def delete(key: K): Option[V] = {
//    rootOpt.flatMap(_.delete(key))
//  }

  override def toString: String = {
    rootOpt match {
      case None => "{}"
      case Some(root) => root.toString
    }
  }
}
