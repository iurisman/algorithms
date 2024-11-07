package igorurisman.algorithms

object String {

  def reverse(string: String): String = {
    val array = string.toArray
    val len = array.length
    for (i <- 0 until len / 2) {
      val temp = array(i)
      array(i) = array(len-1-i)
      array(len-1-i) = temp
    }
    new String(array)
  }

  def main(args: Array[String]): Unit = {
    println(reverse("abcde"))
  }
}
