package igorurisman.algorithms.dynamicprogramming

object Levenshtein {

  /**
   * Compute the Levenshtein distance between two sequences S1 and S2 of elements form a domain D,
   * defined as the min number of single-element operations required to turn S1 into S2.
   * The single-element operation can be
   *   Deletion -- removal of an existing element
   *   Insertion -- addition of a new element
   *   Replacement -- changing of an existing element with an arbitrary character from D.
   * Typically applied to strings, where D == an alphabet.
   *
   * Let n1 be the length of S1 and n2 be the length of S2. We consider the memo table with n1+1 rows
   * and n2+1 columns, with the 0th row and 0th column are initialized with their respective indices,
   * representing a convenient boundary case. For the sequences S1={brother} and S2={sister}
   *
   *     s i s t e r
   *   0 1 2 3 4 5 6
   * b 1
   * r 2
   * o 3
   * t 4
   * h 5
   * e 6
   * r 7
   *
   * The initial values represent the cost of solving the (i,j) problem where either i or j == 0,
   * i.e. one of the sequences is empty. Clearly, in that case the cost of the solution is to delete all the
   * elements from the non-empty sequence.
   *
   * In the general case, the value in the cell (i,j) is computed using the following
   * recurrence relation:
   *
   * C(i,j) = if (S1(i) == S2(j))  // The current elements are the same -- 0 new cost
   *            C(i-1, j-1)
   *          else
   *            min(
   *              1 + C(i-1,j),   // Deletion of S1(i), shifting the rest of S1 left by one.
   *              1 + C(i,j-1),   // Insertion of S2(j) into ith index i, shifting rest of S1 right by one.
   *              1 + C(i-1,j-1)  // Replacement of S1(i) element with S2(j)
   *            )
   *
   *     s i s t e r
   *   0 1 2 3 4 5 6
   * b 1 1
   * r 2
   * o 3
   * t 4
   * h 5
   * e 6
   * r 7
   *
   * Explanations:
   *  Cell   | Explanation                       | Modified string S1
   *  C(1,1) | replaced b with s                 | srother
   *  C(1,2) |
   *
   * Running time complexity O(n*m), space complexity O(m)
   */
  def lev[T](xs: Seq[T], ys: Seq[T]): Int = {

    val distance = Array.tabulate(xs.length + 1, ys.length + 1) { case (ix, iy) =>
      if (ix == 0) iy          // 0th row
      else if (iy == 0) ix     // 0th column
      else -1                  // Doesn't matter
    }

    //distance.foreach(arr => println(arr.mkString(" ")))

    for {
      i <- 1 to xs.length
      j <- 1 to ys.length
    } {
      distance(i)(j) =
        if (xs(i-1) == ys(j-1)) {
          distance(i-1)(j-1)
        } else {
          1 + distance(i-1)(j).min(distance(i)(j-1)).min(distance(i-1)(j-1))
        }
    }

    distance(xs.length)(ys.length)
  }

}