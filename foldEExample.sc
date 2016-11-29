
val names = List("Daniel", "Chris", "Joseph")
/*var str = names.foldLeft("") { (acc, n) =>
  acc + ", " + n
}*/
//println(str)

var str = names.reduceLeft[String] { (acc, n) =>
  acc + ", " + n
}
println(str)

str = names
  .map(s => s"'${s}'")
  .reduceLeft[String] { (accumulator, scriptItem) =>
  accumulator + ", " + scriptItem
}