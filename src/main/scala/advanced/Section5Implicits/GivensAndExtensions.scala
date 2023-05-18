package org.learning
package advanced.Section5Implicits

/**
 * Notes:
 * 1. given <=> implicit val
 * 2.
 */
object GivensAndExtensions extends App {

  val aList = List(1,2,3,4)
//  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  given reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  println(aList.sorted)
}
