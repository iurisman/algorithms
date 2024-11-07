package igorurisman.algorithms.matrix

import collection.immutable.ArraySeq
import scala.util.Random
import scala.math.*

class Matrix private (private val elems: Array[Array[Double]])
                      // We're tranlating from 1-based index to 1-based index. Still need to decrement after translation.
                      // before referencing into the `elems` arrays.
                     (private val rowIndex: SkipIndex = new SkipIndex(elems.length, 1),
                      private val columnIndex: SkipIndex = new SkipIndex(elems(0).length, 1))
{
  if (elems.exists(row => row.length != elems(0).length)) {
    throw new Exception("Not a matrix")
  }
  def this(elems: Array[Array[Double]]) = {
    this(elems)()
  }
  private def copy: Matrix = new Matrix(elems)(rowIndex.copy, columnIndex.copy)
  def rows: Int = rowIndex.length
  def columns: Int = columnIndex.length
  def isSquare: Boolean = rows == columns

  def apply(row: Int, col: Int): Double = {
    val r = rowIndex(row)
    elems(rowIndex(row)-1)(columnIndex(col)-1)
  }

  /** A view of this matrix without given row and given column */
  def drop(row: Int, col: Int): Matrix = {
    val result = copy
    result.rowIndex.skip(row)
    result.columnIndex.skip(col)
    result
  }

  def det: Double = {
    if (!isSquare) {
      throw new Exception("Only square matrices can have determinants")
    }
    if (rows == 1) {
      this(1, 1)
    }
    else {
      var result = 0.0
      for (j <- 1 to columns) {
        val detMinor =  {
          val minor = drop(1, j)
          minor.det
        }
        result = result + pow(-1, j+1) * this(1, j) * detMinor
      }
      result
    }
  }

  override def toString: String = {
    val result = new StringBuffer()
    for (i <- 1 to rowIndex.length) {
      if (i > 1) result.append("\n")
      for (j <- 1 to columnIndex.length) {
        if (j > 1) result.append(",")
        result.append(this(i,j))
      }
    }
    result.toString
  }
}

object Matrix {
  def fill(rows: Int, cols: Int)(f: (Int,Int) => Double): Matrix = {
    val elems: Array[Array[Double]] = Array.ofDim[Double](rows, cols)
    for {
      i <- 0 until rows
      j <- 0 until cols
    } {
      elems(i)(j) = f(i+1, j+1)
    }
    new Matrix(elems)
  }
  def fill(dimension: Int)(f: (Int, Int) => Double): Matrix = {
    fill(dimension, dimension)(f)
  }

  def main(args: Array[String]): Unit = {
    val m = Matrix.fill(12)((i, j) => Random.nextInt(100) - 50)
    println(m.det)
  }
}