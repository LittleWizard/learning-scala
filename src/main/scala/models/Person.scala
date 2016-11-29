package models


class Person(val name: String)


object Person {
  implicit val person: Person = new Person("User")
}

