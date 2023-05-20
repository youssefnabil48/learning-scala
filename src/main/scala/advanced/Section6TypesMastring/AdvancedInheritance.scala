package org.learning
package advanced.Section6TypesMastring

/**
 * Notes:
 * 1. You can specify the type of mixin in the parameter list if you don't know what class have this structure
 * 2. A class cannot inherit from multiple traits which have conflicting methods unless they are overrides
 * 3. In case you have multiple overrides the compiler ALWAYS PICKS THE LAST OVERRIDE
 * 4. Type linearization is the process of transferring a big inheritance structure to one linear chained with statements
 * 5. The super always refer to the MOST Right (last) TYPE
 */
object AdvancedInheritance extends App {

  trait Writer[T] {
    def write(value: T): Unit
  }
  trait Closeable {
    def close(status: Int): Unit
  }
  trait GenericStream[T] {
    // some methods
    def foreach(f: T => Unit): Unit
  }
  def processStream[T](stream: GenericStream[T] with Writer[T] with Closeable): Unit = {
    stream.foreach(println)
    stream.close(0)
  }

  trait Type1 {
    def method1(): Unit = println("method1")
    def method11(): Unit = println("method11")
  }
  trait Type2 {
    def method2(): Unit = println("method2")
    def method22(): Unit = println("method22")
  }
  class Type3 {
    def method3(): Unit = println("method3")
    def method33(): Unit = println("method33")
    def method22(): Unit = println("override method22")
  }

//  class UnknownType extends Type3 with Type2 with Type1 {} // gives an conflicting members error

  def testMethod(unknownTypeName: Type1 with Type2 with Type3): Unit = {
    unknownTypeName.method1()
    unknownTypeName.method11()
    unknownTypeName.method2()
    unknownTypeName.method22()
    unknownTypeName.method3()
    unknownTypeName.method33()
  }

  trait Animal {
    def name: String
  }
  trait Lion extends Animal {
    override def name: String = "lion"
  }
  trait Tiger extends Animal {
    override def name: String = "tiger"
  }
  class Mutant extends Lion with Tiger
  val m = new Mutant
  println(m.name)

}
