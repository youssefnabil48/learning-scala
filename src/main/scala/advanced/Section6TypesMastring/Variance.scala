package org.learning
package advanced.Section6TypesMastring


/**
 * Notes:
 * 1. Invariant types works in all cases
 * 2. Covariance types works in [member vals, return types] (covariant positions)
 * 3. Contravariance types works in [parameter types] (contravariant positions)
 */
object Variance extends App {

//  // UNCOMMENT TO SEE ERRORS
//  object Invariance {
//    class Class[T](val value: T, var variable: T) {
//      def action(param: T): T = param
//    }
//  }
//
//  // UNCOMMENT TO SEE ERRORS
//  object Covariance {
//    class Class[+T](val value: T, var variable: T) {
//      def action(param: T): T = param
//    }
//  }
//
//  // UNCOMMENT TO SEE ERRORS
//  object Contravariance {
//    class Class[-T](val value: T, var variable: T) {
//      def action(param: T): T = param
//    }
//  }

//  class Parent
//  class Child extends Parent
//  class MyClass[-T] {
//    def action(param: T): T = param
//  }
//
//  val instance: MyClass[Parent] = new MyClass[Parent]
//  instance.action(new Child) // why the compiler not just forbid this call

}
