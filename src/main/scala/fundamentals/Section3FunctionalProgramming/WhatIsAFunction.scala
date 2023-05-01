package org.learning
package fundamentals.Section3FunctionalProgramming

object WhatIsAFunction extends App {

  trait MyFunction[A, B] {
    def apply(element: A): B
  }

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  // function types = Function1[A, B]
  val stringToIntConverter = new Function1[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntConverter("3") + 4)

  val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }
  adder(2,1)

  private val subtract: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(x: Int, y: Int): Int = x-y
  }
  println(subtract(2, 1))

  // single abstract method
  private val concatenation: ((String, String) => String) = (str1: String, str2: String) => str1 + str2

  println(concatenation("hello", "There"))

  val functionThatTakeIntAndReturnFunction: (Int => Int => Int) = new Function[Int, Int => Int] {
    override def apply(number: Int): Int => Int = new Function[Int, Int] {
      override def apply(multiplier: Int): Int = number*multiplier
    }
  }

  println(functionThatTakeIntAndReturnFunction(5)(10))

  // How to do it
  val functionThatTakesAFunctionAsParam: ((Int => Int, Int) => Int) = (inner: Int => Int, x: Int) => inner(x)
  def paramFunction(param1: Int): Int = param1
  println(functionThatTakesAFunctionAsParam(paramFunction, 2))

}