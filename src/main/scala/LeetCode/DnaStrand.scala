package org.learning
package LeetCode

import javax.naming.directory.InvalidAttributesException

object DnaStrand extends App {
  /**
   * Deoxyribonucleic acid (DNA) is a chemical found in the nucleus of cells and carries the "instructions" for the development and functioning of living organisms.
   *
   * If you want to know more: http://en.wikipedia.org/wiki/DNA
   *
   * In DNA strings, symbols "A" and "T" are complements of each other, as "C" and "G". Your function receives one side of the DNA (string, except for Haskell); you need to return the other complementary side. DNA strand is never empty or there is no DNA at all (again, except for Haskell).
   *
   */
  private def makeComplement(dna: String): String = {
    val charList = for {
      c <- dna
    } yield {
      c match {
        case 'T' => 'A'
        case 'A' => 'T'
        case 'C' => 'G'
        case 'G' => 'C'
        case _ => throw new InvalidAttributesException("DNA should only contain A, T, C, G")
      }
    }
    charList.foldLeft("")(_ + _)
  }

  private def makeComplementMap(dna: String): String = {
    dna.map {
      case 'T' => 'A'
      case 'A' => 'T'
      case 'C' => 'G'
      case 'G' => 'C'
      case _ => throw new InvalidAttributesException("DNA should only contain A, T, C, G")
    }
  }

//  println(makeComplement("AATTAAGCB")) Throws Exception
  println(makeComplement("CGAT"))
  println(makeComplementMap("CGAT"))

}