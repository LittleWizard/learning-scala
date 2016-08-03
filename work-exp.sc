def async2(block: => Option[String]): Option[String] = {


  Some("Hello")
}

/*async2{
   "Hey"
}*/
val a = { Some("Hello")}
async2(a)

