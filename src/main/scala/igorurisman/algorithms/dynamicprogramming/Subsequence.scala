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
   *       a c b c e d
   *  i j  Memo
   *  0    1 1 1 1 1 1
   *  1 0  1 2 1 1 1 1
   *  2 0  1 2 2 1 1 1
   *  2 1  1 2 2 1 1 1
   *  3 0  1 2 2 2 1 1
   *  3 1  1 2 2 2 1 1
   *  3 2  1 2 2 3 1 1
   *  4 0  1 2 2 3 2 1
   *  4 1  1 2 2 3 3 1
   *  4 2  1 2 2 3 3 1
   *  4 3  1 2 2 3 4 1
   *  5 0  1 2 2 3 4 2
   *  5 1  1 2 2 3 4 3
   *  5 2  1 2 2 3 4 3
   *  5 3  1 2 2 3 4 4
   *  5 4  1 2 2 3 4 4
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

}
