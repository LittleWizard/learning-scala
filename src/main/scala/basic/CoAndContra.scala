package basic


object CoAndContra extends App {

  abstract class Fruit {def name: String}



  class Orange extends Fruit {def name = "Orange"}

  class Apple extends Fruit {def name = "Apple"}

  abstract class Box {
    def fruit: Fruit
    def contains(aFruit: Fruit) = fruit.name.equals(aFruit.name)
  }


  class OrangeBox(orange: Orange) extends Box {
    def fruit: Fruit = orange
  }

  class AppleBox(apple: Apple) extends Box {
    def fruit: Fruit = apple
  }

//the above is bad for violating DRY

  class Box1 [F <: Fruit](aFruit: F) {
    def fruit: F = aFruit
    def contains(aFruit: Fruit) = fruit.name == aFruit.name
  }



  //by default parameterized types they are invariant, so the following is illegal

 // var box: Box1[Fruit] = new Box1[Apple](new Apple)


  class Box2[+F <: Fruit](aFruit: F) {
    def fruit: F = aFruit
    def contains(aFruit: Fruit) = fruit.name == aFruit.name
  }

  var box2: Box2[Fruit] = new Box2[Apple](new Apple)

  println("success")

}
