package org.learning
package fundamentals.Section2OOP

import scala.language.postfixOps

object MethodNotation extends App {
  class Person(val name: String, favoriteMovie: String, val age: Int = 0) {
    def likes(movie: String): Boolean = movie == favoriteMovie
    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"
    def +(aString: String) = s"new Person $name ($aString)"
    def unary_! : String = s"$name, what the heck?!"
    //    will not work due to restriction on the unary operators
    //    def unary_% : String = s"$name, i am just mod"
    def unary_+ : Person = new Person(this.name, favoriteMovie, age + 1)
    //    will not work due to restriction on the unary operators
    //    def unary_++ : Person = new Person(this.name, favoriteMovie, age + 1)
    def isAlive: Boolean = true
    def learns(language: String): String = s"$name learns $language"
    def learnsScala: String = this.learns("scala")
    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"
    def apply(times: Int): String = s"$name watched $favoriteMovie $times times"
  }

  val mary = new Person("Mary", "Inception")
  // infix notation = operator notation (syntactic sugar)
  println(mary.likes("Inception"))
  println(mary likes "Inception") // equivalent

  val tom = new Person("Tom", "Fight Club")
  println(mary + tom)
  println(mary.+(tom)) // equivalent

  println(1 + 2)
  println(1.+(2))

  // prefix notation (unary_ prefix only works with - + ~ !)
  println(-1)
  println(1.unary_-) // equivalent

  println(!mary)
  println(mary.unary_!)

  // postfix notation
  println(mary.isAlive)
  // discouraged to use and only available for backward compatibility
  println(mary isAlive) // only available with the scala.language.postfixOps import

  // apply
  println(mary.apply())
  println(mary()) // equivalent

  // Exercise
  println(mary + "a rock star")
  println((+mary).age)

  println(mary learns "scala")
  println(mary learnsScala)

  println(mary(5))

}