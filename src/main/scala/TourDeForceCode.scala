object TourDeForceCode extends App {
  trait Functor[F[_]]  {
    def map[A,B](fa: F[A])(t: A => B): F[B]
  }

  abstract class ListFunctor extends Functor[List]
  val listFunction = new ListFunctor() {
    override def map[A, B](fa: List[A])(t: A => B): List[B] =
      for (a <- fa) yield t(a)
  }
}
