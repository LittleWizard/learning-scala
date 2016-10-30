package streams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, RunnableGraph, Sink, Source}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


object Streams {

  def main(args: Array[String]): Unit = {


    implicit val system = ActorSystem("sys")
    implicit val materializer = ActorMaterializer()

    println("Hello World")

    val source = Source(1 to 10)
    val sink = Sink.fold[Int, Int](0)(_ + _)

    // connect the Source to the Sink, obtaining a RunnableGraph
    val runnable: RunnableGraph[Future[Int]] = source.toMat(sink)((w, sum) ⇒ {
      println(w.toString)
      println("test")
      sum
    }) //Keep.right

    // materialize the flow and get the value of the FoldSink
    val sum: Future[Int] = runnable.run()


    sum.map(s => {
      println(s)
      system.terminate()
    })


  }

}
