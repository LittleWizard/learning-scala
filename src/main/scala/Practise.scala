


object Practise {

  def main(args: Array[String]): Unit = {

    println("Hello World")

    println(funcOne { param =>
      param
    })


    def funcOne(paramFunc: String => String) = {
      paramFunc("Hello World Again");
    }


  }
}
