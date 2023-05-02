package org.learning
package fundamentals.Section3FunctionalProgramming

import scala.util.Random


/**
 * 1. Options used to deal with uncertainty of value existence (null pointer exception)
 * 2. Don't use some for safe checking a value coming out of an option
 * 3. Some should always hold a value or-else it should be None
 * 4. Option.get is not recommended because it can retrieve the same null pointer inside the option
 */
object Options extends App {

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  println(myFirstOption)

  def unsafeMethod(): String = null

  val result = Option(unsafeMethod())
  println(result)

  def backupMethod(): String = "A valid result"

  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))

  def betterUnsafeMethod(): Option[String] = None

  def betterBackupMethod(): Option[String] = Some("A valid result")

  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()

  println(myFirstOption.isEmpty)
  println(myFirstOption.get)

  println(myFirstOption.map(_ * 2))
  println(myFirstOption.filter(x => x > 10))
  println(myFirstOption.flatMap(x => Option(x * 10)))

  val config: Map[String, String] = Map(
    // fetched from elsewhere
    "host" -> "176.45.36.1",
    "port" -> "80"
  )
  class Connection {
    def connect = "Connected" // connect to some server
  }
  object Connection {
    val random = new Random(System.nanoTime())
    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }
  val host = config.get("host")
  val port = config.get("port")
  val connection = host.flatMap(h => port.flatMap(p => Connection.apply(h, p)))
  val connectionStatus = connection.map(c => c.connect)
  println(connectionStatus)

  connectionStatus.foreach(println)
  config.get("host")
    .flatMap(host => config.get("port")
      .flatMap(port => Connection(host, port))
      .map(connection => connection.connect))
    .foreach(println)
  val forConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect
  forConnectionStatus.foreach(println)
}