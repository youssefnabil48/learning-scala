package org.learning
package fundamentals.Section2OOP

import scala.annotation.unused

object OOPBasicsExercise extends App {

  class Writer(firstName: String, surName: String, val yearOfBirth: Int) {
    def fullName(): String = s"$firstName $surName"
  }

  class Novel(name: String, yearOfRelease: Int, author: Writer){
    def authorAge: Int = yearOfRelease - author.yearOfBirth
    def authorName: String = author.fullName()

    def isWrittenBy(author: Writer): Boolean = author == this.author
    def copy(newYear: Int) = new Novel(name, newYear, author)
  }

  class Counter(initValue: Int = 0) {
    def count(): Int = initValue
    def increment: Counter = new Counter(initValue + 1)
    def decrement: Counter = new Counter(initValue - 1)
    // overloads
    def increment(step: Int): Counter = new Counter(initValue + step)
    def decrement(step: Int): Counter = new Counter(initValue - step)
  }

  val writer1 = new Writer("ahmed", "mohamed", 1990)
  val writer2 = new Writer("ahmed", "mohamed", 1990)
  val novel1 = new Novel("novel1", 2000, writer1)
  println(novel1.copy(2001))
  println(novel1.authorAge)
  println(novel1.isWrittenBy(writer2))
  println(novel1.authorName)

  val c = new Counter
  println(c.increment.increment.count())
  println(c.decrement.increment(10).decrement(5).count())
  
}

// tutorial implementation
/*
  Novel and a Writer
  Writer: first name, surname, year
    - method fullname
  Novel: name, year of release, author
  - authorAge
  - isWrittenBy(author)
  - copy (new year of release) = new instance of Novel
 */
class Writer(firstName: String, surname: String, val year: Int) {
  def fullName: String = firstName + " " + surname
}

class Novel(name: String, year: Int, author: Writer) {
  def authorAge = year - author.year
  def isWrittenBy(author: Writer) = author == this.author
  def copy(newYear: Int): Novel = new Novel(name, newYear, author)
}

/*
  Counter class
    - receives an int value
    - method current count
    - method to increment/decrement => new Counter
    - overload inc/dec to receive an amount
 */
class Counter(val count: Int = 0) {
  def inc = {
    println("incrementing")
    new Counter(count + 1)  // immutability
  }

  def dec = {
    println("decrementing")
    new Counter(count - 1)
  }

  def inc(n: Int): Counter = {
    if (n <= 0) this
    else inc.inc(n-1)
  }

  def dec(n: Int): Counter =
    if (n <= 0) this
    else dec.dec(n-1)

  def print = println(count)
}