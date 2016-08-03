import scala.util.Try

//a block with a bunch of case inside is one way of defining an anonymous function
val map = Map("1" -> "1", "2" -> "2", "3" -> "3")
map foreach { case (k, v) => println(k + " -> " + v)}
//List(41, "cat") map { case i: Int ⇒ i + 1 } //crash
List(41, "cat") collect { case i: Int ⇒ i + 1 } // no crash
//case block define special functions called partial functions

val fraction = new PartialFunction[Int, Int] {
  def apply(d: Int) = 42 / d
  def isDefinedAt(d: Int) = d != 0
}
fraction.isDefinedAt(42)
fraction.isDefinedAt(0)

fraction(42)
//fraction(0)
val fraction2: PartialFunction[Int, Int] =
{ case d: Int if d != 0 ⇒ 42 / d }
fraction2(42)
//fraction2(0)
val incAny: PartialFunction[Any, Int] =
{ case i: Int ⇒ i + 1 }
incAny(41)
//incAny("cat")
incAny.isDefinedAt(41)
incAny.isDefinedAt("cat")
List(41, "cat") collect incAny

//also, as you notice, if you define the partial function inline, the compiler knows that
//it's a partial function and you avoid the explicit PartialFunction trait
//notice that partial function can lie

val liar: PartialFunction[Any, Int] =
{ case i: Int ⇒ i; case s: String ⇒ s.toInt }

liar.isDefinedAt(42) // true
liar.isDefinedAt("cat")
//liar("cat")  //java.lang.NumberFormatException: For input string: "cat"

//here, liar says incorrectly that it's defined for cat. It would probably
//be better to write :
def isParsableAsInt(s: String): Option[Int] = {
  Try(s.toInt).toOption
}

val honest: PartialFunction[Any, Int] =
{ case i: Int ⇒ i; case s: String if isParsableAsInt(s) != None  ⇒ s.toInt }

honest.isDefinedAt("12")
honest.isDefinedAt("cat") // false
val songTitles = List("The White Hare", "Childe the Hunter", "Take no Rogues")
songTitles.map(t => t.toLowerCase)
songTitles.map(_.toLowerCase)


val wordFrequencies = ("habitual", 6) :: ("and", 56) :: ("consuetudinary", 2) ::
  ("additionally", 27) :: ("homely", 5) :: ("society", 13) :: Nil
def wordsWithoutOutliers(wordFrequencies: Seq[(String, Int)]): Seq[String] =
  wordFrequencies.filter(wf => wf._2 > 3 && wf._2 < 25).map(_._1)
wordsWithoutOutliers(wordFrequencies) // List("habitual", "homely", "society")

// which function take anonymous functions we can pass block of code with case classes

def wordsWithoutOutliers2(wordFrequencies: Seq[(String, Int)]): Seq[String] =
  wordFrequencies.filter { case (_, f) => f > 3 && f < 25 } map { case (w, _) => w }

val predicate: ((String, Int)) => Boolean = { case (_, f) => f > 3 && f < 25 }
val transformFn: ((String, Int)) => String = { case (w, _) => w }

//if you browse through scala's collections api, you will notice a method called collect, which, for
//a Seq[A], has the following signature :
//def collect[B](pf: PartialFunction[A,B]

//This method returns a new sequence by applying the given function to all of it's elements
//-the partial function both filters and maps the sequence.


//So, what is a partial function ? In short, it's a unary function(only one parameter) that is known to be defined only for
//certain input values and that allows clients to check whether it is defined for a specific input value.

//to this end, the PartialFunction trait provides an isDefinedAt method. As a matter of fact,
//the PartialFunction[-A, +B] type extends the type (A) => B(which can also be written as Function1[A, B]), and a
//pattern matching anonymous function is always of type PartialFunction

//Due to this inheritance hierarchy, passing a pattern matching anonymous function to a method
//that expects a Function1, like map or filter, is perfectly fine, as long as that function is
//defined for all input values, i.e. there is always a matching case



//the collect method, however, specifically expects a PartialFunction[A,B] that may not be defined for all values and knows exactly how to deal with case.

val pf = new PartialFunction[(String, Int), String] {
  def apply(wordFrequency: (String, Int)) = wordFrequency match {
    case (word, freq) if freq > 3 && freq < 25 => word
  }
  def isDefinedAt(wordFrequency: (String, Int)) = wordFrequency match {
    case (word, freq) if freq > 3 && freq < 25 => true
    case _ => false
  }
}
//wordFrequencies.map(pf) // will throw a MatchError

wordFrequencies.collect(pf) // List("habitual", "homely", "society")

//final
def wordsWithoutOutliersFinal(wordFrequencies: Seq[(String, Int)]): Seq[String] =
  wordFrequencies.collect { case (word, freq) if freq > 3 && freq < 25 => word }































