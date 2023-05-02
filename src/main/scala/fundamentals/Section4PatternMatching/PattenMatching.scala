package org.learning
package fundamentals.Section4PatternMatching

import org.learning.fundamentals.Section4PatternMatching.PattenMatching.Number

import scala.util.Random


/**
 * Notes:
 * 1. cases are matched in order
 * 2. if no match the MatchError is thrown
 * 3. the return type of match expression is the unified type of all the types in all the cases
 * 4. pattern matching works really well with case classes*
 */
object PattenMatching extends App {

  // switch on steroids
  val random = new Random
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "the ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else" // _ = WILDCARD
  }
  println(x)
  println(description)

  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)
  val greeting = bob match {
    case Person(n, a) if a < 21 => s"Hi, my name is $n and I can't drink in the US"
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
    case _ => "I don't know who I am"
  }
  println(greeting)

  // pattern matching with sealed classes makes the compiler gives an error if a subtype if the class has no case
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal
  val animal: Animal = Dog("Terra Nova")
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed")
  }

  // match everything
  val isEven = x match {
    case n if n % 2 == 0 => true
    case _ => false
  }
  println(isEven)


  // Exercise
  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr


  // My Implementation
  def show_(expression: Expr): String = {
    expression match {
      case Number(n) => s"$n"
      case Sum(e1, e2) => show_(e1) + " + " + show_(e2)
      case Prod(e1, e2) => show_(e1) + " * " + show_(e2)
    }
  }

  println(show_(Sum(Number(2), Number(3))))
  println(show_(Sum(Sum(Number(2), Number(3)), Number(4))))
  println(show_(Prod(Sum(Number(2), Number(1)), Number(3))))
  println(show_(Prod(Sum(Number(2), Number(1)), Sum(Number(3), Number(4)))))
  println(show_(Sum(Prod(Number(2), Number(1)), Number(3))))
  println("-------------------------")

  // tutorial implementation

  def show(e: Expr): String = e match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
    case Prod(e1, e2) => {
      def maybeShowParentheses(exp: Expr) = exp match {
        case Prod(_, _) => show(exp)
        case Number(_) => show(exp)
        case _ => "(" + show(exp) + ")"
      }

      maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
    }
  }
  println(show(Sum(Number(2), Number(3))))
  println(show(Sum(Sum(Number(2), Number(3)), Number(4))))
  println(show(Prod(Sum(Number(2), Number(1)), Number(3))))
  println(show(Prod(Sum(Number(2), Number(1)), Sum(Number(3), Number(4)))))
  println(show(Sum(Prod(Number(2), Number(1)), Number(3))))

}