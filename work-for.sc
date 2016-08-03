for (i <- 1 to 5) yield i
for (i <- 1 to 5) yield i * 2
val a = Array(1,2,3,4,5,6)
for (e <- a) yield e
//with guard condition
for (e <- a if e > 2) yield e

//a real world example

/*def getQueryAsSeq(query: String): Seq[MiniTweet] = {
  val queryResults = getTwitterInstance.search(new Query(query))
  val tweets = queryResults.getTweets  // java.util.List[Status]
  for (status <- tweets) yield ListTweet(status.getUser.toString, status.getText, status.getCreatedAt.toString)
}*/


//for comprehension

for {
  item <- 1 to 5
 // if item.toString.endsWith("3")
} yield item

val x = Some("1")
val y = None
val z = Some("3")
(for {
  v1 <- x
  v2 <- y
  v3 <- z
} yield {
  v1 + v2 + v3
})