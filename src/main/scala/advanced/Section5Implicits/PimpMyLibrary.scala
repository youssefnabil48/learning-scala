package org.learning
package advanced.Section5Implicits

import scala.annotation.tailrec
import scala.language.implicitConversions

/**
 * Type Enrichment (AKA: pimping By implicit classes) allow us to decorate existing classes that we may not have access to with additional methods and properties
 * 1. Implicit classes must take one and only one parameter (for conversion)
 * 2. Implicit classes Optionally extends AnyVal for memory optimization purposes
 * 3. Compiler doesn't do multiple implicit searches (only 1 depth level)
 * 4. implicit classes are basically plain classes when the implicit conversion
 *    - class RichAltInt(value: Int) = implicit def enrich(value: Int): RichAltInt = new RichAltInt(value)
 */
object PimpMyLibrary extends App {

  //==================================================================================
  // Simple Feature Statement
  //==================================================================================
  implicit class RichInt(val self: Int) extends AnyVal {
    def isEven: Boolean = self % 2 == 0
    def isBiggerThat10: Boolean = self > 10
    def multiply(value: Int): Int = self * value
    def *[T](list: List[T]): List[T] = list.flatMap(x => List.fill(self)(x))
    def isPrime: Boolean = {
      if (self <= 1) return false
      if (self == 2) return true
      !(2 to (Math.sqrt(self).toInt) exists (self % _ == 0))
    }
    def times(fun: () => Unit): Unit = {
      @tailrec
      def executionMultiplier(aux: Int): Unit = {
        if( aux < 1) return
        fun()
        executionMultiplier(aux -1)
      }
      executionMultiplier(self)
    }
  }

  println(2.isPrime)
  println(10.isBiggerThat10)
  println(3.isEven)
  println(2 multiply 10)
  println(3 * List(1,2,3))
  3 times { () => println("hello") }

  //==================================================================================
  // Enhancing String class
  //==================================================================================

  implicit class RichString(val self: String) extends AnyVal {
    def asInt: Int = String.valueOf(self)
//    def /(value: Int): Int = self.asInt / value //=> gives ambiguous error (stringToInt | RichString) on method
  }
  println("1".asInt)

  //==================================================================================
  // Implicit conversions by implicit methods
  //==================================================================================
  implicit def stringToInt(self: String): Int = Integer.valueOf(self)
  println("3" / 4)

}
