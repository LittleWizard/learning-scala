class User {
  private var _name: String = _

  def name = _name

  def name_=(name: String) = {
    if (name == null) {
      throw new NullPointerException("User.name cannot be null!")
    }
    _name = name
  }
}


val user = new User

println(user.name)
user.name = "hello"
println(user.name)


//first thought : type parameterization is related to input value type

def f[A](x: A): A = x

f(1)         // 1
f("two")     // "two"
f[Float](3)  // 3.0F

//def g[A](x: A): A = 2 * x

//is a compile-time error since * is not defined for every type T


