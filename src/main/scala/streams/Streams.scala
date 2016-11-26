package streams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._

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





    val sink1 = Sink.fold[Int, Int](0)(_ + _)
    val runnable1: RunnableGraph[Future[Int]] =
      Source(1 to 10).toMat(sink1)(Keep.right)

    // get the materialized value of the FoldSink
    val sum1: Future[Int] = runnable1.run()
    val sum2: Future[Int] = runnable1.run()

for{
  sum1Res <- sum1
  sum2Res <- sum2
}yield {
  println(sum1Res)
  println(sum2Res)
}





    // Broadcast to a sink inline
    val otherSink: Sink[Int, NotUsed] =
      Flow[Int]
        .alsoTo(Sink.foreach(s1 => println("1 " + s1)))
        .alsoTo(Sink.foreach(s2 => println("2 " + s2)))
        .to(Sink.ignore)
    Source(1 to 6).to(otherSink).run()




    sum.map(s => {
      println(s)
      system.terminate()
    })


  }

}
