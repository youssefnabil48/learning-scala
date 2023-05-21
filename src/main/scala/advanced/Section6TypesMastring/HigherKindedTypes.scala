package org.learning
package advanced.Section6TypesMastring


/**
 * Notes:
 * 1. Higher Kinded Types is The trait/class that have a type parameter that accept type parameter on it's own
 *    - same like higher order function naming a function that takes a function
 *    - a HKT a type that take a type
 */
object HigherKindedTypes extends App {
  // the main problem
  // we have multiple monads that we can apply for comprehension to them and we want to implement multiply function
  // for type safety we will be required to implement same implementation of multiply 2 times to adhere to the monad types
  // the solution is to wrap all the functionality in a generic type which takes in the type parameter a type that takes a type parameter in it's own (List)
  trait AHigherKindedType[F[_]]

  //  def multiply[A, B](listA: List[A], listB: List[B]): List[(A, B)] =
  //    for {
  //      a <- listA
  //      b <- listB
  //    } yield (a, b)
  //
  //  def multiply[A, B](listA: Option[A], listB: Option[B]): Option[(A, B)] =
  //    for {
  //      a <- listA
  //      b <- listB
  //    } yield (a, b)

  trait Monad[F[_], A] {
    def flatMap[B](f: A => F[B]): F[B]

    def map[B](f: A => B): F[B]
  }

  implicit class MonadList[A](list: List[A]) extends Monad[List, A] {
    override def flatMap[B](f: A => List[B]): List[B] = list.flatMap(f)

    override def map[B](f: A => B): List[B] = list.map(f)
  }

  implicit class MonadOption[A](option: Option[A]) extends Monad[Option, A] {
    override def flatMap[B](f: A => Option[B]): Option[B] = option.flatMap(f)

    override def map[B](f: A => B): Option[B] = option.map(f)
  }

  def multiply[F[_], A, B](ma: Monad[F, A], mb: Monad[F, B]): F[(A, B)] =
    for {
      a <- ma
      b <- mb
    } yield (a, b)

  println(multiply(List(1, 2), List("a", "b")))
  println(multiply(Some(2), Some("scala")))


  trait CollectionWrapper[T[_]] {
    def wrap[A](a: A): T[A]

    def first[A](b: T[A]): A
  }

  object ListCollection extends CollectionWrapper[List] {
    override def wrap[A](list: A): List[A] = List(list)

    override def first[B](list: List[B]): B = list.head
  }

  ListCollection.wrap(List(1, 2, 3)).foreach(println)
  ListCollection.wrap(List(true, false, true)).foreach(println)


}
