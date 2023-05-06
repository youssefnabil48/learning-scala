package org.learning
package LeetCode

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
 * https://leetcode.com/problems/add-two-numbers/
 * // TODO: Problem with this implementation is i've not reversed the elements in the list i've added them as they are
 */
object AddTwoNumberFromList extends App {
  class ListNode(_x: Int = 0, _next: ListNode = null) {
    var next: ListNode = _next
    var x: Int = _x

    def traverse(): Unit = {
      @tailrec
      def traverseHelper(l: ListNode): Unit = {
        print(l.x)
        if (l.next != null) {
          traverseHelper(l.next)
        }
      }
      traverseHelper(this)
    }
  }
  private def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
    //  Implementation of listElementsToString without aux value
    //  private def listElementsToString(l: ListNode): String = {
    //    if (l.next == null) return l.x.toString
    //    l.x.toString + listElementsToString(new ListNode(l.next.x, l.next.next))
    //  }
    @tailrec
    def listElementsToString(l: ListNode, aux: String): String = {
      if (l.next == null) return aux + l.x.toString
      listElementsToString(new ListNode(l.next.x, l.next.next), aux + l.x.toString)
    }
    val stringResult = (listElementsToString(l1, "").toInt + listElementsToString(l2, "").toInt).toString
    @tailrec
    def populateListNodeFromStringReversed(s: String, aux: ListNode): ListNode = {
      if (s.isEmpty) return aux
      populateListNodeFromStringReversed(s.drop(1), new ListNode(s(0).asDigit, aux))
    }
    populateListNodeFromStringReversed(stringResult.drop(1), new ListNode(stringResult(0).asDigit))
  }

//  addTwoNumbers(
//    new ListNode(1, new ListNode(2, new ListNode(3))),
//    new ListNode(4, new ListNode(5, new ListNode(6)))
//  ).traverse() // result should be [9,7,5]

  addTwoNumbers(
    new ListNode(2, new ListNode(4, new ListNode(9))),
    new ListNode(5, new ListNode(6, new ListNode(4, new ListNode(9))))
  ).traverse() // result should be
}