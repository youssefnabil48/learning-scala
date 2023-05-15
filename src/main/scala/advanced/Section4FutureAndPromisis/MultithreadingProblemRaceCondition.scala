package org.learning
package advanced.Section4FutureAndPromisis

import java.util.concurrent.Executors
import scala.util.Random

/**
 * - Race condition definition: a race condition is a situation where the outcome of a computation depends
 *                              on the sequence or timing of other uncontrollable events.
 *                              Race conditions can occur when multiple threads or processes are accessing the same data at the same time.
 *                              This can lead to unexpected results, such as data corruption or system crashes.
 * - Multiple threads reading the same value of x and modifying the variable leading unexpected result
 *   due to the uncertainty of thread execution.
 * - To avoid race condition with vars one of the two options must be provided
 *    1. synchronized method and putting the critical section inside
 *         - This method ensures that no other thread is executing this part of the code at the same time
 *         - it locks the object monitor (object monitor is a data structure used by the jvm to lock objects shared with many threads)
 *         - only anyRefs can only have synchronized methods (anyRefs all - primitive types)
 *         - make no assumption on which thread gets the lock first
 *    2. @volatile var (used with variables only)
 *         - this variable annotation ensures that all threads see the most recent value of the variable at any given time
 * -
 */
object MultithreadingProblemRaceCondition extends App {

  // problem statement
  // ==========================================================
  val executorsPool = Executors.newFixedThreadPool(10)
  var x = 1
  executorsPool.execute(() => {
    Thread.sleep(Random.nextInt(10))
    x = 1
  })
  executorsPool.execute(() => {
    Thread.sleep(Random.nextInt(10))
    x = 2
  })
  executorsPool.execute(() => {
    Thread.sleep(Random.nextInt(10))
    x = 3
  })
  println(x)
  //===========================================================

  // Buying example
  // ==========================================================
  class BankAccount(var amount: Int) {}

  def buy(account: BankAccount, price: Int): Unit = {
    account.amount -= price
  }

  for (_ <- 1 to 1000) {
    val account = new BankAccount(50000)
    val thread1 = new Thread(() => buySafe(account,3000))
    val thread2 = new Thread(() => buySafe(account,4000))

    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join()
    if (account.amount != 43000) println("Race condition occurred: " + account.amount)
  }

  // option #1: use synchronized()
  def buySafe(account: BankAccount, price: Int): Unit = {
    account.synchronized {
      buy(account, price)
    }
  }

  // option #2 use @volatile vars
  @volatile var volatileVariable = 1
  new Thread(() => { volatileVariable += 1; println(volatileVariable) }).start()
  new Thread(() => { volatileVariable += 1; println(volatileVariable) }).start()
  new Thread(() => { volatileVariable += 1; println(volatileVariable) }).start()
  new Thread(() => { volatileVariable += 1; println(volatileVariable) }).start()
  new Thread(() => { volatileVariable += 1; println(volatileVariable) }).start()
  //===========================================================

}
