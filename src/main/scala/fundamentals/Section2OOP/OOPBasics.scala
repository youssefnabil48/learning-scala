package org.learning
package fundamentals.Section2OOP

object OOPBasics extends App {


  class Person(val name: String, age: Int) { // constructor signature
    // fields
    val field1: String = "hello"

    // this side effects is evaluated at the instantiation of a class
    println(1 + 4)
    println(field1)

    // methods
    def greet(name: String): String = s"${this.name} says: Hi, $name"

    // signature differs
    // OVERLOADING
    def greet(): String = s"Hi, everyone, my name is $name"

    // aux constructor
    def this(name: String) = this(name, 0)

    def this() = this("Jane Doe")
  }

  println(new Person("John", 26))
  println(new Person().name)
}