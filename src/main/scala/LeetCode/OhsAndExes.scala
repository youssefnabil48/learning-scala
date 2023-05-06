package org.learning
package LeetCode


/**
 * Check to see if a string has the same amount of 'x's and 'o's.
 * The method must return a boolean and be case insensitive.
 * The string can contain any char.
 */
object OhsAndExes extends App {
  private def xo(str: String): Boolean = {
    str.toLowerCase.count(c => c == 'o') == str.toLowerCase.count(c => c == 'x')
  }

  println(xo("xxooh"))
}