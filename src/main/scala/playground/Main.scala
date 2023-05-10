package org.learning
package playground

import advanced.Section3AdvancedFunctionalProgramming.Monads.Attempt


object Main {
  def main(args: Array[String]): Unit = {
    // values
    val defaultInt: Int = 1;
    println(defaultInt)
    val implicitTypeInt = 1;
    println(implicitTypeInt)
    val valueWithoutSemiColon = 12
    println(valueWithoutSemiColon)
    val basicString = "hello there";
    println(basicString)
    val interpolatedString = s"hi there $basicString"
    println(interpolatedString)
    val characterLiteral: Char = 'g'
    println(characterLiteral)
    val basicDouble: Double = 1.1
    println(basicDouble)
    val basicFloat: Float = 11.2f
    println(basicFloat)
    val basicBoolean: Boolean = true
    println(basicBoolean)
    val nullValue: Null = null // null can be assigned to value
    println(nullValue)
    val assignNullToString: String = null
    println(assignNullToString)
    val printAssignedNull: String = null
    println(printAssignedNull)
//    val printAssignedNullToDouble: Double = null => gives as error due to implicit conversion
//    var unassignedVariable: Double; => result in compiler error variables must be init
    val (var1: Int, var2: Int) = (1,2) // multiple assignment using tuples
    println(var1)
    println(var2)
    //Mutable values
    var mutableVariable = "hello there"
    mutableVariable = "hii"
    println(mutableVariable)
//    mutableVariable = 1 => will result in compilation error due to change in type
    var x: Any = 1
    println(x)
    x = 2
    println(x)
    x = "hello"
    println(x)
    x = '1'
    println(x)

    class Point(var x: Int, var y: Int) {
      def move(dx: Int, dy: Int): Unit = {
        x += dx;
        y += dy;
      }

      def printCoordinates(): Unit = {
        println(s"x: $x, y: $y")
      }
    }

    val point = new Point(1,12)
    point.printCoordinates()
    point.move(10, 2)
    point.printCoordinates()

    // specifying a Unit as return type won't fire compilation error but will result in unexpected output
    def addReturningUnit(num1: Int, num2: Int): Unit = {
      num1 + num2
    }
    println(addReturningUnit(1,3)) // output  => ()

    def addReturningInt(num1: Int, num2: Int): Int = {
      num1 + num2
    }
    println(addReturningInt(1,5)) // output => 6

//    def addReturningString(num1: Int, num2: Int): String = { compiler error specifying wrong return type
//      num1 + num2
//    }
//    println(addReturningString(1, 5))

    def functionWithinAFunction(x: Int, y: Int): Int = {
      def innerFunction(x: Int, y: Int): Int = x + y
      innerFunction(x, y)
    }
    println(functionWithinAFunction(3, 4))

    def recursivePrintLoop(word: String, loopCount: Int): String = {
      if (loopCount == 1) return word // removing return here will lead to infinite recursive calls
      word + recursivePrintLoop(word, loopCount - 1)
    }
    println(recursivePrintLoop("Hello", 3))

    def show(x: Option[String]) = x match {
      case Some(s) => s
      case None => "?"
    }

    val capitals = Map("France" -> "Paris", "Japan" -> "Tokyo")

    println("show(capitals.get( \"Japan\")) : " + show(capitals.get("Japan")))
    println("show(capitals.get( \"India\")) : " + show(capitals.get("India")))

    println(show(Some("kasjfkaj")))
    println(show(None))

    def isIntOrString(t: Either[Int, String]): String = {
      t match {
        case Left(i) => "%d is an Integer".format(i)
        case Right(s) => "%s is a String".format(s)
      }
    }

    println(isIntOrString(Left(10))) // prints "10 is an Integer"
    println(isIntOrString(Right("hello"))) // prints "hello is a String"

    val myLinkedList = new MyLinkedList[Int]()
    val populatedList = myLinkedList.add(1).add(2).add(3).add(4).add(5)
    println(populatedList.head.get.next.get.next.get.next.head.data)
    println(populatedList.traverse())

    val attempt = Attempt {
      throw new RuntimeException("oh my goooooddd")
    }
    val functionx = null
    val attempt2 = Attempt(functionx)
    println(attempt2)

    println(Option(null))

    val incFunction: Int => Int = (n: Int) => n + 1
    def incMethod(n: Int): Int = n + 1
  }
}