import scala.reflect.ClassTag

object TourDeForceCode extends App {

  class Pet
  class Fish extends Pet
  class Snake extends Pet
  val fishes = new Array[Fish](10)
  val pets: Array[Pet] = new Array[Fish](10)
}
