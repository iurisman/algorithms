import scala.reflect.ClassTag

object TourDeForceCode extends App {

  case class Foo(var f: Int = 0) {
    def set(value: Int) = { f = value; }
  }

  val foo = Foo()
  foo.set(1)
  println(foo)
}
