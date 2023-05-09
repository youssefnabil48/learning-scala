package org.learning
package advanced.Section3AdvancedFunctionalProgramming

/**
 * Notes:
 * 1. lazy evaluation is a technique that delays the evaluation of an expression until it is actually needed.
 *    It can be useful for managing expensive computations, avoiding unnecessary computations, and managing circular dependencies.
 * 2. Call by need is a technique that take the parameter passed to a function/method and lazy evaluate it
 *    (should be call by name already)
 * 3. withFilter function use lazy values in computation
 */
object LazyEvaluation extends App {
  // lazy DELAYS the evaluation of values
  lazy val x: Int = {
    println("hello")
    42
  }
  println(x)
  println(x)

  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }
  def simpleCondition: Boolean = false
  lazy val lazyCondition = sideEffectCondition
  println(if (simpleCondition && lazyCondition) "yes" else "no") // lazyCondition (sideEffectCondition) never evaluated

  def byNameMethod(n: => Int): Int = {
    // CALL BY NEED
    lazy val t = n // only evaluated once
    t + t + t + 1
  }

  def retrieveMagicValue = {
    // side effect or a long computation
    println("waiting")
    Thread.sleep(1000)
    42
  }
  println(byNameMethod(retrieveMagicValue))

  // filtering
  def lessThan30(i: Int): Boolean = {
    println(s"$i is less than 30?")
    i < 30
  }
  def greaterThan20(i: Int): Boolean = {
    println(s"$i is greater than 20?")
    i > 20
  }
  val numbers = List(1, 25, 40, 5, 23)
  val lt30 = numbers.filter(lessThan30) // List(1, 25, 5, 23)
  val gt20 = lt30.filter(greaterThan20)
  println(gt20)

  val lt30lazy = numbers.withFilter(lessThan30)
  val gt20lazy = lt30lazy.withFilter(greaterThan20)
  println
  gt20lazy.foreach(println)

  // for-comprehensions use withFilter with guards
  for {
    a <- List(1, 2, 3) if a % 2 == 0 // use lazy vals
  } yield a + 1
  List(1, 2, 3).withFilter(_ % 2 == 0).map(_ + 1)
}