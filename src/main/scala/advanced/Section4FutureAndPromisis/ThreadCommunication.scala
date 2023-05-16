package org.learning
package advanced.Section4FutureAndPromisis

import scala.collection.mutable
import scala.util.Random


/**
 * Notes:
 * Will discuss thread communication by the use of wait(), notify(), notifyAll()
 * 1. wait, notify and notifyAll only used inside synchronized expressions
 * 2. wait()      => release the lock and block the execution until notified, when notified gain the lock and proceed
 * 3. notify()    => signal one waiting thread to gain the lock and continue
 * 4. notifyAll() => signal all waiting threads gain lock and continue
 * 5. no any guarantees which thread will get the lock and proceed
 */
object ThreadCommunication extends App {

  // proving pass by reference works in scala
    case class MyMutableClass(var value: Int)
    def modifyValue(obj: MyMutableClass, newValue: Int): Unit = {
      obj.value = newValue
    }
    def incrementValue(obj: MyMutableClass): Unit = {
      obj.value += 1
    }
    val obj = MyMutableClass(10)
    println(obj.value)
    modifyValue(obj, 20)
    incrementValue(obj)
    println(obj.value)

  //=====================================================================
  // wait and notify
  //=====================================================================

  private class SimpleContainer {
    private var v: Int = 0
    def value_=(newValue: Int): Unit = v = newValue
    def value: Int = {
      val result = v
      v = 0
      result
    }
  }

  private def producerConsumer(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[consumer] waiting...")
      container.synchronized {
        container.wait()
        println("[consumer] I have consumed " + container.value)
      }
    })

    val producer = new Thread(() => {
      println("[producer] Hard at work...")
      Thread.sleep(2000)
      val value = 42

      container.synchronized {
        println("[producer] I'm producing " + value)
        container.value = value
        container.notify()
      }
    })

    consumer.start()
    producer.start()
    consumer.join()
    producer.join()
  }
  //  producerConsumer()

  //=====================================================================
  // Multiple producer consumer problem
  //=====================================================================
  // Node: If the consumers are more than the producers the consumers that didn't consume any value will still be waiting indefinitely

  def createProducers(container: => SimpleContainer, number: Int): Unit = {
    println(container)
    if(number <= 0) return
    new Thread(() => {
      println(s"[producer $number] Hard at work...")
      Thread.sleep(2000)

      container.synchronized {
        container.value = number
        println(s"[producer $number] I'm producing " + container.value)
        container.notify()
      }
    }).start()
    createProducers(container, number - 1)
  }

  def createConsumers(container: => SimpleContainer, number: Int): Unit = {
    println(container)
    if (number <= 0) return
    new Thread(() => {
      println(s"[consumer $number] waiting...")
      container.synchronized {
        container.wait()
        println(s"[consumer $number] I have consumed " + container.value)
      }
    }).start()
    createConsumers(container, number - 1)
  }
  private def multipleProducerConsumer(): Unit = {
    val container = new SimpleContainer
    createProducers(container, 10)
    createConsumers(container, 10)
  }
//  multipleProducerConsumer()

  //=====================================================================
  // 1 producer and one consumer with buffer
  //=====================================================================

  def producerConsumerBuffer(): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    val consumer = new Thread(() => {
      val random = new Random()
      while (true) {
        buffer.synchronized {
          if (buffer.isEmpty) {
            println("[consumer] buffer empty, waiting...")
            buffer.wait()
          }
          val x = buffer.dequeue()
          println("[consumer] consumed " + x)
          buffer.notify()
        }
        Thread.sleep(random.nextInt(250))
      }
    })

    val producer = new Thread(() => {
      val random = new Random()
      var i = 0
      while (true) {
        buffer.synchronized {
          if (buffer.size == capacity) {
            println("[producer] buffer is full, waiting...")
            buffer.wait()
          }
          println("[producer] producing " + i)
          buffer.enqueue(i)
          buffer.notify()
          i += 1
        }
        Thread.sleep(random.nextInt(500))
      }
    })

    consumer.start()
    producer.start()
  }
  producerConsumerBuffer()
}
