package org.learning
package advanced.exercises

import scala.annotation.tailrec

trait MySet[A] extends (A => Boolean){
  override def apply(v1: A): Boolean = contains(v1)
  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A]
  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(f: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit
}

class EmptyMySet[A]() extends MySet[A] {
  override def apply(v1: A) = false
  override def contains(elem: A): Boolean = false
  override def +(elem: A): MySet[A] = new FullMySet[A](elem, this)
  override def ++(anotherSet: MySet[A]): MySet[A] = anotherSet
  override def map[B](f: A => B): MySet[B] = new EmptyMySet()
  override def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptyMySet()
  override def filter(f: A => Boolean): MySet[A] = new EmptyMySet()
  override def foreach(f: A => Unit): Unit = ()
}

class FullMySet[A](head: A, tail: MySet[A]) extends MySet[A] {
//  override def apply(v1: A): Boolean = contains(v1)
  override def contains(element: A): Boolean = element == head || tail.contains(element)
  override def +(element: A): MySet[A] = {
    if(this.contains(element)) return this
    new FullMySet[A](element, this)
  }
  override def ++(anotherSet: MySet[A]): MySet[A] = anotherSet ++ tail + head
  override def map[B](f: A => B): MySet[B] = new FullMySet[B](f(head), tail.map(f)) // (tail map f) + f(head)
  // concat deals with the two sets and decompose then into it's components
  override def flatMap[B](f: A => MySet[B]): MySet[B] = (tail flatMap f) ++ f(head)
  override def filter(f: A => Boolean): MySet[A] = {
    if(f(head)) (tail filter f) + head
    else tail filter f
  }
  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }
}

case object FullMySet {
  def apply[A](elements: A*): MySet[A] = {
    @tailrec
    def setBuilder(values: Seq[A], acc: MySet[A]): MySet[A] = {
      if(values.isEmpty) return acc
      setBuilder(values.tail, acc + values.head)
    }
    setBuilder(elements, new EmptyMySet[A])
  }
}

object Testing extends App {
  val myset = new FullMySet[Int](1, new FullMySet[Int](2, new FullMySet(3, new EmptyMySet())))
  myset.foreach(print)
  println("")
  val mappedMySet = myset.map(_ + 1)
  mappedMySet.foreach(print)
  println("")
  val concatenatedSet = myset ++ mappedMySet
  concatenatedSet.foreach(print)
  println("")
  val filteredSet = concatenatedSet.filter(_ % 2 == 0)
  filteredSet.foreach(print)
  println("")

  FullMySet(1, 2, 3).foreach(print)
}