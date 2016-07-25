import scala.util.{Failure, Success, Try}
import java.net.URL

def parseUrl(url: String): Try[URL] = Try(new URL(url))

val res = parseUrl("http://google.com")

res match {
  case Success(url) => println(url)
  case Failure(exception) => println(exception.getMessage)
}


val url = parseUrl("oga") getOrElse new URL("http://duckduckgo.com")

parseUrl("http://danielwestheide.com").map(_.getProtocol)
// results in Success("http")
parseUrl("garbage").map(_.getProtocol)

import java.io.InputStream
def inputStreamForURL(url: String): Try[Try[Try[InputStream]]] = parseUrl(url).map { u =>
  Try(u.openConnection()).map(conn => Try(conn.getInputStream))
}


def inputStreamForURL2(url: String): Try[InputStream] = parseUrl(url).flatMap { u =>
  Try(u.openConnection()).flatMap(conn => Try(conn.getInputStream))
}


//you can also filter Try or call foreach on it.Both work exactly as you would except
//after having learned about Option.

//The filter method returns a Failure if the Try on which it is called is already a Failure or if the predicate passed
//to it returns false(in which case wrapped exception is a NoSuchElementException).If the Try on which
//it is called is a Success and the predicate returns true, that Success instance is returned unchanged.

def parseHttpURL(url: String) = parseUrl(url).filter(_.getProtocol == "http")
parseHttpURL("http://apache.openmirror.de") // results in a Success[URL]
parseHttpURL("ftp://mirror.netcologne.de/apache.org") // results in a Failure[URL]

//the function passed to a foreach is executed only if the Try is a Success , which allows you to
//execute a side effect. The function passed to foreach is executed exactly once in that case,
//being passed the value wrapped by the Success:

parseHttpURL("http://danielwestheide.com").foreach(println)

//the support for flatMap, map and filter means that you can also use for comprehensions in order to chain
//operations on try instances.Usually, this results in more readable code.To demonstrate this, let's implement a method that returns the content of a web
//page with a given URL using for comprehensions.

//so for comprehensions means chaining operations

import scala.io.Source
def getURLContent(url: String): Try[Iterator[String]] =
  for {
    url <- parseUrl(url)
    connection <- Try(url.openConnection())
    is <- Try(connection.getInputStream)
    source = Source.fromInputStream(is)
  } yield source.getLines()


//There are three places where things can go wrong, all of them covered by usages of the
//Try type. First, the already implemented parseUrl method returns a Try[URL]. Only if this is a
//Success[URL], we will try to open a connection and create a new input stream from it.
//If opening the connection and creating the input stream succeeds , we continue, finally yielding the lines of the web page.
//Since we effectively chain multiple flatMap calls in this for comprehension, the result type is
//flat Try[Iterator[String]]

//pattern matching

import scala.util.Success
import scala.util.Failure
getURLContent("http://danielwestheide.com/foobar") match {
  case Success(lines) => lines.foreach(println)
  case Failure(ex) => println(s"Problem rendering URL content: ${ex.getMessage}")
}



//recovering from failure


//if you want to establish some kind of default behaviour in the case of a Failure, you don't
//have to use getOrElse.An alternative is recover, which expects a partial function and returns another Try.
//If recover is called on a Success instance, that instance is returned as is.
//Otherwise, if the partial function is defined or the given Failure instance , its result is
//returned as a Success


import java.net.MalformedURLException
import java.io.FileNotFoundException
val content = getURLContent("garbage") recover {
  case e: FileNotFoundException => Iterator("Requested page does not exist")
  case e: MalformedURLException => Iterator("Please make sure to enter a valid URL")
  case _ => Iterator("An unexpected error has occurred. We are so sorry!")
}

//We could now safely get the wrapped value on the Try[Iterator[String]] that we assigned to content,
//because we know that it must be a Success. Calling content.get.foreach(println) would result in Please make
//sure to enter a valid URL being printed to the console.




















