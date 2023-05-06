package org.learning
package LeetCode

import scala.annotation.tailrec


/**
 * In a small town the population is p0 = 1000 at the beginning of a year.
 * The population regularly increases by 2 percent per year and moreover 50 new inhabitants per year come to live in the town.
 * How many years does the town need to see its population greater or equal to p = 1200 inhabitants?
 *
 *   At the end of the first year there will be:
 *   1000 + 1000 * 0.02 + 50 => 1070 inhabitants
 *
 *   At the end of the 2nd year there will be:
 *   1070 + 1070 * 0.02 + 50 => 1141 inhabitants (** number of inhabitants is an integer **)
 *
 *   At the end of the 3rd year there will be:
 *   1141 + 1141 * 0.02 + 50 => 1213
 *
 *   It will need 3 entire years.
 */
object GrowthOfPopulation extends App {
  private def nbYear(p0: Int, percent: Double, aug: Int, p: Int): Int = {
    @tailrec
    def populationCalculator(p0: Int, percent: Double, aug: Int, numberOfYears: Int): Int = {
      if (p0 >= p) return numberOfYears
      val populationNumber = p0 + (p0 * percent / 100).toInt + aug
      if (populationNumber >= p) numberOfYears + 1 else populationCalculator(populationNumber, percent, aug, numberOfYears + 1)
    }
    populationCalculator(p0, percent, aug, 0)
  }
  println(nbYear(1000, 2.0, 50, 1200))
  println(nbYear(1500, 5.0, 100, 5000))
  println(nbYear(1000, 2.0, 50, 1000))
}