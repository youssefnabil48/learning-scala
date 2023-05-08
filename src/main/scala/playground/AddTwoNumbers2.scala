package org.learning
package playground

import scala.annotation.tailrec

/**
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order, and each of their nodes contains a single digit.
 * Add the two numbers and return the sum as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807.
 *
 */

object AddTwoNumbers2 extends App {

  def addTwoNumberLists(l1: List[Int], l2: List[Int]) = {
    val l1ValueReversed = l1.reverse.foldLeft("")((concatinatedString, y) => concatinatedString + y.toString).toInt
    val l2ValueReversed = l2.reverse.foldLeft("")((concatinatedString, y) => concatinatedString + y.toString).toInt
    (l1ValueReversed + l2ValueReversed).toString.reverse.split("").toList.map(_.toInt)
  }

  println(addTwoNumberLists(List(2,4,3), List(5,6,4)))

}