package org.learning
package fundamentals.Excersices

import java.util.function.Predicate

abstract class MyList[+A] {

  def head: A

  def tail: MyList[A]

  def isEmpty: Boolean

  def addElement[B >: A](element: B): MyList[B]

  def toString: String

  def printElements: String
}

class EmptyList extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException()
  override def tail: MyList[Nothing] = throw new NoSuchElementException()
  override def isEmpty: Boolean = true
  override def addElement[B >: Nothing](element: B): MyList[B] = new NodesList(element, tail)
  override def toString: String = ""
  override def printElements: String = ""
}

class NodesList[A](h: A, t: MyList[A]) extends MyList[A] {
  override def head: A = h
  override def tail: MyList[A] = t
  override def isEmpty: Boolean = false
  override def addElement[B >: A](element: B): MyList[B] = new NodesList(element, this)
  override def toString: String = {
    def printElements(list: MyList[A]): String = {
      if(list.isEmpty) return ""
      list.head.toString + " " + printElements(list.tail)
    }
    val nodes = printElements(this)
    "[" + nodes + "]"
  }
  override def printElements: String = {
    if (this.isEmpty) ""
    else this.head.toString + " " + printElements
  }
}

trait Transformer[A, B] {
  def transform(value: A): B
}
trait MyPredicate[T] {
  def test(t: T): Boolean
}

object MyList extends App {
  val list = new NodesList[Int](1, new NodesList(2, new NodesList(3, new EmptyList)))
  println(list.tail.tail.head)
  println(list.toString)

  // we Cannot add a Proper list if it's not a covariant list because we wont be able to add EmptyList to node list
  val listOfStrings = new NodesList[String]("hello", new NodesList[String]("there", new NodesList[String]("!", new EmptyList)))
  println(listOfStrings.toString)
}