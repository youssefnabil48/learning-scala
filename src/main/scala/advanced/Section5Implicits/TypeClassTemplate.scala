package org.learning
package advanced.Section5Implicits

object TypeClassTemplate extends App {

  trait MyTypeClassTemplate[T] {
    def action(value: T): String
  }

  object MyTypeClassTemplate {
    def apply[T](implicit instance: MyTypeClassTemplate[T]): MyTypeClassTemplate[T] = instance
  }

  implicit object StringImplementor extends MyTypeClassTemplate[String] {
    override def action(value: String): String = "desired string from action"
  }

}
