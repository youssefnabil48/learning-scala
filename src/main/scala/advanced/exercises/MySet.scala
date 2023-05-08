package org.learning
package advanced.exercises

import scala.annotation.tailrec


/**
 * Notes: A property based set is a special type of set that doesn't hold data of it's own
 * it just mimic the basic functionality of basic functions like contains, add, remove and so on
 * it has a predicate in which this predicate identifies if the element exists in the set or not
 * it is just a container of a function like: x % 2 == 0 this set holds all the even numbers (doesn't hold them in memory)
 * all elements of type A which satisfy a property { x in A | property(x) }
 * this property based set cannot implement map, flatmap and foreach because it is undetermined if this set is infinite
 */

trait MySet[A] extends (A => Boolean){
  override def apply(v1: A): Boolean = contains(v1)
  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A]
  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(f: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit
  def -(element: A): MySet[A]
  def remove(element: A): MySet[A]
  def &(anotherSet: MySet[A]): MySet[A]
  def intersection(anotherSet: MySet[A]): MySet[A]
  def --(anotherSet: MySet[A]): MySet[A]
  def difference(anotherSet: MySet[A]): MySet[A]
  def unary_! : MySet[A]
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
  override def -(element: A): MySet[A] = new EmptyMySet[A]
  override def &(anotherSet: MySet[A]): MySet[A] = new EmptyMySet[A] // or this
  override def --(anotherSet: MySet[A]): MySet[A] = this // or new EmptySet[A]
  override def remove(element: A): MySet[A] = this.-(element)
  override def intersection(anotherSet: MySet[A]): MySet[A] = this.&(anotherSet)
  override def difference(anotherSet: MySet[A]): MySet[A] = this.--(anotherSet)
  def unary_! : MySet[A] = new PropertyBasedSet[A](_ => true)
}
class PropertyBasedSet[A](property: A => Boolean) extends MySet[A] {
  def contains(elem: A): Boolean = property(elem)
  // { x in A | property(x) } + element = { x in A | property(x) || x == element }
  def +(elem: A): MySet[A] =
    new PropertyBasedSet[A](x => property(x) || x == elem)
  // { x in A | property(x) } ++ set => { x in A | property(x) || set contains x }
  def ++(anotherSet: MySet[A]): MySet[A] =
    new PropertyBasedSet[A](x => property(x) || anotherSet(x))
  // all integers => (_ % 3) => [0 1 2]
  def map[B](f: A => B): MySet[B] = politelyFail
  def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail
  def foreach(f: A => Unit): Unit = politelyFail
  def filter(predicate: A => Boolean): MySet[A] = new PropertyBasedSet[A](x => property(x) && predicate(x))
  def -(element: A): MySet[A] = filter(x => x != element)
  def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)
  def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)
  override def remove(element: A): MySet[A] = this - element
  override def intersection(anotherSet: MySet[A]): MySet[A] = this & anotherSet
  override def difference(anotherSet: MySet[A]): MySet[A] = this -- anotherSet
  def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))
  private def politelyFail = throw new IllegalArgumentException("Really deep rabbit hole!")
}
class FullMySet[A](val head: A, val tail: MySet[A]) extends MySet[A] {
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
  override def -(element: A): MySet[A] = this.filter(_ != element) // if(element == head) tail else tail - element + head
  override def &(anotherSet: MySet[A]): MySet[A] = this.filter(anotherSet.contains) // this.filter(x => anotherSet.contains(x))
  override def --(anotherSet: MySet[A]): MySet[A] = this.filter(x => !anotherSet.contains(x))
  override def remove(element: A): MySet[A] = this.-(element)
  override def intersection(anotherSet: MySet[A]): MySet[A] = this.&(anotherSet)
  override def difference(anotherSet: MySet[A]): MySet[A] = this.--(anotherSet)
  def unary_! : MySet[A] = new PropertyBasedSet[A](x => !this.contains(x))
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

  (FullMySet(1, 2, 3) remove 3).foreach(print)
  println("")
  (FullMySet(1,2,3) & FullMySet(2,3,5)).foreach(print)
  println("")
  (FullMySet(1, 2, 3) -- FullMySet(2, 3, 5)).foreach(print)
  println("")
  (FullMySet(2, 3, 5, 6) -- FullMySet(1, 2, 3)).foreach(print)
  println("")

  // why it doesn't have a type of PropertyBasedSet
  private val negativeSet = !myset
  println(negativeSet(2))
  println(negativeSet(5))
  val allInclusiveSet = new PropertyBasedSet[Int](_ => true) // the all inclusive set of domain Int
  println(allInclusiveSet(Int.MaxValue + 1))
}