package org.learning
package advanced.exercises

import scala.annotation.tailrec

/**
 * naturals = MyStream.from(1)(x => x + 1) = stream of natural numbers (potentially infinite!)
 * naturals.take(100).foreach(println) // lazily evaluated stream of the first 100 naturals (finite stream)
 * naturals.foreach(println) // will crash - infinite!
 * naturals.map(_ * 2) // stream of all even numbers (potentially infinite)
 */
abstract class MyStream[+A] {
  def isEmpty: Boolean
  val head: A
  val tail: MyStream[A]

  def #::[B >: A](element: B): MyStream[B]  // prepend operator
  def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] // concatenate two streams

  def foreach(f: A => Unit): Unit
  def map[B](f: A => B): MyStream[B]
  def flatMap[B](f: A => MyStream[B]): MyStream[B]
  def filter(predicate: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A]
  def takeAsList(n: Int): List[A]

  @tailrec
  final def toList[B >: A](acc: List[B] = Nil): List[B] =
    if (isEmpty) acc.reverse
    else tail.toList(head :: acc)
}

object MyStream {
  def from[A](start: A)(generator: A => A): MyStream[A] = new FullMyStream[A](start, from(generator(start))(generator))
}

class EmptyMyStream extends MyStream[Nothing] {
  override def isEmpty: Boolean = true

  override lazy val head: Nothing = throw new NoSuchElementException()

  override lazy val tail: MyStream[Nothing] = throw new NoSuchElementException()

  override def #::[B >: Nothing](element: B): MyStream[B] = new FullMyStream[B](element, this)

  override def ++[B >: Nothing](anotherStream: => MyStream[B]): MyStream[B] = anotherStream

  override def foreach(f: Nothing => Unit): Unit = ()

  override def map[B](f: Nothing => B): MyStream[B] = this

  override def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this

  override def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  override def take(n: Int): MyStream[Nothing] = this

  override def takeAsList(n: Int): List[Nothing] = Nil
}

class FullMyStream[+A](h: A, t: => MyStream[A]) extends MyStream[A] {
  override def isEmpty: Boolean = false

  override val head: A = h

  override lazy val tail: MyStream[A] = t

  override def #::[B >: A](element: B): MyStream[B] = new FullMyStream[B](element, this)

  override def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] = new FullMyStream[B](head, tail ++ anotherStream)

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  def map[B](f: A => B): MyStream[B] = new FullMyStream[B](f(head), tail.map(f))

  def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatMap(f)

  def filter(predicate: A => Boolean): MyStream[A] =
    if (predicate(head)) new FullMyStream[A](head, tail.filter(predicate))
    else tail.filter(predicate)

  override def take(n: Int): MyStream[A] = {
    if (n <= 0) new EmptyMyStream
    else if (n == 1) new FullMyStream(head, new EmptyMyStream)
    else new FullMyStream(head, tail.take(n - 1))
  }

  override def takeAsList(n: Int): List[A] = this.take(n).toList()
}

object SteamPlayground extends App {
  val naturals = MyStream.from(1)(_ + 1)
  println(naturals.head)
  println(naturals.tail.head)
  println(naturals.tail.tail.head)

  val startFrom0 = 0 #:: naturals // naturals.#::(0)
  println(startFrom0.head)

  startFrom0.take(10000).foreach(println)

  println(startFrom0.map(_ * 2).take(100).toList())
  println(startFrom0.flatMap(x => new FullMyStream(x, new FullMyStream(x + 1, new EmptyMyStream))).take(10).toList())
  println(startFrom0.filter(_ < 10).take(10).take(20).toList())
}