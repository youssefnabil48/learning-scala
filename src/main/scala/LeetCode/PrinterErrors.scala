package org.learning
package LeetCode

object PrinterErrors extends App {
  def printerError(s: String): String = s.toLowerCase.count(c => c.toByte > 109) + "/" + s.length
  println(printerError("aaabbbbhaijjjm"))
  println(printerError("aaaxbbbbyyhwawiwjjjwwm"))
}