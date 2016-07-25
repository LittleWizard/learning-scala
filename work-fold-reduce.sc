val opt = Some("Hello World")

/*
opt.fold("a") {
  b => "b"
}*/

val f = (p: String) => "b"
val f2 = {
  b: String => "b"
}
opt.fold("a"){f}
opt.fold("a")(f2)


val names = List("Daniel", "Chris", "Joseph")
var str = names.foldLeft("") { (acc, n) =>
  acc + ", " + n
}
println(str)
 str = names.reduceLeft[String] { (acc, n) =>
  acc + ", " + n
}
println(str)

val option: Option[String] = Some("Hey There !!")
def alt: Int = 1
def func(in: String): Int = 2
// if you prefer one liner, fold is actually a combination of map and getOrElse
val x: Int = option map func getOrElse alt
//or, if you are a C programmer that still wants to write in C, but using Scala compiler:
val y: Int = if(option.isDefined)
  func(option.get)
else
  alt

//interestingly this is how fold() is implemented

val z: Int = option.fold(alt)(func)
























