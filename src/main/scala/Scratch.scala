
object Scratch {
  def main(args: Array[String]): Unit = {
    val foo = Foo(10, None)
    println("**************")
    for ((param, name) <- foo.productIterator.zip(foo.productElementNames)) {
      if (param.isInstanceOf[Option[_]]) println(s"${param.getClass.getName} $name")
    }
  }
}

case class Foo(a: Integer, b: Option[String])