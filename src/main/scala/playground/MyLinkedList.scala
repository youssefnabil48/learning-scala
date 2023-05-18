package org.learning
package playground

import scala.annotation.{tailrec, targetName}
import math.Fractional.Implicits.infixFractionalOps
import math.Integral.Implicits.infixIntegralOps
import math.Numeric.Implicits.infixNumericOps

class Node[T](val data: T, val next: Option[Node[T]] = None)

class MyLinkedList[T](private val _head: Option[Node[T]] = None) {
  def head: Option[Node[T]] = _head

  def add(data: T): MyLinkedList[T] = {
    val newNode = new Node(data)
    if (head.isEmpty) {
      new MyLinkedList[T](Some(newNode))
    } else {
      val newTail = new MyLinkedList[T](head.get.next).add(data)
      new MyLinkedList[T](Some(new Node[T](head.get.data, newTail.head)))
    }
  }

  def remove(data: T): MyLinkedList[T] = {
    if (head.isEmpty) {
      this
    } else if (head.get.data == data) {
      new MyLinkedList[T](head.get.next)
    } else {
      val newTail = new MyLinkedList[T](head.get.next).remove(data)
      new MyLinkedList[T](Some(new Node[T](head.get.data, newTail.head)))
    }
  }

  def contains(data: T): Boolean = {
    head match {
      case Some(node) => node.data == data || new MyLinkedList[T](node.next).contains(data)
      case None => false
    }
  }

  /**
   * pattern matching for reference of checking on type of option param
   * def show(x: Option[String]) = x match {
   * case Some(s) => s
   * case None => "?"
   * }
   *
   * @return
   */
  def traverse(): String = {
    extension (a: T) {
      @targetName("T")
      private def +(b: String) = a.toString + b
    }
    def traverseHelper(node: Option[Node[T]]): String = {
      node match {
        case Some(h) => {
          h.data + "," + traverseHelper(h.next)
        }
        case None => "?"
      }
    }
    "List: [" + traverseHelper(head) + "]"
  }
}