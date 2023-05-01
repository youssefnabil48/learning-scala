package org.learning
package playground

class VariousMyListImplementations {

//  Method 1

//  sealed trait LinkedList[+T] {
//    def head: Option[T]
//
//    def tail: LinkedList[T]
//
//    def isEmpty: Boolean
//
//    def add[U >: T](data: U): LinkedList[U]
//
//    def remove[U >: T](data: U): LinkedList[U]
//
//    def contains[U >: T](data: U): Boolean
//  }
//
//  final case class EmptyList[+T]() extends LinkedList[T] {
//    override def head: Option[T] = None
//
//    override def tail: LinkedList[T] = this
//
//    override def isEmpty: Boolean = true
//
//    override def add[U >: T](data: U): LinkedList[U] = Node(data, this)
//
//    override def remove[U >: T](data: U): LinkedList[U] = this
//
//    override def contains[U >: T](data: U): Boolean = false
//  }
//
//  final case class Node[+T](data: T, next: LinkedList[T]) extends LinkedList[T] {
//    override def head: Option[T] = Some(data)
//
//    override def tail: LinkedList[T] = next
//
//    override def isEmpty: Boolean = false
//
//    override def add[U >: T](data: U): LinkedList[U] = Node(this.data, this.next.add(data))
//
//    override def remove[U >: T](data: U): LinkedList[U] = {
//      if (this.data == data) {
//        this.next
//      } else {
//        Node(this.data, this.next.remove(data))
//      }
//    }
//
//    override def contains[U >: T](data: U): Boolean = {
//      this.data == data || this.next.contains(data)
//    }
//  }

//  --------------------------
//  THE BELOW CLASS NOT WORKING ADDING HAS A PROBLEM

//  case class Node[T](data: T, next: Option[Node[T]] = None)
//
//  class LinkedList[T](val head: Option[Node[T]] = None) {
//
//    def add(data: T): LinkedList[T] = {
//      val newNode = Node(data)
//      head match {
//        case Some(h) => new LinkedList(Some(h.copy(next = Some(newNode))))
//        case None => new LinkedList(Some(newNode))
//      }
//    }
//
//    def remove(data: T): LinkedList[T] = {
//      def removeRec(prev: Option[Node[T]], current: Option[Node[T]]): Option[Node[T]] = current match {
//        case Some(node) if node.data == data => prev match {
//          case Some(p) => Some(p.copy(next = node.next))
//          case None => node.next
//        }
//        case Some(node) => removeRec(Some(node), node.next)
//        case None => None
//      }
//
//      new LinkedList(removeRec(None, head))
//    }
//
//    def contains(data: T): Boolean = {
//      def containsRec(current: Option[Node[T]]): Boolean = current match {
//        case Some(node) if node.data == data => true
//        case Some(node) => containsRec(node.next)
//        case None => false
//      }
//
//      containsRec(head)
//    }
//
//  }

}