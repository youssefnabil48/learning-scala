package org.learning
package fundamentals

object CallByValue extends App {

  // call by value is the default when passing parameters it evaluate the expression at the time of the function call
  // call by name reduce evaluating the expression to each and every time this expression or value is being used in the function
  def functionCall(byName: => Long, byValue: Long): Unit = {
    println("byName: ", byName)
    println("byValue:", byValue)
  }

  println(System.nanoTime())
  val time = System.nanoTime()
  println(functionCall(byName = System.nanoTime(), byValue = time))

  def callByName(time: => Long): Unit = {
    println("time by name: ", time)
    println("time by name: ", time)
  }

  def callByValue(time: => Long): Unit = {
    println("time by value: ", time)
    println("time by value: ", time)
  }

  callByName(System.nanoTime())
  callByValue(System.nanoTime())
}