package org.learning
package fundamentals.Section3FunctionalProgramming

object Collections extends App {

  /**
   * Notes:
   * 1. foreach vs map is foreach only used for applying side effects map returns a new list so used for expressions
   * 2. the function that is taken for flatmap is the  function applied as the normal map function except the result is flattened
   * 3. a list can have mixed types as the generic type of the standard list is A*
   * 4. for-comprehensions is the syntax sugar for the chain of map, flatmap and filters
   */
  val list = List(1, 2, 3)
  println(list.head)
  println(list.tail)
  val listOfStrings = List("hi", "hello", "greetings")
  println(listOfStrings.head)
  println(listOfStrings.tail)

  val mixedList = List(1, "hello", 'c')
  println(mixedList)

  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))
  println(list.foreach(_ + 1))
  println(list.length)

  println(list.filter(_ % 2 == 0))

  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair))

  // print all combinations between two lists
  val numbers = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c', 'd')
  val colors = List("black", "white")

  val combinations = numbers.filter(_ % 2 == 0).flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + "-" + color)))
  println(combinations)

  // for-comprehensions

  for {
    n <- numbers
  } println(n)

  val forCombinations = for {
    n <- numbers if n % 2 == 0
    c <- chars
    color <- colors
  } yield "" + c + n + "-" + color
  println(forCombinations)

  val fruits = List("apple", "banana", "orange", "pear")

  var result = for {
    fruit <- fruits
    if fruit.startsWith("a")
  } yield fruit.toUpperCase
  println("result : " +result)

  val result2 = for {
    fruit <- fruits
    element <- list
  } yield println(fruit + element)
  println("result2 : " + result2)

  val words = List("hello", "world", "scala", "functional", "programming")
  val filteredWords = for {
    word <- words // if word.length > 5
    letter <- word
  } yield letter.toUpper

  println("words to upper: " + filteredWords)

  val evens = for (i <- 1 to 10 if i % 2 == 0) yield i
  println("evens: " + evens)

  val arr = Array(1, 2, 3, 4, 5)

  val result3 = for {
    i <- arr.indices if arr(i) % 2 == 0 // (SHORT HAND FOR i <- 0 until arr.length) Generator expression: iterate over the indices of the array
  } yield arr(i) * 2
  println("result3 : " + result3.mkString(", "))

  val result4 = for {
    (fruit, count) <- Map("apple" -> 1, "banana" -> 2, "cherry" -> 3) if count % 2 == 1 // same as destructing syntax in js
  } yield fruit
  println("result4 : " + result4.mkString(", "))

  val result5 = for {
    x <- List(1, 2, 3) // Generator expression: iterate over each element of list1
    y <- List(4, 5, 6) if x + y < 8 // Nested generator expression: iterate over each element of list2
  } yield (x, y)
  println("result5 : " +result5)

  // syntax overload
  println(list.map { x =>
    val y = 2
    x * y
  })

  println(list.map {
    _ * 2
  })
}