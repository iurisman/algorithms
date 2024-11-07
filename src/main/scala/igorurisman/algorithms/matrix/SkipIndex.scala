package igorurisman.algorithms.matrix

/**
 * N-based index skippable index.
 * @param initLen
 */
class SkipIndex(initLen: Int, base: Int) {
  private val indices = new Array[Int](initLen)
  for (i <- 0 until initLen) indices(i) = base + i
  private var _length = initLen

  def copy: SkipIndex = {
    val result = new SkipIndex(initLen, base)
    Array.copy(this.indices, 0, result.indices, 0, initLen)
    result._length = this._length
    result
  }
  def apply(ix: Int): Int = {
    if (ix < base || ix >= base + _length) {
      throw new Exception(s"Bad index $ix")
    }
    indices(ix - base)
  }
  def length = _length
  def skip(ix: Int): Unit = {
    if (ix < base || ix >= base + _length) {
      throw new Exception (s"Bad index $ix")
    }
    _length = _length - 1
    // ith elem gets the value from its right neighbor
    for (i <- ix - base until _length) {
      indices(i) = indices(i+1)
    }
    // The rightmost element is marked invalid.
    indices(_length) = -1
  }

  override def toString: String = {
    indices.mkString(", ")
  }
}
