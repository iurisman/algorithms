scalaVersion := "3.2.2"
name := "Algorithms"
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.11" % "test",
  "commons-lang" % "commons-lang" % "2.6"
)
compileOrder := CompileOrder.JavaThenScala