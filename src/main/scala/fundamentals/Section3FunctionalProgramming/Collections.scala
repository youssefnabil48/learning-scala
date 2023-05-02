package org.learning
package fundamentals.Section3FunctionalProgramming

import scala.util.Random

object Collections extends App {

  /**
   * Notes:
   * 1. foreach vs map is foreach only used for applying side effects map returns a new list so used for expressions
   * 2. the function that is taken for flatmap is the  function applied as the normal map function except the result is flattened
   * 3. a list can have mixed types as the generic type of the standard list is A*
   * 4. for-comprehensions is the syntax sugar for the chain of map, flatmap and filters
   * 5. the difference between arrays and list is immutability and arrays have the same implementation as java
   * 6. difference between list and vectors are in performance the vectors are better in middle insertions and retrievals
   * 7. lists: keeps reference to tail and updating an element in the middle takes long
   * 8. depth of the tree is small and needs to replace an entire 32-element chunk
   * 9. a -> b is sugar for (a, b) (tuples)
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

  // Seq
  val aSequence = Seq(1, 3, 2, 4)
  println(aSequence)
  println(aSequence.reverse)
  println(aSequence(2))
  println(aSequence ++ Seq(7, 5, 6))
  println(aSequence.sorted)

  // Ranges
  val aRange: Seq[Int] = 1 until 10
  aRange.foreach(println)

  (1 to 10).foreach(x => println("Hello"))

  val aList = List(1, 2, 3)
  val prepended = 42 +: aList :+ 89
  println(prepended)

  val apples5 = List.fill(5)("apple")
  println(apples5)
  println(aList.mkString("-|-"))

  val numbersArr = Array(1, 2, 3, 4)
  val threeElements = Array.ofDim[String](3)
  threeElements.foreach(println)

  numbersArr(2) = 0 // syntax sugar for numbers.update(2, 0)
  println(numbers.mkString(" "))

  // implicit conversions whenever possiable
  val numbersSeq: Seq[Int] = numbers
  println(numbersSeq)

  val vector: Vector[Int] = Vector(1, 2, 3)
  println(vector)

  // calculating insertion types between lists and vectors
  val maxRuns = 1000
  val maxCapacity = 1000000

  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random()
    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), r.nextInt())
      System.nanoTime() - currentTime
    }

    times.sum * 1.0 / maxRuns
  }

  val numbersList = (1 to maxCapacity).toList
  val numbersVector = (1 to maxCapacity).toVector

  println(getWriteTime(numbersList))
  println(getWriteTime(numbersVector))

  val aTuple: (Int, String) = (2, "hello, Scala") // Tuple2[Int, String] = (Int, String)

  println(aTuple._1) // 2
  println(aTuple.copy(_2 = "goodbye Java"))
  println(aTuple.swap) // ("hello, Scala", 2)

  val aMap: Map[String, Int] = Map()

  val phonebook = Map(("Jim", 555), "Daniel" -> 789, ("JIM", 9000)).withDefaultValue(-1)
  println(phonebook)

  println(phonebook.contains("Jim"))
  println(phonebook("Mary"))

  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhonebook = phonebook + newPairing
  println(newPhonebook)

  // conversions to other collections
  println(phonebook.toList)
  //  println(List(("Daniel", 555)).toMap)
  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  println(names.groupBy(name => name.charAt(0)))
  println(names.groupBy(name => name.charAt(0) == 'J'))
}