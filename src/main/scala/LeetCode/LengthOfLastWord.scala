package org.learning
package LeetCode

object LengthOfLastWord extends App {
  private def lengthOfLastWord(s: String): Int = {
    s.trim.split(" ").last.length
  }

  private val testString = "hello there my name is ahmeed"
  println(lengthOfLastWord(testString))
}
