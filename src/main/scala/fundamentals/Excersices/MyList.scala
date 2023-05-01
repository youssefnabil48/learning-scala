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
  def map[B](transformer: A => B): MyList[B]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]
  // concatenation function
  def ++[B >: A](list: MyList[B]): MyList[B] = list
}

case class EmptyList() extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException()
  override def tail: MyList[Nothing] = throw new NoSuchElementException()
  override def isEmpty: Boolean = true
  override def addElement[B >: Nothing](element: B): MyList[B] = new NodesList(element, tail)
  override def toString: String = ""
  override def printElements: String = ""
  override def map[B](transformer: Nothing => B): MyList[B] = EmptyList()
  override def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = EmptyList()
  override def filter(predicate: Nothing => Boolean): MyList[Nothing] = EmptyList()
  // empty list concatenated with anything will return the same thing
  override def ++[B](list: MyList[B]): MyList[B] = list
}

case class NodesList[A](h: A, t: MyList[A]) extends MyList[A] {
  override def head: A = h
  override def tail: MyList[A] = t
  override def isEmpty: Boolean = false
  override def addElement[B >: A](element: B): MyList[B] = NodesList(element, this)
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

  // the base case of this function is in the EmptyNode Class Implementation
  override def map[B](transformer: A => B): MyList[B] = NodesList(transformer(h), t.map(transformer))
  override def ++[B >: A](list: MyList[B]): MyList[B] = NodesList(h, t ++ list)
  override def flatMap[B](transformer: A => MyList[B]): MyList[B] = transformer(h) ++ t.flatMap(transformer)

  // the base case of this function is in the EmptyNode Class Implementation
  // if the predicate has passed and returned true in the if condition the ead will be included in the new otherwise it won't
  override def filter(predicate: A => Boolean): MyList[A] = {
    if (predicate(h)) NodesList(h, t.filter(predicate))
    else t.filter(predicate)
  }
}

object MyList extends App {
  val list = new NodesList[Int](1, NodesList(2, NodesList(3, new EmptyList)))
  println(list.tail.tail.head)
  println(list.toString)

  // we Cannot add a Proper list if it's not a covariant list because we wont be able to add EmptyList to node list
  val listOfStrings = NodesList[String]("hello", NodesList[String]("there", NodesList[String]("!",new EmptyList)))
  println(listOfStrings.toString)

  val cloneListOfIntegers: MyList[Int] = NodesList(1, NodesList(2, NodesList(3, new EmptyList)))
  val anotherListOfIntegers: MyList[Int] = NodesList(4, NodesList(5, new EmptyList))

  println(list.toString)
  println(listOfStrings.toString)

  println(list.map(x => x * 2).toString)

  println(list.filter(x => x % 2 == 0).toString)

  println((list ++ anotherListOfIntegers).toString)
  // Don't quite understand what is going on
  println(list.flatMap(elem => NodesList(elem, NodesList(elem + 1, new EmptyList))).toString)

  println(cloneListOfIntegers == list)
}