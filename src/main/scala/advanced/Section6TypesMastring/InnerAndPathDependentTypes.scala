package org.learning
package advanced.Section6TypesMastring


/**
 * Notes:
 * 1. the compiler doesn't allow member types in code blocks unless it's an alias
 * 2. classes that are defined inside another object or classes (inner classes) is instance scoped
 *    - it means this class is not the same class of another instance
 * 3. All inner classes have the same parent as "OuterClass#InnerClass" (still not the same but share the same parent)
 */
object InnerAndPathDependentTypes extends App {

  class Outer {
    class Inner
    object InnerObject
    type InnerType
    def print(i: Inner) = println(i)
    def printGeneral(i: Outer#Inner) = println(i)
  }

  def aMethod: Int = {
    class HelperClass
    type HelperType = String
    2
  }

  // per-instance
  val o = new Outer
  val inner = new o.Inner // o.Inner is a TYPE
  val oo = new Outer
  //  val otherInner: oo.Inner = new o.Inner
  o.print(inner)
  //  oo.print(inner)

  // path-dependent types
  // Outer#Inner
  o.printGeneral(inner)
  oo.printGeneral(inner)

}
