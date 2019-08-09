name := "guess-the-product"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.3" % "provided"
//
//assemblyJarName in assembly := s"${name.value.replace(' ', '-')}-${version.value}.jar"
//
//assemblyOption in assembly := (assemblyOption in assembly).
//  value.copy(includeScala = false)
