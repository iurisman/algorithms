package igorurisman.algorithms.dynamicprogramming

/**
 *
 */
object Knapsack extends App {

  case class Item(weight: Int, value: Int) {
    def maxByValue(other: Item): Item = if (other.value > value) other else this
    def + (other: Item) = Item(weight + other.weight, value + other.value)
  }

  /**
   *   Compute the max value that can be held by a knapsack of weight capacity W, given
   *   a list of n items (wᵢ,vᵢ), 0 < i <= n, where wᵢ denotes the items weight and vᵢ
   *   denotes the item's value. Each item is available in unlimited number of copies.
   *
   *   We consider the table with n + 1 rows — one more than the number of items.
   *   Starting with the 0th row, representing the artificial, but convenient boundary
   *   case of 0 items, each subsequent row 0 < i <= n represents a subset of items
   *   equal the previous row's subset plus the next item in ascending order of value,
   *   the variable being optimized for. The number of columns in the table
   *   is W + 1, one more than the weight capacity of the knapsack, with column 0
   *   representing the artificial, but convenient boundary case of 0 capacity.
   *   Each subsequent column's index 0 < j <= W is the amount in ascending order 1 to W.
   *
   *   We initialize the 0th row and the 0th column with with pairs (0,0).
   *
   *   For example, given the weight limit W = 9 and items {(1,5),(2,3),(5,7)}, the initial table is:
   *
   *                 0    1     2     3     4     5     6     7     8     9     10
   *   ----------------------------------------------------------------------------
   *   {}          | 0,0  0,0   0,0   0,0   0,0   0,0   0,0   0,0   0,0   0,0   0,0
   *   {(1,5)}     | 0,0
   *   {...,(2,3)} | 0,0
   *   {...,(5,7)} | 0,0
   *
   *   In the general case, the value in the cell C(i,j) is a pair (weight, value) denoting the
   *   solution for the sub-problem for the knapsack capacity j and the i smallest (by weight) items.
   *   It can be computed using the following key recurrence relation as a choice between taking
   *   the current item or not. If we omit the current item, then C(i,j) = C(i-1,j), which we
   *   already have. In order to take the current item (wk, vk), we find the element C(i, j-k) and,
   *   if it exists, compare its value plus the value of the current item, if we were to take it,
   *   with the value of the solution C(i-1,j) and pick the one that yields the greater value.
   *
   *                 0    1     2     3     4     5     6     7     8     9     10
   *   ------------------------------------------------------------------------------
   *   {}          | 0,0  0,0   0,0   0,0   0,0   0,0   0,0   0,0   0,0   0,0   0,0
   *   {(1,2)}     | 0,0  1,2   2,4   3,6   4,8   5,10  6,12  7,14  8,16  9,18  10,20
   *   {...,(2,5)} | 0,0  1,1   2,5   3,6   4,10  5,11  6,15  7,16  8,20  9,21  10,25
   *   {...,(5,7)} | 0,0  1,1   2,5   3,6   4,10  5,11  6,15  7,16  8,20  9,21  10,25
   *
   *  The goal is to get to the bottom-right column, which will contain the weight and the value
   *  of a most valuable backpack. In practice, it's not necessary to memoize the entire table; keeping just the
   *  current and the previous rows will suffice, as implemented by [[TwoRowFrame]] class.
   *
   *  The running time complexity O(W*n) and space complexity O(W)
   */

  def maxValue(limit: Int, items: Seq[Item]): Int = {

    val memo = TwoRowFrame[Item](limit + 1, Item(0,0))

    for (item <- items.sortBy(_.weight)) {
      for (j <- 1 to limit ) {
        memo.current(j) =
          if (item.weight > j) {
            memo.previous(j)
          } else {
            memo.previous(j).maxByValue(memo.current(j - item.weight) + item)
          }
      }
      memo.advance()
    }
    memo.previous.last.value
  }

}

