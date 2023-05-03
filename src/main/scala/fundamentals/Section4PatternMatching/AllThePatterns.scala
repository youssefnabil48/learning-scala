package org.learning
package fundamentals.Section4PatternMatching

import fundamentals.Excersices.{EmptyList, MyList, NodesList}

object AllThePatterns extends App {

  // 1 - constants
  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "THE Scala"
    case true => "The Truth"
    case AllThePatterns => "A singleton object"
  }
  println(constants)

  // 2 - match anything
  val matchAVariable = x match {
    case something => s"I've found $something"
  }
  println(matchAVariable)

  // 3 - tuples
  val aTuple = (1, 2)
  val matchATuple = aTuple match {
    case (1, 1) =>
    case (something, 2) => s"I've found $something"
  }
  println(matchATuple)

  val nestedTuple = (1, (2, 3))
  val matchANestedTuple = nestedTuple match {
    case (_, (2, v)) => s"v: ${v}"
  }
  println(matchANestedTuple)

  // 4 - case classes - constructor pattern
  val aList: MyList[Int] = NodesList(1, NodesList(2, EmptyList()))
  val matchAList = aList match {
    case EmptyList() => new EmptyList
    case NodesList(head, NodesList(subhead, subTail)) => s"$head, $subhead, $subTail"
  }
  println(matchAList)

  // 5 - list patterns
  val aStandardList = List(1, 2, 3, 42)
  val standardListMatching = aStandardList match {
    case List(1, _, _, _) => 11 // extractor - advanced
    case List(1, _*) => 12 // list of arbitrary length - advanced
    case 1 :: List(_) => 13 // infix pattern
    case List(1, 2, _) :+ 42 => "lala" // infix pattern
  }
  println(standardListMatching)

  // 6 - type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] => list // explicit type specifier
    case _ => "noMatch"
  }
  println(unknownMatch)

  // 7 - name binding
  val nameBindingMatch = aList match {
    case nonEmptyList @ NodesList(_, _) => nonEmptyList // name binding => use the name later(here)
    case NodesList(1, rest @ NodesList(2, _)) => rest // name binding inside nested patterns
  }
  println(nameBindingMatch)

  // 8 - multi-patterns
  val multipattern = aList match {
    case EmptyList() | NodesList(0, _) => // compound pattern (multi-pattern)
    case _ => "anything"
  }
  println(multipattern)

  // 9 - if guards
  val secondElementSpecial = aList match {
    case NodesList(_, NodesList(specialElement, _)) if specialElement % 2 == 0 => specialElement
  }
  println(secondElementSpecial)

//  will throw exception because anything is tried to get accessed before the match expression leads to NullPointerException
//  val anything: Any = null
//  anything match {
//    case _: RuntimeException | _: NullPointerException => ""
//  }
//  println(anything)

  val numbers = List(1, 2, 3)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of strings"
    case listOfNumbers: List[Int] => "a list of numbers"
    case _ => ""
  }

  println(numbersMatch)  //print a list of strings (erasure due to jvm compatibility)

}