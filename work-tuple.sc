//a scala tuple is a class that can contain a miscellaneous collection of elements.
//i like to think of them as a little bag or container you can use to hold things and
//pass them around


val stuff = (42, "fish")

//A tuple isn't actually a collection; it's a series of classes named Tuple2, Tuple3, etc. through Tuple22.
//You don't have to worry about the detail, other than knowing that you can have anywhere from two to twenty-two items in a tuple.
//you can access tuple elements using an underscore syntax. The first element is accessed with _1, the second element with _2 and so on.

val things = ("a", 1, 3.5)
println(things._1)
println(things._2)
println(things._3)
//use variable names to access tuple elements
def getUserInfo = ("Al", 42, 200.0)
val(name, age, weight) = getUserInfo

name
age
weight