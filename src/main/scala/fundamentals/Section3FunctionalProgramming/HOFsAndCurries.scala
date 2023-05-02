package org.learning
package fundamentals.Section3FunctionalProgramming

import scala.annotation.tailrec

object HOFsAndCurries extends App {

  def nTimes(f: Int => Int, n: Int, x: Int): Int =
    if (n <= 0) x
    else nTimes(f, n-1, f(x))

  val plusOne = (x: Int) => x + 1
  println(nTimes(plusOne, 10, 1))

  val multiplyTwo = (x: Int) => x * 2
  println(nTimes(multiplyTwo, 2, 5))

  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) =
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n - 1)(f(x))

  val plus10 = nTimesBetter(plusOne, 2)
  println(plus10(1))

  val superAdder: Int => (Int => Int) = (x: Int) => (y: Int) => x + y
  println(superAdder(3)(10))

  val add3 = superAdder(3)
  println(add3(10))


  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  val standardFormat: (Double => String) = curriedFormatter("%4.2f")
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

  println(standardFormat(Math.PI))
  println(preciseFormat(Math.PI))

  def cFunction(x: Int)(y: Int)(a: String): Int = {
    x * y * a.toInt
  }
  println(cFunction(1)(2)("3"))

  def mixedParamCurriedFunction(x: Int, y: Int)(a: String, b: Int)(m: Int) = {
    x + y * a.toInt + b + m
  }
  println(mixedParamCurriedFunction(1,2)("5", 2)(1))
//  quite complicated
//  def toCurry(f: (Int, Int) => Int): (Int => Int => Int) =
//    x => y => f(x, y)
//
//  def fromCurry(f: (Int => Int => Int)): (Int, Int) => Int =
//    (x, y) => f(x)(y)
//
//  def compose[A, B, T](f: A => B, g: T => A): T => B =
//    x => f(g(x))
//
//  def andThen[A, B, C](f: A => B, g: B => C): A => C =
//    x => g(f(x))
}