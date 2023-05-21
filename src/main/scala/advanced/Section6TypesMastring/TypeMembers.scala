package org.learning
package advanced.Section6TypesMastring

/**
 * Notes:
 * 1. type members are in theory alternatives to generics
 * 2. type members can be overridden
 * 3. type members only used for type referencing and cannot be instantiated
 * 4. type members can have bounds (upper and lower bound)
 * 5. type aliasing often used in conflicting type naming from different packages
 * 6. using .type member holds the type of the instance and can be used in referencing nut not instantiating
 * 7. Type members CANNOT have variance (only invariant generic type)
 */
object TypeMembers extends App {
  class Animal
  class Dog extends Animal
  class Cat extends Animal
  object AnimalCollection {
    type AnimalType
    type BoundedAnimal <: Animal
    type SuperBoundedAnimal >: Dog <: Animal
    type AnimalC = Cat // Type aliasing
  }

//  val dog: AnimalCollection.AnimalType = new Dog // simply what is the "AnimalType", all i know it's a type but this just it
//  val cat: AnimalCollection.BoundedAnimal = new Cat // even though i know it's a subclass of animal but i still don't know anything about it
  val pup: AnimalCollection.SuperBoundedAnimal = new Dog // i know it's a super type of dog so i can instantiate a dog from it so that's fine
  val cat: AnimalCollection.AnimalC = new Cat // i know that AnimalC is = to Cat so that's fine

  // aliasing
  type CatAlias = Cat
  val anotherCat: CatAlias = new Cat

  // .type
  type CatsType = cat.type
  val newCat: CatsType = cat

  // usage as alternative of generic types (Invariance only)
  trait MyList {
    type T
    def add(element: T): MyList
    def another(element: T): T
  }
  class NonEmptyList(value: Int) extends MyList {
    override type T = Int
    def add(element: T): MyList = this
    def another(element: T): T = element
  }

  class Parent
  class Child
  class VarianceTest extends MyList {
    override type T = Parent
    override def add(element: Parent): MyList = this
    override def another(element: Parent): Parent = element
  }
  val varianceTest = new VarianceTest
//  varianceTest.another(new Child) // doesn't work so only as invariant type parameter

//  Exercise WHY IT's NOT WORKING IN SCALA 3
//  trait MList {
//    type A <: Number
//    def head: A
//    def tail: MList
//  }
//
//   class CustomList(hd: String, tl: CustomList) extends MList {
//     type A = String
//     def head = hd
//     def tail = tl
//   }
//
//  class IntList(hd: Int, tl: IntList) extends MList {
//    type A = Int
//    def head = hd
//    def tail = tl
//  }


}
