package org.learning
package fundamentals.Section2OOP

object Generics extends App {

  class Mylist[A] {}
  class MyMap[A, B]
  class MyMap2[key, value] // it can be any name

  val listOfIntegers = new Mylist[Int]
  val TwoParamList = new MyMap[String, Int]

  object Mylist {
    def empty[A]: Mylist[A] = new Mylist[A]
  }

  val emptyList = Mylist.empty[String]

  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // 1. yes, List[Cat] extends List[Animal] = COVARIANCE
  class CovariantList[+A]

  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // animalList.add(new Dog) ??? HARD QUESTION => we return a list of Animals

  // 2. NO = INVARIANCE
  class InvariantList[A]

  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]

  // 3. Hell, no! CONTRAVARIANCE
  class Trainer[-A]

  val trainer: Trainer[Cat] = new Trainer[Animal]

  // bounded types
  class Cage[A <: Animal](animal: A)

  val cage = new Cage(new Dog)
  val cage2 = new Cage[Dog](new Dog)

  class Car
  // generic type needs proper bounded type
  //  val newCage = new Cage(new Car)

  trait MyPredicate[T] {
    def test(t: T): Boolean
  }

  class MyPredicateImplementor extends MyPredicate[Int] {
    override def test(t: Int): Boolean = if(t == 1) true else false
  }

  val myPredicateImplementorObject = new MyPredicateImplementor()
  println(myPredicateImplementorObject.test(1))

  trait Transformer[A,B] {
    def transform(value: A): B
  }

  class TransformerImplementor extends Transformer[Int, String] {
    override def transform(value: Int): String = value.toString
  }

  val transformerImplementorObject = new TransformerImplementor()
  println(transformerImplementorObject.transform(1))

}