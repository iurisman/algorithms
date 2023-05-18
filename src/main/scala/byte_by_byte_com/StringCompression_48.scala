package byte_by_byte_com

import java.util.concurrent.atomic.AtomicInteger
import scala.util.Random

object StringCompression_48 extends App {

  val string = "acccbbb"
  println(compress(string))
  def compress(s: String): String = {
    val result = StringBuilder(s.length)
    var currChar:Char = s.charAt(0)
    var currCount = 1
    for (c <- s.substring(1, s.length)) {
      if (c == currChar) {
        currCount += 1
      } else {
        if (currCount > currCount.toString.length + 1) {
          result ++= s"$currChar$currCount"
        } else {
          result ++= currChar.toString * currCount
        }
        currChar = c
        currCount = 1
      }
    }
    if (currCount > currCount.toString.length + 1) {
      result ++= s"$currChar$currCount"
    } else {
      result ++= currChar.toString * currCount
    }
    result.toString
  }

}