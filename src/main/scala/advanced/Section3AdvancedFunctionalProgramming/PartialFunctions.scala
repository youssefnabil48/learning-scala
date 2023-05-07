package org.learning
package advanced.Section3AdvancedFunctionalProgramming

/**
 * In Scala, a partial function is a function that is only defined for certain values of its input parameter(s)
 * 1. Partial functions extend normal functions
 * 2. Partial functions can only have ONE parameter type
 * 3. I think it is only meant for a shorthand or syntactic sugar for smaller code
 *    (it only a normal function with a match statement that has no default case to throw error if the value is out of range)
 *    {1,2,5} => Int or throws an error
 * 4. isDefinedAt is a function that is used to check a value is valid in the PF domain and return boolean
 * 5. lifted is a function is used to lift the PF ot normal function that return Option(returnType) instead of throwing exception
 * 6. orElse chain function is a function that is used to define a function to run if the main PF throws an error
 */
object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }
  println(aPartialFunction(2))
  println(aPartialFunction.isDefinedAt(67))
  println(aPartialFunction.toString())
  println(aPartialFunction.unapply(1))
//  println(aPartialFunction.clone()) protected member function

  val lifted = aPartialFunction.lift // Int => Option[Int]
  println(lifted(2))
  println(lifted(98))

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }
  println(pfChain(2))
  println(pfChain(45))


  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  val aMappedList = List(1, 2, 3).map {
    case 1 => 42
    case 2 => 78
    case 3 => 1000
  }
  println(aMappedList)

  def aConstructedPartialFunction: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {
    // another value can be added but it will throw match error if called
    // x == 1 || x == 2 || x == 3 || x == 4
    override def isDefinedAt(x: Int): Boolean = {
      if (x == 1 || x == 2 || x == 3) true else false
    }

    override def apply(v1: Int): Int = v1 match {
      case 1 => 42
      case 2 => 78
      case 3 => 1000
    }
  }
  println(aConstructedPartialFunction(1))
  println(aConstructedPartialFunction.isDefinedAt(4))
  println(aConstructedPartialFunction.isDefinedAt(5))
//  println(aConstructedPartialFunction(5)) throws match error

  def aDumbChatBot: PartialFunction[String, String] = {
    case "hello" => "Hello there"
    case "tell me your name" => "my name is chatbot1"
    case "how old are you?" => "i am 0 years old, you just created me"
  }
  println(aDumbChatBot("hello"))
  println(aDumbChatBot("how old are you?"))
//  println(aDumbChatBot("how old are you??")) throws match error

  print("$ ")
  scala.io.Source.stdin.getLines().foreach(line => {
    if(aDumbChatBot.isDefinedAt(line))
      println( aDumbChatBot(line))
    else {
      println("invalid input")
    }
    print("$ ")
  })
}