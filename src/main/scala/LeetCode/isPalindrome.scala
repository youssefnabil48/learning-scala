package org.learning
package LeetCode

object isPalindrome extends App {
  private def isPalindrome(x: Int): Boolean = {
    x.toString == x.toString.reverse
  }

  private val testInt = 123
  println(isPalindrome(testInt))
}
