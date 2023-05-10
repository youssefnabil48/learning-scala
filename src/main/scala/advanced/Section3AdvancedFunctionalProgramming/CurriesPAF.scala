package org.learning
package advanced.Section3AdvancedFunctionalProgramming

/**
 * Notes:
 * 1. lifting is wrapping a method in a function form so it can be passed to HOF
 * 2. functions != methods (JVM limitation):
 *    a method is a member of a class or object that can have side effects and can be called on an instance of the class or object,
 *    while a function is a standalone block of code that is typically pure and can be called directly by its name.
 * 3. using _ to denote that you don't care about the second parameter list to return the curried function without specifying the type
 *     [ val add5 = curriedAdder(5) _ ] exactly equal to [ val add5: Int => Int = curriedAdder(5) ]
 * 4. Eta expansion is a technique in functional programming where a function that takes multiple arguments is
 *    transformed into a function that takes fewer arguments by partially applying the function with one or more of its arguments.
 */
object CurriesPAF extends App {
  // curried functions
  val superAdder: Int => Int => Int =
    x => y => x + y
  def inc(x: Int) = x + 1

  List(1, 2, 3).map(x => inc(x)) // ETA-expansion
  def curriedAdder(x: Int)(y: Int): Int = x + y
  val add5 = curriedAdder(5) _ // exactly equal: val add5: Int => Int = curriedAdder(5)
  println(add5(5))

  // EXERCISE
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  // alternative syntax for turning methods into function values
  // add7: Int => Int = y => 7 + y
  val add7 = (x: Int) => simpleAddFunction(7, x)
  val add7_2 = simpleAddFunction.curried(7)
  val add7_6 = simpleAddFunction(7, _: Int)

  // PAF
  val add7_3 = curriedAddMethod(7) _
  val add7_4 = curriedAddMethod(7)(_)

  val add7_5 = simpleAddMethod(7, _: Int)

  // underscores are powerful
  def concatenator(a: String, b: String, c: String) = a + b + c

  val insertName = concatenator("Hello, I'm ", _: String, ", how are you?") // x: String => concatenator(hello, x, howarewyou)
  println(insertName("Daniel"))

  val fillInTheBlanks = concatenator("Hello, ", _: String, _: String) // (x, y) => concatenator("Hello, ", x, y)
  println(fillInTheBlanks("Daniel", " Scala is awesome!"))

}