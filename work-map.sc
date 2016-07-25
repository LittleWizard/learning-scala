val fruits = Seq("apple", "banana", "orange")
fruits.map(_.toUpperCase)
fruits.flatMap(_.toUpperCase)

val mapResult = fruits.map(_.toUpperCase)
val flattenResult = mapResult.flatten

def toInt(s: String): Option[Int] = {
  try {
    Some(Integer.parseInt(s.trim))
  } catch {
    // catch Exception to catch null 's'
    case e: Exception => None
  }
}

val strings = Seq("1", "2", "foo", "3", "bar")
strings.map(toInt)
strings.map(toInt).flatten
strings.flatMap(toInt)
strings.flatMap(toInt).sum

//so, it's worth noting that flatMap is equivalent to running map and then flatten


val map = Map(1 -> "one", 2 -> "two", 3 -> "three")
1 to map.size flatMap(map.get)

