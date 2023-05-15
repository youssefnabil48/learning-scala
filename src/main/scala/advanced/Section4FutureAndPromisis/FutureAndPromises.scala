package org.learning
package advanced.Section4Concurrency

import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Random, Success}
import scala.concurrent.duration._

// important for futures
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Notes:
 * Future is w wrapper to operations that will happen in point of time in the future
 * 1. future code runs on a separate thread
 * 2. an global implicit executor is passed to the future by the compiler
 * 3. future.value return Option[Try[Int]]
 * 4. you can handle multiple sequential futures by three forms
 *  4.1 for comprehensions (syntax sugar for map flatmap)
 *  4.2 callbacks (onComplete)
 *  4.3 Await.results is blocking the thread till the future resolve
 * 5. Future either return a Success with the value inside or Failure with exception
 * 6. future recovery is is possible with different ways
 *  6.1 using recover and recover with
 *  6.2 using fallback
 */

object FutureAndPromises extends App {

  val aFuture = Future {
    Thread.sleep(2000)
    42
  }
  println(aFuture.value) // Option[Try[Int]]
  println("Waiting on the future")
  aFuture.onComplete {
    case Success(meaningOfLife) => println(s"the meaning of life is $meaningOfLife")
    case Failure(e) => println(s"I have failed with $e")
  }
  Thread.sleep(3000)

  //==================================================================================
  // mini social network example
  //==================================================================================
  case class Profile(id: String, name: String) {
    def poke(anotherProfile: Profile) =
      println(s"${this.name} poking ${anotherProfile.name}")
  }
  object SocialNetwork {
    // "database"
    val names = Map(
      "fb.id.1-zuck" -> "Mark",
      "fb.id.2-bill" -> "Bill",
      "fb.id.0-dummy" -> "Dummy"
    )
    val friends = Map(
      "fb.id.1-zuck" -> "fb.id.2-bill"
    )

    val random = new Random()

    // API
    def fetchProfile(id: String): Future[Profile] = Future {
      // fetching from the DB
      Thread.sleep(random.nextInt(300))
      Profile(id, names(id))
    }

    def fetchBestFriend(profile: Profile): Future[Profile] = Future {
      Thread.sleep(random.nextInt(400))
      val bfId = friends(profile.id)
      Profile(bfId, names(bfId))
    }
  }

  // client: mark to poke bill
  val mark = SocialNetwork.fetchProfile("fb.id.1-zuck")

  // functional composition of futures
  // map, flatMap, filter
  val nameOnTheWall = mark.map(profile => profile.name)
  val marksBestFriend = mark.flatMap(profile => SocialNetwork.fetchBestFriend(profile))
  val zucksBestFriendRestricted = marksBestFriend.filter(profile => profile.name.startsWith("Z"))

  // for-comprehensions
  for {
    mark <- SocialNetwork.fetchProfile("fb.id.1-zuck")
    bill <- SocialNetwork.fetchBestFriend(mark)
  } mark.poke(bill)

  Thread.sleep(1000)

  // fallbacks
  val aProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recover {
    case e: Throwable => Profile("fb.id.0-dummy", "Forever alone")
  }

  val aFetchedProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recoverWith {
    case e: Throwable => SocialNetwork.fetchProfile("fb.id.0-dummy")
  }

  val fallbackResult = SocialNetwork.fetchProfile("unknown id").fallbackTo(SocialNetwork.fetchProfile("fb.id.0-dummy"))

  //==================================================================================
  // online banking app
  //==================================================================================
  case class User(name: String)
  case class Transaction(sender: String, receiver: String, amount: Double, status: String)
  object BankingApp {
    val name = "Rock the JVM banking"
    def fetchUser(name: String): Future[User] = Future {
      Thread.sleep(500)
      User(name)
    }
    def createTransaction(user: User, merchantName: String, amount: Double): Future[Transaction] = Future {
      Thread.sleep(1000)
      Transaction(user.name, merchantName, amount, "SUCCESS")
    }
    def purchase(username: String, item: String, merchantName: String, cost: Double): String = {
      val transactionStatusFuture = for {
        user <- fetchUser(username)
        transaction <- createTransaction(user, merchantName, cost)
      } yield transaction.status
      Await.result(transactionStatusFuture, 2.seconds)
    }
  }
  println(BankingApp.purchase("Daniel", "iPhone 12", "rock the jvm store", 3000))
}