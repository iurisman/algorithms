package igorurisman.algorithms.dynamicprogramming

/**
 *
 */
object CoinChange extends App {

  /**
   *   Compute number of ways change can be given in coins of given denominations.
   *
   *   The key idea is to consider the table with as many rows as the number of coins
   *   (plus one for no coins) and as many columns as the desired change (plus one
   *   for the amount of 0). The first row is the artificial case of the empty
   *   coin set. Each subsequent row represents a partial coin set {d1..dk),
   *   derived from the previous row's subset by adding the next coin in ascending
   *   order. The first column is the artificial case of the change amount of 0.
   *   Each subsequent column is the amount in ascending order 1 to A. For example,
   *   given the amount A = 5 and coin set {1,2,5}, the initial table is:
   *
   *             0 1 2 3 4 5
   *   ---------------------
   *   {}      | 1 0 0 0 0 0
   *   {1}     | 1
   *   {1,2}   | 1
   *   {1,2,5} | 1
   *
   *   The 1s indicate that regardless of the coin set, including an empty one,
   *   there's one way to give a change of 0. The 0s indicate that regardless
   *   of the change amount > 0, it cannot be given with an empty coin set.
   *
   *   In the general case, the value in the cell (i,j) is computed based on the
   *   following key recurrent relationship. The number of ways change can be given
   *   for the amount j and the increasing coin list {c1...ci) is the sum of two other
   *   values which have already been computed: the cell (i-1,j) above
   *   is number of ways to give j, but without the rightmost coin C, and the cell (i, j-d),
   *   where d is the denomination of the rightmost coin in this row's subset of coins,
   *   is the cell d columns to the left containing the number of times the change can be
   *   given with the entire coin subset.
   *   Cell(i, j) =
   *     Cell(i-1, j) + // Always exists
   *     Cell(i, j-d)   // or 0 if d > j
   *
   *             0 1 2 3 4 5
   *   ---------------------
   *   {}      | 1 0 0 0 0 0
   *   {1}     | 1 1 1 1 1 1
   *   {1,2}   | 1 1 2 2 3 3
   *   {1,2,5} | 1 1 2 2 3 4
   *
   *  The goal is to get to the bottom-right column, which will contain the number of
   *  ways the given amount can be given in the given coin set.
   */
  def ways(amount: Int, coins: Array[Int]): Long = {

    val coinsSorted = coins.sorted
    val (rows, columns) = (coins.length + 1, amount + 1)

    // Setup the table
    val table = Array.ofDim[Long](rows, columns)
    for (i <- 0 until rows) table(i)(0) = 1
    for (j <- 1 until columns) table(0)(j) = 0

    // Fill in the table
    for {
      i <- 1 until rows
      j <- 1 until columns
    } {
      val coins = coinsSorted.take(i)
      table(i)(j) = table(i - 1)(j) + (if (coins.last > j) 0 else table(i)(j - coins.last))
    }

    table(rows - 1)(columns - 1)
  }

  /**
   *   Compute the min number coins required to give a given amount of change
   *   in coins of given denominations.
   *
   *   The key idea is to consider the table with as many rows as the number of coins
   *   (plus one for no coins) and as many columns as the desired change (plus one
   *   for the amount of 0). The first row is the artificial case of the empty
   *   coin set. Each subsequent row represents a partial coin set {d1..dk),
   *   derived from the previous row's subset by adding the next coin in ascending
   *   order. The first column is the artificial case of the change amount of 0.
   *   Each subsequent column is the amount in ascending order 1 to A. For example,
   *   given the amount A = 5 and coin set {1,2,5}, the initial table is:
   *
   *             0 1 2 3 4 5
   *   ---------------------
   *   {}      | 1 ∞ ∞ ∞ ∞ ∞
   *   {1}     | 1
   *   {1,2}   | 1
   *   {1,2,5} | 1
   *
   *   The 1s indicate that regardless of the coin set, including an empty one,
   *   there's one way to give a change of 0. The ∞s indicate that regardless
   *   of the change amount > 0, it cannot be given with an empty coin set.
   *
   *   In the general case, the value in the cell (i,j) is computed based on the
   *   following key recurrent relationship. The number of ways change can be given
   *   for the amount j and the increasing coin list {c1...ci) is the sum of two other
   *   values which have already been computed: the cell (i-1,j) above
   *   is number of ways to give j, but without the rightmost coin C, and the cell (i, j-d),
   *   where d is the denomination of the rightmost coin in this row's subset of coins,
   *   is the cell d columns to the left containing the number of times the change can be
   *   given with the entire coin subset.
   *   Cell(i, j) =
   *     Cell(i-1, j) + // Always exists
   *     Cell(i, j-d)   // or 0 if d > j
   *
   *             0 1 2 3 4 5
   *   ---------------------
   *   {}      | 1 0 0 0 0 0
   *   {1}     | 1 1 1 1 1 1
   *   {1,2}   | 1 1 2 2 3 3
   *   {1,2,5} | 1 1 2 2 3 4
   *
   *  The goal is to get to the bottom-right column, which will contain the number of
   *  ways the given amount can be given in the given coin set.
   */

  def minCoins(amount: Int, coins: Array[Int]): Option[Int] = {

    val coinsSorted = coins.sorted
    val (rows, columns) = (coins.length + 1, amount + 1)
    val Inf = Int.MaxValue

    // Setup the table
    val table = Array.ofDim[Int](rows, columns)
    for (i <- 0 until rows) table(i)(0) = 1
    for (j <- 1 until columns) table(0)(j) = Inf

    // Fill in the table
    for {
      i <- 1 until rows
      j <- 1 until columns
    } {
      val coins = coinsSorted.take(i)
      table(i)(j) = table(i - 1)(j).min {
        if (coins.last > j || table(i)(j - coins.last) == Inf) Inf
        else table(i)(j - coins.last) + 1
      }
      println(s"$i $j")
      for (i <- 0 until rows) println(table(i).mkString(","))
    }

    for (i <- 0 until rows) println(table(i).mkString(","))
    table(rows - 1)(columns - 1) match {
      case Inf => None
      case res => Some(res)
    }
  }
}

