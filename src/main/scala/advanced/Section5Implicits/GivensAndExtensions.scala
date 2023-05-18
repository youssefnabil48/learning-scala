package org.learning
package advanced.Section5Implicits

import scala.language.implicitConversions

/**
 * Notes:
 *
 * // Implicit parameters (given and using)
 * 1. given <=> implicit val (in instantiating vals)
 * 2. implicit val consider the order of the declaration while givens only consider scope
 * 3. in scala 2 importing a package import the implicits all along in scala 3 with givens
 *    com.packageThatHaveGivens.given keyword must be imported in order to import givens from the package or import te given explicitly
 * 4. implicit => using (in function signature)
 *
 * // Implicit functions (Conversion)
 * 5. import scala.language.implicitConversions is required to use implicit conversions
 * 6. implicit functions has a new structure (deprecated) with specifying the given a Conversion[Any, Any] class with type parameters with overriding anon class
 *      - This statement:
 *          implicit def string2Person(string: String): Person = Person(string)
 *        Is required be converted to:
 *          given string2Person: Conversion[String, Person] with {
 *            override def apply(x: String) = Person(x)
 *          }
 *
 * // Implicit Classes (extension)
 * 7. extension statement is the replacement to explicit classes in scala 3
 * 8. explicit class MyClass(value: Type) {} <=> extension (value: Type) {}
 *      - name is omitted all together cause there is no need to it (more direct way)
 */
object GivensAndExtensions extends App {

  val aList = List(1,2,3,4)
  object OrderingWithImplicits {
    implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  }
  println(aList.sorted)
  object OrderingWithGivens {
    given reverseOrdering: Ordering[Int] with {
      override def compare(x: Int, y: Int): Int = y - x
    }
  }
  import OrderingWithGivens.given


  object GivenWith {
    given reverseOrdering: Ordering[Int] = new Ordering[Int] {
      override def compare(x: Int, y: Int): Int = y - x
    }
  }
  // scala 2
  import GivenWith._
  // scala 3
  import GivenWith.given

  // scala 2
  def extremes[A](list: List[A])(implicit ordering: Ordering[A]): (A, A) = {
    val sortedList = list.sorted
    (sortedList.head, sortedList.last)
  }
  // scala 3
  def extremes_v2[A](list: List[A])(using ordering: Ordering[A]): (A, A) = {
    val sortedList = list.sorted // (ordering)
    (sortedList.head, sortedList.last)
  }

  trait Combinator[A] {
    def combine(x: A, y: A): A
  }
  // scala 2
  implicit def listOrdering[A](implicit simpleOrdering: Ordering[A], combinator: Combinator[A]): Ordering[List[A]] =
    (x: List[A], y: List[A]) => {
      val sumX = x.reduce(combinator.combine)
      val sumY = y.reduce(combinator.combine)
      simpleOrdering.compare(sumX, sumY)
    }
  // scala 3
  given listOrdering_v2[A] (using simpleOrdering: Ordering[A], combinator: Combinator[A]): Ordering[List[A]] with {
    override def compare(x: List[A], y: List[A]): Int = {
      val sumX = x.reduce(combinator.combine)
      val sumY = y.reduce(combinator.combine)
      simpleOrdering.compare(sumX, sumY)
    }
  }

  case class Person(name: String) {
    def greet(): String = s"Hi, my name is $name."
  }
  // scala 2
  implicit def string2Person(string: String): Person = Person(string)
  val danielsGreet = "Daniel".greet()
  // scala 3 "import scala.language.implicitConversions" required
  given string2PersonConversion: Conversion[String, Person] with {
    override def apply(x: String) = Person(x)
  }
}
