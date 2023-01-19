object TourDeForceCode extends App {

  class Bar[X >: Integer]  {
    val x = "foo"
  }

  def pi(cencored: Boolean) = if (cencored) 1000L else Math.PI
}
