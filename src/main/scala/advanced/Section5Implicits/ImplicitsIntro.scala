package org.learning
package advanced.Section5Implicits

import scala.language.implicitConversions

object ImplicitsIntro extends App {

  // operator (function) -> doesn't exists on the String class nor the Int class
  private val newTuple = "hello" -> 1
  println(newTuple)

  /**
   * The compiler will first try to find greet on the string class and won't find it
   * then it will look for any implicit functions, classes, objects that can help in completion
   * it looks for implicit that can convert the "mohamed" string to a something that can have greet method
   */
  case class Person(name: String) {
    def greet: String = s"hello $name"
    def greetSomeone(anotherPersonName: String) = s"$name says hello to $anotherPersonName"
  }
  implicit def fromStringToPerson(str: String): Person = Person(str)
  println("mohamed".greet)
  println("youssef" greetSomeone "ahmed")


  /**
   *Implicit Parameter
   * The compiler tries to fill the implicit function parameter amount with any implicit value that has the same type
   */
  def increment(x: Int)(implicit amount: Int) = x + amount
  implicit val defaultAmount: Int = 10
  println(increment(2))
  println(increment(2)(5))

}
