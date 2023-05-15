package org.learning
package advanced.Section4Concurrency

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import java.net.URL
import scala.util.{Failure, Success}
import play.api.libs.json._

object FutureExtended extends App {
  def operation1(x: Int = 0): Future[Int] = {
    Future {
      Thread.sleep(1000)
      1 + x
    }
  }

  def operation2(x: Int = 0): Future[Int] = {
    Future {
      Thread.sleep(2000)
      2 + x
    }
  }

  def operation3(x: Int = 0): Future[Int] = {
    Future {
      Thread.sleep(3000)
      3 + x
    }
  }

  // chain of awaits
  val f1: Int = Await.result(operation1(), Duration.Inf)
  val f2: Int = Await.result(operation2(f1), Duration.Inf)
  val f3: Int = Await.result(operation3(f2), Duration.Inf)
  println(s"Awaits $f3")

  // for comprehentions
  val forComprehensionResult = for {
    x <- operation1()
    y <- operation2(x)
    z <- operation3(y)
  } yield println(s"z $z")
  println(s"forComprehensionResult $forComprehensionResult")
  println(s"forComprehensionResult map ${forComprehensionResult.map(x => s"$x")}")
  forComprehensionResult.foreach(x => println(s"forComprehensionResult foreach $x"))


  // maps anf flatmaps
  val chainMaps = operation1().flatMap((x) => {
    operation2(x).flatMap((y) => {
      operation3(y).map((z) => z)
    })
  })
  println(s"chainedMaps map ${chainMaps.map(x => s"$x")}")
  chainMaps.foreach(x => println(s"chainedMaps foreach $x"))

  // callback functions
  chainMaps.onComplete {
    case Success(extractedIntValue) => println(extractedIntValue)
    case Failure(e) => e.printStackTrace()
  }

  def callingApi(): Future[String] = {
    Future {
      val url = new URL("https://api.github.com/users/bard")
      val response = url.openStream()
      val json = scala.io.Source.fromInputStream(response).mkString
      val user = Json.parse(json)
      user("login").toString()
      throw new RuntimeException("there was intentional exception here")
    }
  }

  callingApi().foreach(println)
  for { result <- callingApi() } yield println(s"foreach result of calling api $result")

  callingApi().recover {
    case e => println(s"recover $e")
  }

  callingApi().recoverWith {
    case _ => operation2()
  }

  val fallbackResult = callingApi() fallbackTo Future.successful(42)
  fallbackResult.foreach(x => println(x))

//  callingApi() andThen println("hello")

  // just to keep the jvm running
  while(true) {}
}