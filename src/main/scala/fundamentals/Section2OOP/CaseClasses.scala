package org.learning
package fundamentals.Section2OOP

object CaseClasses {

  /*
   1. class parameters are fields
   2. sensible toString => println(instance) = println(instance.toString) // syntactic sugar
   3. sensible equals, hashCode and toString implementation out of the box
   4. CCs have handy copy methods
   5. CCs have companion objects
   6. CCs are serializable (used in akka for example)
   7. CCs have extractor patterns = CCs can be used in PATTERN MATCHING
  */
  case class Person(name: String, age: Int)
  val jim = new Person("Jim", 34)
  println(jim.name)

  println(jim)

  val jim2 = new Person("Jim", 34)
  println(jim == jim2)

  val jim3 = jim.copy(age = 45)
  println(jim3)

  val thePerson = Person
  val mary = Person("Mary", 23)

  // case can be used on objects except they don't have a class
  case object UnitedKingdom {
    def name: String = "The UK of GB and NI"
  }

}