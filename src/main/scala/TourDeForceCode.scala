object TourDeForceCode extends App {

  val ml = collection.mutable.Seq("Citizen Kane")
  val il = collection.immutable.List("Citizen Kane")
  println(ml.getClass.getName)
  println(il.getClass.getName)
}
