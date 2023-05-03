package org.learning
package advanced.Section2AdvancedScala

/**
 * NOTES:
 * 1. In Scala, the unapply method is a special method used in conjunction with pattern matching to extract values from objects.
 * 2. The unapply method is defined in an object and takes a single argument of the same type as the object.
 *    It returns an Option type, which is either Some if the object matches a certain pattern, or None if it does not.
 *    The Some type contains the extracted values.
 * 3. If the unapply method returned a Boolean type instead of an Option, it would still be valid,
 *    but the returned value would have a different meaning.
 *    When the unapply method returns a Boolean type,
 *    it is typically used to check if a given object matches a particular pattern.
 *    In this case, the method returns true if the object matches the pattern, and false otherwise.
 * 4. In short unapply => Some(value) or None for match and mismatch and to extract value
 *             unapply => true or false for match and mismatch but without extracting the value
 * 5. unapplySeq is also special and it is used to decompose variable number of elements in any seq or subtype of seq
 *    "the unapplySeq method is similar to the unapply method, but it is used when we want to extract a variable number of values from an object."
 * 6. For pattern matching, unapply method doesn't have to always return an option it can return custom object
 *    but this custom object has to fulfil these two conditions
 *    condition one: to have isEmpty function that returns boolean
 *    condition two: to have get function that returns something
 *    if the custom object met these two conditions it will work and be used by match expression just like an object
 *    it just happen that Option wrapper has these two methods so it works by default
 * 7. infix pattern can also be used in patten matching
 */
object AdvancedPatternMatching extends App {

  println(1 :: List(1))
  println(1 :: Nil)
//  println(1 :: 2) will not compile In Scala,
//  Nil is an object that represents an empty list. It is a subtype of List[Nothing] and is defined in the scala package.
  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => s"the first and only element is $head and it is just added in the pattern"
    case _ => "not a list"
  }
  println(description)

  class Person (val name: String, val age: Int)
  object Person {
    def unapply(person: Person): Option[(String, Int)] = Some((person.name, person.age))
    def unapply(age: Int): Option[String] = Some(if(age >= 25) "major" else "minor")
  }

  val bob = new Person("bob", 26)

  val greeting = bob match {
    case Person(name, age) => s"Hello My name is $name and my age is $age"
  }
  println(greeting)

  val legality = bob.age match {
    case Person(status) => status
  }
  println(legality)

  object NegativeNumber{
    def unapply(arg: Int): Option[Int] = if (arg < 0) Some(arg) else None
  }
  object PositiveLessThan100 {
    def unapply(arg: Int): Option[Int] = if (arg > 0 && arg < 100) Some(arg) else None
  }
  object ThreeDigitNumber {
    def unapply(arg: Int): Option[Int] = if (arg >= 100 && arg < 1000) Some(arg) else None
  }
  object BiggerThan1000 {
    def unapply(arg: Int): Option[Int] = if (arg >= 1000) Some(arg) else None
  }

  val number = 1000
  val numberMatch = number match {
    case NegativeNumber(number) => s"the number $number is less than 0"
    case PositiveLessThan100(number) => s"the number $number is between 0 and 100"
    case ThreeDigitNumber(number) => s"the number $number has three digit number"
    case BiggerThan1000(number) => s"the number $number bigger tha 1000"
    case _ => "No match found"
  }
  println(numberMatch)

  // Tutorial implementation
  object even {
    def unapply(arg: Int): Boolean = arg % 2 == 0
  }
  object singleDigit {
    def unapply(arg: Int): Boolean = arg > -10 && arg < 10
  }
  val n: Int = 8
  val mathProperty = n match {
    case singleDigit() => "single digit"
    case even() => "an even number"
    case _ => "no property"
  }
  println(mathProperty)
  println(mathProperty)

  // infix patterns
  case class Or[A, B](a: A, b: B)
  val either = Or(2, "two")
  val humanDescription = either match {
    case number Or string => s"$number is written as $string"
  }
  println(humanDescription)

  // decomposing sequences
  val vararg = numbers match {
    case List(1, _*) => "starting with 1"
  }
  println(vararg)

  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }
  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]
  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
  }
  // the return type of this expression can be hold in a variable l using the generator naming
  // i don't know if there is another way
  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Empty)))))
  val decomposed = myList match {
    case l @ MyList(1, 2, _*) => s"starting with 1, 2 $l"
    case _ => "something else"
  }
  println("decomposed: " + decomposed)

  // custom object for pattern matching instead of Option
  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }
  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      def isEmpty: Boolean = false
      def get: String = person.name
    }
  }
  println(bob match {
    case PersonWrapper(n) => s"This person's name is $n"
    case _ => "An alien"
  })
}