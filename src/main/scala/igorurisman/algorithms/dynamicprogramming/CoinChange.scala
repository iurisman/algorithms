package igorurisman.algorithms.dynamicprogramming

/**
 *
 */
object CoinChange extends App {

  /**
   *   Compute number of ways change for the amount A monetary units can be given
   *   in coins of given denominations {d1,...,dm} monetary units.
   *
   *   We consider the table with m + 1 rows — one more than the number of coins.
   *   Starting with the 0th row, representing the artificial, but convenient boundary
   *   case of 0 coins, each subsequent row 0 < k <= m represents a partial coin
   *   set {d1 < d2 < ... < dk}, derived from the previous row's subset by adding the
   *   next coin in ascending order of denomination. The number of columns in the table
   *   is A + 1 — the desired change amount, starting with column 0 representing
   *   the artificial, but convenient boundary case of the change amount A == 0.
   *   Each subsequent column's index 0 < j <= A is the amount in ascending order 1 to A.
   *
   *   We initialize the 0th column with all 0s and the 0th column with all 1s. (The value
   *   of the cell (0,0) is inconsequential because it is not used in the algorithm.)
   *
   *   For example, given the amount A = 5 and coin set {1,2,5}, the initial table is:
   *
   *             0 1 2 3 4 5
   *   ---------------------
   *   {}      | ? 0 0 0 0 0
   *   {1}     | 1
   *   {1,2}   | 1
   *   {1,2,5} | 1
   *
   *   In the general case, the value in the cell C(i,j) is equal to the number of ways
   *   change can be given for the amount j using the coin set {d1...di}. It can be
   *   computed using the following key recurrence relation:
   *
   *   C(i, j) = C(i-1, j) + C(i, j-di)

   *   Here, the value in cell C(i-1,j) (one above C(i,j)) is the number of ways change for j
   *   can be given without the rightmost coin di; and the value in C(i, j-di)
   *   (di columns to the left of C(i,j)), if exists, is the number of ways change can be
   *   given for j - di with the entire coin subset.
   *
   *             0 1 2 3 4 5
   *   ---------------------
   *   {}      | 1 0 0 0 0 0
   *   {1}     | 1 1 1 1 1 1
   *   {1,2}   | 1 1 2 2 3 3
   *   {1,2,5} | 1 1 2 2 3 4
   *
   *  The goal is to get to the bottom-right column, which will contain the number of
   *  ways to give change for 5 with the coin set {1,2,5}.
   *
   *  In practice, it's not necessary to memoize the entire table; keeping just the
   *  current row will suffice. We will make repeated passes over this array,
   *  starting with the key subset {d1} and ending with the full key set {d1,...,dk}.
   */
  def ways(amount: Int, coins: Array[Int]): Long = {

    val memo = Array(1L) ++ Array.fill(amount)(0L)

    for {
      coin <- coins.sorted
      j <- 1 to amount
    } {
        memo(j) += (if (coin > j) 0 else memo(j - coin))
    }

    memo.last
  }

  /**
   *   Compute the min number coins required to give a given amount of change
   *   in coins of given denominations.
   *
   *   We consider the table with m + 1 rows — one more than the number of coins.
   *   Starting with the 0th row, representing the artificial, but convenient boundary
   *   case of 0 coins, each subsequent row 0 < k <= m represents a partial coin
   *   set {d1 < d2 < ... < dk}, derived from the previous row's subset by adding the
   *   next coin in ascending order of denomination. The number of columns in the table
   *   is A + 1 — the desired change amount, starting with column 0 representing
   *   the artificial, but convenient boundary case of the change amount A == 0.
   *   Each subsequent column's index 0 < j <= A is the amount in ascending order 1 to A.
   *
   *   We initialize the 0th row with all ∞s and the 0th column with all 1s. (The value
   *   of the cell (0,0) is inconsequential because it is not used in the algorithm.)
   *
   *   For example, given the amount A = 5 and coin set {1,2,5}, the initial table is:
   *
   *             0 1 2 3 4 5
   *   ---------------------
   *   {}      | ? ∞ ∞ ∞ ∞ ∞
   *   {1}     | 0
   *   {1,2}   | 0
   *   {1,2,5} | 0
   *
   *   In the general case, the value in the cell C(i,j) is equal to the minimum number
   *   of coins needed to give change for the amount j using the coin set {d1...di}.
   *   It can be computed using the following key recurrence relation:
   *
   *   C(i, j) = min (C(i-1, j), C(i, j-di) + 1)

   *   Here, the value in cell C(i-1,j) (one above C(i,j)) is the min number of coins needed
   *   to give change for j; and the value in C(i, j-di) (di columns to the left of C(i,j)),
   *   if exists, is the min number of coins needed to give change for j - di with the
   *   entire coin subset.
   *
   *             0 1 2 3 4 5
   *   ---------------------
   *   {}      | 0 ∞ ∞ ∞ ∞ ∞
   *   {1}     | 0 1 2 3 4 5
   *   {1,2}   | 0 1 1 2 2 3
   *   {1,2,5} | 0 1 1 2 2 1
   *
   *  The goal is to get to the bottom-right column, which will contain the minimum number
   *  of coins required to give change for 5 with the coin set {1,2,5}.
   *
   *  In practice, it's not necessary to memoize the entire table; keeping just the
   *  current row will suffice. We will make repeated passes over this array,
   *  starting with the key subset {d1} and ending with the full key set {d1,...,dk}.
   */

  def minCoins(amount: Int, coins: Array[Int]): Option[Int] = {

    val Inf = Int.MaxValue
    val memo = Array(0) ++ Array.fill(amount)(Inf)

    for {
      coin <- coins.sorted
      j <- 1 to amount
    } {
      memo(j) = memo(j).min {
        if (coin > j || memo(j - coin) == Inf) {
          Inf
        } else {
          memo(j - coin) + 1
        }
      }
    }
    println(memo.mkString(" "))
    memo(amount) match {
      case 0 | Inf => None
      case res => Some(res)
    }
  }
}

