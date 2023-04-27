package org.learning
package fundamentals.Section2OOP

/**
 * traits vs abstract classes
 * 1 - traits do not have constructor parameters
 * 2 - multiple traits may be inherited by the same class
 * 3 - traits = behavior, abstract class = "thing"
 */
object AbstractClasses extends App {

  // abstract
  // abstract classes can have abstract and non abstract methods and functions
  // not specifying the value does make the variable or the function abstract
  abstract class Animal {
    val creatureType: String = "wild"
    def eat: Unit
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"
    def eat: Unit = println("crunch crunch")
  }

  // traits
  trait Carnivore {
    def eat(animal: Animal): Unit

    val preferredMeal: String = "fresh meat"
  }

  trait ColdBlooded {
    def eat(animal: Animal): Unit

//    val preferredMeal: String = "fresh meal" will lead to inheritsConflicting members
    val preferredMeal: String
  }

  /**
   * inheriting same implemented or have value members from a trait can lead to the basic multiple inheritance problem
   * thus lead to an error as in the example of the preferredMeal that is inherited from the two traits Carnivore and coldblooded
   * and it lead to an error but the ide have no idea
   * it can be solved by overriding the method or the variable
   */
  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"

    def eat: Unit = println("nomnomnom")

    def eat(animal: Animal): Unit = println(s"I'm a croc and I'm eating ${animal.creatureType}")
  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)
  println(croc.preferredMeal)
}