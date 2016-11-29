package utils

import models.Person


object Utils {
  def sayHello(implicit person: Person): String = "Hello " + person.name
}
