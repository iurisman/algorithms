package igorurisman.algorithms.dynamicprogramming

/**
 *
 */
object Knapsack extends App {

  case class Item(weight: Int, value: Int) {
    def maxByValue(other: Item): Item = if (other.value > value) other else this
  }

  /**
   *   Compute the max value we can pack into a knapsack of weight capacity W, given
   *   a list of n items (wᵢ,vᵢ), 0 < i < n, where wᵢ denotes the items weight and vᵢ
   *   denotes the item's value. Each item is available in unlimited number of copies.
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
   *   For example, given the weight limit W = 9 and items {(1,1),(2,3),(5,10)}, the initial table is:
   *
   *                         0    1     2     3     4     5     6     7     8     9     10
   *   --------------------------------------------------------------------------------------
   *   {}                  | 0    0     0     0     0     0     0     0     0     0     0
   *   {(1,5)}             | 0    1,1   2,2   3,3   4,4   5,5   6,6   7,7   8,8   9,9   10,10
   *   {(1,5),(2,3)}       | 0    1,1   2,3   3,4   4,6   5,7   6,9   7,10  8,12  9,13  10,15
   *   {(1,5),(2,3),(5,7)} | 0    1,1   2,3   3,4   4,6   5,7   6,9   7,11  8,12  9,13  10,15
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
   *                         0    1     2     3     4     5     6     7     8     9     10
   *   --------------------------------------------------------------------------------------
   *   {}                  | 0    0     0     0     0     0     0     0     0     0     0
   *   {(1,5)}             | 0    1,1   2,2   3,3   4,4   5,5   6,6   7,7   8,8   9,9   10,10
   *   {(1,5),(2,3)}       | 0    1,1   2,3   3,4   4,6   5,7   6,9   7,10  8,12  9,13  10,15
   *   {(1,5),(2,3),(5,7)} | 0    1,1   2,3   3,4   4,6   5,7   6,9   7,11  8,12  9,13  10,15
   *
   *  The goal is to get to the bottom-right column, which will contain the minimum number
   *  of coins required to give change for 5 with the coin set {1,2,5}.
   *
   *  In practice, it's not necessary to memoize the entire table; keeping just the
   *  current and the previous rows will suffice, as implemented by [[TwoRowFrame]] class.
   */

  def maxValue(limit: Int, items: Seq[Item]): Int = {

    val memo = TwoRowFrame[Item](limit, Item(0,0))

    for (item <- items.sortBy(_.weight)) {
      for (j <- 1 to limit ) {
        memo.current(j) =
          if (item.weight > j) {
            memo.previous(j)
          } else {
            memo.previous(j).maxByValue(memo.current(j - item.weight))
          }
      }
      memo.advance()
    }
    memo.previous.last.value
  }

}

