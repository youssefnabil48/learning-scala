package org.learning
package advanced.Section6TypesMastring

/**
 * Notes:
 * 1. a Self type is denoted as trait MyTrait { anyName: MyMustMixinTrait => }
 * 2. self types is defined as a requiring a type to be mixin
 *    - SELF TYPE whoever implements MyTrait must mixin and implement to MyMustMixinTrait
 * 3. The cake pattern is the scala way to do dependency injection
 * 4. the difference between dependency injection and cake pattern is at applying time (compiletime for cake and runtime for DI)
 *    - by making a class or trait have self Types it has access to all the members and defs at compile time "as injected"
 */
object SelfTypes extends App {

  trait Instrumentalist {
    def play(): Unit
  }
  trait Singer { this: Instrumentalist =>
    def sing(): Unit
  }
  class LeadSinger extends Singer with Instrumentalist {
    override def play(): Unit = ()
    override def sing(): Unit = ()
  }
  //  NOTE: this is not applicable by the compiler
  //  class Vocalist extends Singer {
  //    override def sing(): Unit = ???
  //  }

  val jamesHetfield = new Singer with Instrumentalist {
    override def play(): Unit = ()
    override def sing(): Unit = ()
  }

  class Guitarist extends Instrumentalist {
    override def play(): Unit = println("(guitar solo)")
  }

  val ericClapton = new Guitarist with Singer {
    override def sing(): Unit = ()
  }



  class Component {}
  class ComponentA extends Component
  class ComponentB extends Component
  class DependentComponent(val component: Component)

  // CAKE PATTERN
  trait ScalaComponent {
    def action(x: Int): String
  }
  trait ScalaDependentComponent { self: ScalaComponent =>
    def dependentAction(x: Int): String = self.action(x) + " this rocks!"
  }
  trait ScalaApplication { self: ScalaDependentComponent with ScalaComponent => }

  class MyScalaApplication extends ScalaApplication with ScalaDependentComponent with ScalaComponent {
    override def action(x: Int): String = x.toString

  }
  val scala = new MyScalaApplication
  println(scala.action(1))
  println(scala.dependentAction(1))

  class TestClass { self: ScalaComponent =>
    self.action(1)
  }

  trait cDependency extends ScalaComponent{
    override def action(x: Int): String = x.toString
  }

  val c = new TestClass with cDependency
  println(1)

}
