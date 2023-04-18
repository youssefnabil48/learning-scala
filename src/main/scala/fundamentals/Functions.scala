package org.learning
package fundamentals

import scala.annotation.tailrec

object Functions extends App {

  // specifying a Unit as return type won't fire compilation error but will result in unexpected output
  def addReturningUnit(num1: Int, num2: Int): Unit = {
    num1 + num2
  }
  println(addReturningUnit(1, 3)) // output  => ()

  def addReturningInt(num1: Int, num2: Int): Int = {
    num1 + num2
  }
  println(addReturningInt(1, 5)) // output => 6

  //    def addReturningString(num1: Int, num2: Int): String = { compiler error specifying wrong return type
  //      num1 + num2
  //    }
  //    println(addReturningString(1, 5))

  def functionWithinAFunction(x: Int, y: Int): Int = {
    def innerFunction(x: Int, y: Int): Int = x + y

    innerFunction(x, y)
  }
  println(functionWithinAFunction(3, 4))

  def recursivePrintLoop(word: String, loopCount: Int): String = {
    if (loopCount == 1) return word // removing return here will lead to infinite recursive calls
    word + recursivePrintLoop(word, loopCount - 1)
  }
  println(recursivePrintLoop("Hello", 3))


  def greetingForKids(name: String, age: Int): String = {
    if(age < 1) throw new IllegalArgumentException("age must be more than 0")
    s"Hi my name is $name and i am $age years old"
  }
  println(greetingForKids("ahmed", 1))

  def factorialFunction(number: Int): Int = {
    if(number == 1) return 1
    number * factorialFunction(number -1)
  }
  println(factorialFunction(5))

  def fibonacciNumber(number: Int): Int = {
    if(number <= 1) return 1
    fibonacciNumber(number - 1) + fibonacciNumber(number - 2)
  }
  println(fibonacciNumber(10))

  def isPrime(n: Int): Boolean = {
    /**
     * then the compiler will warn you if the method is actually not tail-recursive.
     * This makes the @tailrec annotation a good idea,
     * both to ensure that a method is currently optimizable and that it remains optimizable as it is modified.
     * @param t: Int
     * @return Boolean
     */
    @tailrec
    def isPrimeUntil(t: Int): Boolean = {
      if(t <= 1) true
      else n % t != 0 && isPrimeUntil(t-1)
    }

    isPrimeUntil(n / 2)
  }
  println(isPrime(10))
}
