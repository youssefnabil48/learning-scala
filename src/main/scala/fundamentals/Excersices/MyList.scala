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

  def foreach(f: A => Unit): Unit
  def sort(compare: (A, A) => Int): MyList[A]
  def zipWith[B, C](list: MyList[B], zip:(A, B) => C): MyList[C]
  def fold[B](startValue: B)(operator: (B, A) => B): B
}

case class EmptyList() extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException()
  override def tail: MyList[Nothing] = throw new NoSuchElementException()
  override def isEmpty: Boolean = true
  override def addElement[B >: Nothing](element: B): MyList[B] = NodesList(element, tail)
  override def toString: String = ""
  override def printElements: String = ""
  override def map[B](transformer: Nothing => B): MyList[B] = EmptyList()
  override def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = EmptyList()
  override def filter(predicate: Nothing => Boolean): MyList[Nothing] = EmptyList()
  // empty list concatenated with anything will return the same thing
  override def ++[B](list: MyList[B]): MyList[B] = list
  override def foreach(f: Nothing => Unit): Unit = ()

  override def sort(compare: (Nothing, Nothing) => Int): MyList[Nothing] = EmptyList()

  override def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] =
    EmptyList()

  override def fold[B](startValue: B)(operator: (B, Nothing) => B): B = startValue
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
  override def foreach(f: A => Unit): Unit = {
    f(h)
    tail.foreach(f)
  }

  /**
   * Explaining what is going on here
   * we assume sorted tail by making a recursive call on the tail and passing same compare function
   * then we define an insert function that responsible to insert a new item in the right place in a sorted list
   * then come the role of the insert function
   * the insert function has 3 options
   * Option 1 if the list is the sorted list is already empty then it will return a new list with the element in the head
   * Option 2 if the element is smaller than the first element in the list assuming it sort in ascending order then
   *          we put the element in the first position and the rest of the list in the tail
   * Option 3 if the element should be in the middle of the list then we make new List with the same head because it is the right place
   *          and the call insert again with our element in the rest of the main list (tail)
   *
   * @param compare compare function to determine sorting direction
   * @return
   */
  override def sort(compare: (A, A) => Int): MyList[A] = {
    def insert(a: A, sortedList: MyList[A]): MyList[A] = {
      if(sortedList.isEmpty) new NodesList[A](a, EmptyList())
      else if(compare(a, sortedList.head) >= 0) new NodesList[A](a, sortedList)
      else new NodesList[A](sortedList.head, insert(a, sortedList.tail))
    }
    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  override def zipWith[B, C](list: MyList[B], zipFunction: (A, B) => C): MyList[C] = {
    if(list.isEmpty) throw new RuntimeException("cannot zip with empty list")
    else new NodesList[C](zipFunction(h, list.head), t.zipWith(list.tail, zipFunction))
  }

  override def fold[B](startValue: B)(operator: (B, A) => B): B = {
    t.fold(operator(startValue, h))(operator)
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

  list.foreach(x => println("each " + x))

  println(list.sort((x, y) => y - x))
  println(list.sort((x, y) => x - y))
//  println(anotherListOfIntegers.zipWith(listOfStrings, _ + "-" + _)) error _ contextual underscore
  println(anotherListOfIntegers.zipWith[String, String](listOfStrings, _ + "-" + _))
  println(list.fold(0)(_ + _))
  println(anotherListOfIntegers.fold(1)(_ + _))
}