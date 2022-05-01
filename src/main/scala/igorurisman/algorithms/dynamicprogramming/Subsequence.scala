package igorurisman.algorithms.dynamicprogramming

object Subsequence {

  /**
   * Compute the length of the longest increasing subsequence of a given sequence.
   */
  def lisLen[T](seq: Seq[T])(implicit ordering: Ordering[T]): Int = {
    val memo = Array.fill(seq.length)(1)
    for {
      i <- 0 until seq.length
      j <- 0 until i
    } {
      if(ordering.lt(seq(j), seq(i))) {
        memo(i) = memo(i).max(1 + memo(j))
      }
    }
    memo.max
  }

  /**
   * Compute a Longest Increasing Subsequence of a given sequence. If multiple subsequences
   * qualify, compute either.
   */
  def lis[T](seq: Seq[T])(implicit ordering: Ordering[T]): Int = {
    val memo = Array.fill(seq.length)(1)
    for {
      i <- 0 until seq.length
      j <- 0 until i
    } {
      if(ordering.lt(seq(j), seq(i))) {
        memo(i) = memo(i).max(1 + memo(j))
      }
    }
    memo.max
  }

}
