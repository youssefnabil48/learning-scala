package org.learning
package fundamentals.Section2OOP

import java.lang.invoke.MethodHandles.Lookup.ClassOption

/**
 * throwable classes extend the Throwable class.
 * Exception and Error are the major Throwable subtypes
 *
 */
object Exceptions extends App {

  val x: String = null
  //  println(x.length) this will crash the application with java null pointer exception

  //  val aWeirdValue: String = throw new NullPointerException // also crashes

  def getInt(withExceptions: Boolean): Int =
    if (withExceptions) throw new RuntimeException("No int for you!")
    else 42

  val potentialFail = try {
    // code that might throw
    getInt(false)
  } catch {
    case e: RuntimeException => 43
  } finally {
    // code that will get executed NO MATTER WHAT
    // optional
    // does not influence the return type of this expression
    // use finally only for side effects
    println("finally")
  }

  println(potentialFail)


  // practice

  /**
   * crash the program with OutOfMemoryException
   */
//  val list = List.fill(Int.MaxValue)(0)
//  println(list)

  /**
   * crash the program with Stack overflow error
   */
//  def functionThatCauseStackOverflowError(): Unit = {
//    def fibonacciNumber(number: Int): Int = {
//      if (number <= 1) return 1
//      fibonacciNumber(number - 1) + fibonacciNumber(number - 2)
//    }
//    fibonacciNumber(Int.MaxValue)
//  }
//  println(functionThatCauseStackOverflowError())

  /**
   * PocketCalculator
   * - add(x,y)
   * - subtract(x,y)
   * - multiply(x,y)
   * - divide(x,y)
   * Throw
   * - OverflowException if add(x,y) exceeds Int.MAX_VALUE
   * - UnderflowException if subtract(x,y) exceeds Int.MIN_VALUE
   * - MathCalculationException for division by 0
   */

  // Defining the exception as these are not java exceptions
  class OverFlowException extends Exception
  class UnderFlowException extends Exception
  class MathCalculationException extends Exception

  object PocketCalculator {
    @throws(classOf[OverFlowException])
    @throws(classOf[UnderFlowException])
    def add(x: Int, y: Int): Int = {
      if (y > 0 && x > Int.MaxValue - y) {
        throw new OverFlowException
      } else if (y < 0 && x < Int.MinValue - y) {
        throw new UnderFlowException
      } else {
        x + y
      }
    }

    @throws(classOf[OverFlowException])
    @throws(classOf[UnderFlowException])
    def subtract(x: Int, y: Int): Int = {
      if (y > 0 && x < Int.MinValue + y) {
        throw new UnderFlowException
      } else if (y < 0 && x > Int.MaxValue + y) {
        throw new OverFlowException
      } else {
        x - y
      }
    }

    @throws(classOf[OverFlowException])
    def multiply(x: Int, y: Int): Int = {
      if (y != 0 && x > Int.MaxValue / y || x < Int.MinValue / y) {
        throw new OverFlowException
      } else {
        x * y
      }
    }

    @throws(classOf[MathCalculationException])
    def divide(x: Int, y: Int): Int = {
      if(y == 0) throw new MathCalculationException
      x / y
    }
  }

  val calculator = PocketCalculator
//  println(calculator.add(Int.MaxValue,1))
//  println(calculator.subtract(Int.MinValue,1))
//  println(calculator.add(Int.MaxValue,1))
  println(calculator.divide(1000, 0))

  // tutorial implementation

  //  OOM
  //  val array = Array.ofDim(Int.MaxValue)

  //  SO
  //  def infinite: Int = 1 + infinite
  //  val noLimit = infinite

//  class OverflowException extends RuntimeException
//
//  class UnderflowException extends RuntimeException
//
//  class MathCalculationException extends RuntimeException("Division by 0")
//
//  object PocketCalculator {
//    def add(x: Int, y: Int) = {
//      val result = x + y
//
//      if (x > 0 && y > 0 && result < 0) throw new OverflowException
//      else if (x < 0 && y < 0 && result > 0) throw new UnderflowException
//      else result
//    }
//
//    def subtract(x: Int, y: Int) = {
//      val result = x - y
//      if (x > 0 && y < 0 && result < 0) throw new OverflowException
//      else if (x < 0 && y > 0 && result > 0) throw new UnderflowException
//      else result
//    }
//
//    def multiply(x: Int, y: Int) = {
//      val result = x * y
//      if (x > 0 && y > 0 && result < 0) throw new OverflowException
//      else if (x < 0 && y < 0 && result < 0) throw new OverflowException
//      else if (x > 0 && y < 0 && result > 0) throw new UnderflowException
//      else if (x < 0 && y > 0 && result > 0) throw new UnderflowException
//      else result
//    }
//
//    def divide(x: Int, y: Int) = {
//      if (y == 0) throw new MathCalculationException
//      else x / y
//    }
//
//  }

}