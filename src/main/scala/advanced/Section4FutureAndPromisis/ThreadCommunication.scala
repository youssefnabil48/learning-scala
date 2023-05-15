package org.learning
package advanced.Section4FutureAndPromisis


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

  // wait and notify

  private class SimpleContainer {
    private var v: Int = 0
    def isEmpty: Boolean = v == 0
    def value_=(newValue: Int): Unit = v = value
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

  producerConsumer()
}
