package org.learning
package advanced.Section3AdvancedFunctionalProgramming

/**
 * A monad is a design pattern that provides a way to structure computations that involve chaining operations together.
 * Monads are widely used in Scala as a way to handle computations that have side effects or that involve handling of errors.
 * 1. Monad is like a wrapper that encapsulate a computation or value to deal with under the hood
 * 2. Monad is a kind of types that must some fundamental operations:
 *    1) unit (pure or apply) => this function takes a value of type T and returns a wrapped value T in the monad wrapper
 *    2) flatmap (bind) => this HOR function takes a function as a parameter this function takes value of type T
 *                         and return wrapped value T in the monad wrapper
 * 3. Monad must satisfy the three laws monads
 *    1) left identity  => unit(x).flatMap(f) == f(x)
 *    2) right identity => aMonadInstance.flatMap(unit) == aMonadInstance
 *    3) associativity  => aMonadInstance.flatMap(f).flatMap(g) == aMonadInstance.flatMap(x => f(x).flatMap(g))
 */
object Monads extends App {

  // A custom try monad that might throw exception
  // This monad wrap a computation that might throw exception
  trait Attempt[+A] {
    // This is the flatMap or bind function (fundamental operations)
    def flatMap[B](f: A => Attempt[B]): Attempt[B]
  }
  object Attempt {
    // This is the apply / unit / pure function (fundamental operations)
    // Here we are actually applying what the monad basic function is doing or wrapping in our case trying something and catching exceptions
    def apply[A](a: => A): Attempt[A] = {
      try Success(a)
      catch {
        case e: Throwable => Failure(e)
      }
    }
  }

  case class Success[+A](value: A) extends Attempt[A] {
    override def flatMap[B](f: A => Attempt[B]): Attempt[B] = {
      try f(value)
      catch {
        case e: Throwable => Failure(e)
      }
    }
  }

  case class Failure(e: Throwable) extends Attempt[Nothing] {
    override def flatMap[B](f: Nothing => Attempt[B]): Attempt[B] = this // Failure(e)
  }


  // Lazy monad exercise
  class Lazy[+A](value: => A) {
    private lazy val internalValue = value
    def use: A = internalValue // unit
    def flatMap[B](f: (=> A) => Lazy[B]): Lazy[B] = f(internalValue) // bind
  }

  object Lazy {
    def apply[A](value: => A): Lazy[A] = new Lazy(value)
  }

  val l = Lazy {
    println("naahh i won't work")
    42
  }

  private val instance1 = l.flatMap(x => Lazy { 10 * x })
  private val instance2 = l.flatMap(x => Lazy { 10 * x })
  instance1.use
  instance2.use

}