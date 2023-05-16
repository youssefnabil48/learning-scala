package org.learning
package advanced.Section5Implicits

/**
 * Objective:
 * we'll take a look at how the compiler looks for simplicity and discuss various organizing strategies so that we can get the maximum
 * benefit of simplicity without confusing the compiler.
 * (How the compiler looks for simplicity and where and which implies it's have priority over others)
 *
 * Notes:
 * 1. scala.predef package is already imported by default in any scala file exists
 * 2. implicits parameters types:
 *    - val/vars
 *    - object
 *    - accessor methods (defs with no parentheses)
 * 3. implicits parameters must be only defined within class, object and trait (cannot be top level)
 *    this rule applies only on implicits that the compiler tries to pass as an implicit parameter
 * 4. Implicits scope is the place where the compiler searches for implicits values
 * 5. Implicits scope hierarchy (priority):
 *    - local scope.
 *    - imported scope.
 *    - the companion objects of all the types involved in the function signature:
 *      - in the example of sorted: sorted[B:>A](implicit ord: Ordering[B]): List[] the compiler will look in the following companion objects
 *        1) List
 *        2) Ordering
 *        3) A or any supertype of A
 * 6. the compiler must match only one implicit value otherwise it will throw an error
 */
object ImplicitsOrdering extends App {

  /**
   * ordering implicit example
   */
  val numbers = List(1,2,3,5,4)
//   implicit def reverseIntOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  implicit def normalIntOredring: Ordering[Int] = new Ordering[Int] {
    override def compare(x: Int, y: Int): Int = if(x < y) 1 else if(x > y) -1 else 0
  }
  println(numbers.sorted) // what is import scala.math.Numeric.IntIsIntegral (appears when compiler is confused

  /**
   * Person sorting exercise
   */
  case class Person(name: String,age: Int)
  val persons = List(
    Person("ahmed", 20),
    Person("mohamed", 30),
    Person("john", 10)
  )
  implicit def PersonAgeOrderingAscending: Ordering[Person] = Ordering.fromLessThan(_.age < _.age)
//  implicit def PersonAgeOrderingDescending: Ordering[Person] = Ordering.fromLessThan(_.age > _.age)
  println(persons.sorted)

  /**
   *  - totalPrice = most used (50%)
   *  - by unit price = 30%
   *  - by unit count = 20%
   *  total Price is Most used implicit value so we define it in the companion object
   *  unit price is second in probability so it goes in a separate object to be imported
   *  unit count is third in probability so it goes in the local scope to be implemented on the spot
   *
   */
  case class Purchase(nUnits: Int, unitPrice: Double)
  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.nUnits * a.unitPrice < b.nUnits * b.unitPrice)
  }
  object UnitPriceOrdering {
    implicit val unitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.unitPrice < _.unitPrice)
  }
  implicit val unitCountOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.nUnits < _.nUnits)
}
