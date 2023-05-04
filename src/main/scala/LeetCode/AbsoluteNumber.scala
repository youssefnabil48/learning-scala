package org.learning
package LeetCode

object AbsoluteNumber extends App {
  /**
   * In this simple assignment you are given a number and have to make it negative. But maybe the number is already negative?
   */

//  private object ZeroOrNegativeNumber {
//    def unapply(n: Int): Option[Int] = if (n <= 0) Some(n) else None
//  }
//  private object PositiveNumber {
//    def unapply(n: Int): Option[Int] = if (n > 0) Some(n) else None
//  }
//  private def makeNegative(n: Int): Int = n match {
//    case ZeroOrNegativeNumber(n: Int) => n
//    case PositiveNumber(n: Int) => -n
//  }
//  println(makeNegative(1))

  // OR
  private object Absolute {
    def unapply(n: Int): Option[Int] = if (n <= 0) Some(n) else Some(-n)
  }
  private def makeNegative(n: Int): Int = n match {
    case Absolute(n) => n
  }
  println(makeNegative(1))

}