package basic

import models.Person

import scala.util.Try




object ImplicitsAndCompanion extends App {

  println(utils.Utils.sayHello)


  val mayBeFirstName: Option[String] = Option("Tanmoy")
  val mayBeLastName:Option[String] = None

  val maybeFullName = for {
    firstName <- mayBeFirstName
    lastName <- mayBeLastName
  } yield firstName + " " + lastName


  Try{

  }

  println(maybeFullName)

}
