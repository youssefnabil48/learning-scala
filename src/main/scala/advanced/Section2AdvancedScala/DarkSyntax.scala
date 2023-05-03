package org.learning
package advanced.Section2AdvancedScala

import scala.util.Try


/**
 * 1. last char of a function (operator) decides associativity of method
 *    if the last character is : then right associative else left associative
 * 2. In mutable objects like array apply and updated are special and are used to update the inner values
 * 3. the function that ends with =_ can be used as assignment operator like aMutableContainer.member_=(42) => aMutableContainer.member = 42
 * 4. function names can be encapsulated with `` so you can name it in multiple words
 */
object DarkSyntax extends App {

  // syntax sugar #1: methods with single param
  def singleArgMethod(arg: Int): String = s"$arg little ducks..."
  val description = singleArgMethod {
    // write some complex code
    42
  }

  val aTryInstance = Try { // java's try {...}
    throw new RuntimeException
  }

  List(1, 2, 3).map { x =>
    x + 1
  }

  // syntax sugar #2: single abstract method
  trait Action {
    def act(x: Int): Int
  }
  val anInstance: Action = new Action {
    override def act(x: Int): Int = x + 1
  }
  val aFunkyInstance: Action = (x: Int) => x + 1 // magic

  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("hello, Scala")
  })
  val aSweeterThread = new Thread(() => println("sweet, Scala!"))

  abstract class AnAbstractType {
    def implemented: Int = 23
    def f(a: Int): Unit
  }

  val anAbstractInstance: AnAbstractType = (a: Int) => println("sweet")

  // syntax sugar #3: the :: and #:: methods are special
  val prependedList = 2 :: List(3, 4)

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this // actual implementation here
  }
  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  // syntax sugar #4: multi-word method naming
  class TeenGirl(name: String) {
    def `and then said`(gossip: String): Unit = println(s"$name said $gossip")
  }
  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala is so sweet!"

  // syntax sugar #5: infix types
  class Composite[A, B](val n: A, s: B)
  val composite: Int Composite String = new Composite[Int, String](1, "")
  class -->[A, B](val n: A, s: B)
  val towards: Int --> String = new -->[Int, String](1, "")

  // syntax sugar #6: setters for mutable containers
  class Mutable {
    private var internalMember: Int = 0 // private for OO encapsulation
    def member = internalMember // "getter"
    def member_=(value: Int): Unit =
      internalMember = value // "setter"
  }
  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 // rewritten as aMutableContainer.member_=(42)

}