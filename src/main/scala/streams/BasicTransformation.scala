package streams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import scala.concurrent.ExecutionContext.Implicits.global


object BasicTransformation {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("sys")
    implicit val materializer = ActorMaterializer()

    val text =
      """|Lorem Ipsum is simply dummy text of the printing and typesetting industry.
        |Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
        |when an unknown printer took a galley of type and scrambled it to make a type
        |specimen book.""".stripMargin



    println(Thread.currentThread().getId + " MAIN THREAD ID")


    Source.fromIterator(() => text.split("\\s").iterator)
      .map(t => {
        println(Thread.currentThread().getId + " <<MAP ====>> " + t)
        t.toUpperCase()
      })
      .runWith(Sink.foreach(t => {
        Thread.sleep(2000)   // so, source will produce less
        println(Thread.currentThread().getId + " <<SINK ====>> " + t)
      }))
      .onComplete(_ => system.terminate())


  }


}
