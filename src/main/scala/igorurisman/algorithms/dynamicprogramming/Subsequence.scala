package igorurisman.algorithms.dynamicprogramming

object Subsequence {

  /**
   * Compute the length of the Longest Increasing Subsequence of a given sequence S1,S2,...,Sn.
   *
   * The momo structure is an Int array of the same length as the input sequence, which we initialize
   * with all 1s. At the end of the algorithm, Memo(i) will contain the length of the longest increasing
   * subsequence of the first i<=n characters S1,...,Si of the given sequence.
   *
   * This algorithm has running time complexity O(n²). There's an optimization of this algorithm that is
   * (typically) faster, offered by Hunt and Szymanski in 1977, as described in
   * https://en.wikipedia.org/wiki/Longest_increasing_subsequence
   *
   * a c b c e d
   * i j  Memo
   * 0    1 1 1 1 1 1
   * 1 0  1 2 1 1 1 1
   * 2 0  1 2 2 1 1 1
   * 2 1  1 2 2 1 1 1
   * 3 0  1 2 2 2 1 1
   * 3 1  1 2 2 2 1 1
   * 3 2  1 2 2 3 1 1
   * 4 0  1 2 2 3 2 1
   * 4 1  1 2 2 3 3 1
   * 4 2  1 2 2 3 3 1
   * 4 3  1 2 2 3 4 1
   * 5 0  1 2 2 3 4 2
   * 5 1  1 2 2 3 4 3
   * 5 2  1 2 2 3 4 3
   * 5 3  1 2 2 3 4 4
   * 5 4  1 2 2 3 4 4
   */
  def lisLen[T](seq: Seq[T])(implicit ordering: Ordering[T]): Int = {
    val length = Array.fill(seq.length)(1)
    for {
      i <- 0 until seq.length
      j <- 0 until i
    } {
      if (ordering.lt(seq(j), seq(i))) {
        length(i) = length(i).max(1 + length(j))
      }
    }
    length.max
  }

  /**
   * Compute a Longest Increasing Subsequence of a given sequence. If multiple subsequences
   * qualify, compute either.  We adapt the above [[lisLen()]] method by recording the character
   * whenever we increment Memo(i)
   */
  def lis[T](seq: Seq[T])(implicit ordering: Ordering[T]): Seq[T] = {

    val length = Array.fill(seq.length)(1)
    val prevIx = Array.fill(seq.length)(-1)

    for {
      i <- 0 until seq.length
      j <- 0 until i
    } {
      if (ordering.lt(seq(j), seq(i)) && length(i) < length(j) + 1) {
        length(i) = length(j) + 1
        // No previous index (denoted as -1), if the length for this index i is 1.
        prevIx(i) = j
      }
    }

    // The index of the max length.
    val maxIx = length.zipWithIndex.maxBy(_._1)._2

    // Reconstruct the max subseq by walking back the prevIx array.
    val result = collection.mutable.ListBuffer[T]()
    var ix = maxIx
    do {
      seq(ix) +=: result
      ix = prevIx(ix)
    } while (ix >= 0)

    result.toSeq
  }


  /**
   * Compute the length of the Longest Common Subsequence of two given sequences.
   * Let X={xi}, i=1..n and Y={yj}, j=1..m be the two given sequences.
   * We consider the table with n+1 rows and m+1 columns, with the 0th row and 0th column
   * are initialized with 0s, representing a convenient boundary case. For the sequences
   * X={m,o,t,h,e,r} and Y={tether}
   *
   *     d o e s
   *   0 0 0 0 0
   * m 0
   * o 0
   * w 0
   * e 0
   * r 0
   *
   * In the general case, the value in the cell (i,j) is computed using the following
   * recurrence relation:
   *
   * C(i,j) = | IF X(i) == Y(j) THEN 1 + C(i-1, j-1)         // IF current elements are the same, the length is one more
   * |                                              // than already computed value for the two prefixes, ending
   * |                                              // exclusive of the current value.
   * | ELSE            max(C(i, j-1), C(i-1, j))    // OTHERWISE, pick the largest of the two already computed
   * // values for the two prefixes, inclusive of the current values.
   *
   *     d o e s
   *   0 0 0 0 0
   * m 0 0 0 0 0
   * o 0 0 1 1 1
   * w 0 0 1 1 1
   * e 0 0 1 2 2
   * r 0 0 1 2 2
   *
   * In practice, it's not necessary to memoize the entire table; keeping just the
   * current and the previous row is sufficient, as implemented by [[TwoRowFrame]] class.
   *
   * Running time complexity O(n*m), space complexity O(m)
   */
  def lcsLen[T](xs: Seq[T], ys: Seq[T]): Int = {

    val length = TwoRowFrame(ys.length + 1)

    for (i <- 0 until xs.length) {
      for (j <- 0 until ys.length) {
        length.current(j + 1) =
          if (xs(i) == ys(j)) {
            length.previous(j) + 1
          } else {
            length.previous(j + 1).max(length.current(j))
          }
      }
      length.advance()
    }

    length.previous(ys.length)
  }

  case class TwoRowFrame[T](length: Int) {
    private val rows = Array.fill(2, length)(0)
    private var (prevRow, currRow) = (0, 1)

    def current = rows(currRow)

    def previous = rows(prevRow)

    def advance(): Unit = {
      prevRow = (prevRow + 1) % 2
      currRow = (currRow + 1) % 2
    }
  }

  /**
   * Compute a Longest Common Subsequence of two given sequences.
   * We can reuse the algorithm for [[lcsLen()]], but we'll need the
   * entire matrix to work backwards from bottom right to top left
   * in order to reconstruct the resulting subsequence.
   *
   * Running time and space complexity O(n*m)
   */
  def lcs[T](xs: Seq[T], ys: Seq[T]): Seq[T] = {

    val length = Array.fill(xs.size + 1, ys.size + 1)(0)

    for {
      i <- 1 to xs.length
      j <- 1 to ys.length
    } {
      length(i)(j) =
        if (xs(i-1) == ys(j-1)) {
          length(i-1)(j-1) + 1
        } else {
          length(i-1)(j).max(length(i)(j-1))
        }
    }

    val result = collection.mutable.ListBuffer.empty[T]

    var (i, j) = (xs.length, ys.length)
    while (length(i)(j) > 0) {
      if (length(i)(j) == length(i-1)(j-1)) {
        // Irrelevant element, move diagonally
        i -= 1; j -= 1
      } else if (length(i)(j) == length(i-1)(j)) {
        // Irrelevant element, move up
        i -= 1
      } else if (length(i)(j) == length(i)(j-1)) {
        // Irrelevant element, move left
        j -= 1
      } else {
        // Found common element!
        xs(i-1) +=: result
        i -= 1; j -= 1 // move diagonally
      }
    }
    result.toSeq
  }
}