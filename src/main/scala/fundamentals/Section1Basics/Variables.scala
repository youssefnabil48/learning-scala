package org.learning
package fundamentals.Section1Basics

object Variables extends App {
  // Values
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
  val (var1: Int, var2: Int) = (1, 2) // multiple assignment using tuples
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



}