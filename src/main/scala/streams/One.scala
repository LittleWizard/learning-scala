package streams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{Materializer, FlowShape, ClosedShape, ActorMaterializer}
import akka.stream.scaladsl._
import scala.io.StdIn


object One {

  def one(): RunnableGraph[NotUsed] = {
    Source.fromIterator(() => Iterator.apply("one", "two", "three", "four"))
      .map(_.toString)
      .map(_.toUpperCase)
      .filter(_.length > 0)
      .filter(_.length <= 3)
      .to(Sink.foreach(f => println(s"After processing : ${f}")))
  }

  def two() = {
  val source = Source.fromIterator(() => Iterator.apply("one", "two", "three", "four"))
 // val stringMapper = Flow[String].map(_.toString)
  val upperCaseMapper = Flow[String].map(_.toUpperCase())
  val ignoreZeroLength = Flow[String].filter(_.length > 0)
  val ignoreGreaterThanThree = Flow[String].filter(_.length <= 3)
  val sink = Sink.foreach[String](f => println(s"After processing : ${f}"))

  val stream = source
  //.via(stringMapper)
  .via(upperCaseMapper)
  .via(ignoreZeroLength)
  .via(ignoreGreaterThanThree)
  .to(sink)

  stream
}


  def three()(implicit materializer: Materializer) = {
    //you can also think about streams as Graphs. Akka streams also provides a powerful GraphDSL
    //to define complicated streams in simple way. Following with the same example we could do:

    val graph = RunnableGraph.fromGraph(GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._

      val source = Source.fromIterator(() => Iterator.apply("one", "two", "three", "four"))
      // val stringMapper = Flow[String].map(_.toString)
      val upperCaseMapper = Flow[String].map(_.toUpperCase())
      val ignoreZeroLength = Flow[String].filter(_.length > 0)
      val ignoreGreaterThanThree = Flow[String].filter(_.length <= 3)
      val sink = Sink.foreach[String](f => println(s"After processing : ${f}"))
      source ~> upperCaseMapper ~> ignoreZeroLength ~> ignoreGreaterThanThree ~> sink
      ClosedShape
    })
    graph.run()
  }

  def four()(implicit materializer: Materializer) = {

    val combinedFlow = Flow.fromGraph(GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._

      val mapper = builder.add(Flow[String].map(_.toUpperCase()))
      val ignoreZeroLength = builder.add(Flow[String].filter(_.length > 0))
      val ignoreGreaterThanThree = builder.add(Flow[String].filter(_.length <= 3))
      mapper ~> ignoreZeroLength ~> ignoreGreaterThanThree
      FlowShape(mapper.in, ignoreGreaterThanThree.out)
    })

    val source = Source.fromIterator(() => Iterator.apply("one", "two", "three", "four"))
    val sink = Sink.foreach[String](f => println(s"After processing : ${f}"))
    val stream = source
    .via(combinedFlow)
    .to(sink)

    stream.run();


  }

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("sys")
    implicit val materializer = ActorMaterializer()

    //one().run();
    //two().run();

    four()



    StdIn.readLine("Write something .....\n")
    system.terminate();

  }

}
