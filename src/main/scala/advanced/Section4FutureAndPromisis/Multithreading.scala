package org.learning
package advanced.Section4FutureAndPromisis

import java.util.concurrent.Executors

/**
 * Notes:
 * 1. scala multithreading has the same base on the jvm as java
 * 2. instantiating a new thread instance that implements runnable interface in java and override the abstract run method
 * 3. starting a thread with thread.start
 * 4. invoking method run on the runnable object doesn't do anything in parallel
 * 5. blocking the thread till it's completed with thread.
 * 6. execution order is not guaranteed by any means in multithreading (OS control)
 * 7. another way to execute parallel code is to use executors pool (abstract the thread management)
 * 8. pool.shutdown methods don't allow any new threads to be initiated but finishes the already running threads
 * 9. pool.shutdownNow method don't allow any new threads to be initiated and kill all running threads
 * 10. without a shutdown statement over the pool the jvm will not terminate
 */
object Multithreading extends App {

  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("this is an implementation of run function in runnable interface or trait")
  })
  aThread.start()
  aThread.join()
  println("End of file")

  // there is different type of thread pools
  val fixedPool = Executors.newFixedThreadPool(100)
  fixedPool.execute(() => (1 to 10).foreach(number => { Thread.sleep(10); println("executing in thread number 1") }))
  fixedPool.execute(() => (1 to 10).foreach(number => { Thread.sleep(10); println("executing in thread number 2") }))
  fixedPool.execute(() => (1 to 10).foreach(number => { Thread.sleep(10); println("executing in thread number 3") }))

  fixedPool.shutdown()
  fixedPool.execute(() => { println("executing in a thread after shutdown") })
  fixedPool.shutdownNow()
}
