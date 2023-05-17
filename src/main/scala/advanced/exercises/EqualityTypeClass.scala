package org.learning
package advanced.exercises

/**
 * This implementation is slightly different from the main Type Class pattern implementation in which
 * the apply method already apply the main functionality as the equality here has only one (does only one thing)
 * so the apply method instead of returning the instance of equalizer it apply the only function exactEqual directly
 */
object EqualityTypeClass extends App {

  //==================================================================================
  // Type Class Pattern Example
  //==================================================================================
  trait Equal[T] {
    def exactEqual(instanceA: T, instanceB: T): Boolean
  }

  object Equal {
    def apply[T](instanceA: T, instanceB: T)(implicit equalizer: Equal[T]): Boolean = equalizer.exactEqual(instanceA, instanceB)
  }

  implicit object UserEqual extends Equal[User] {
    override def exactEqual(userA: User, userB: User): Boolean = userA.name.equals(userB.name) && userA.age == userB.age
  }
  implicit object StudentEqual extends Equal[Student] {
    override def exactEqual(studentA: Student, studentB: Student): Boolean = studentA.name.equals(studentB.name) && studentA.id == studentB.id
  }
  implicit object IntEqual extends Equal[Int] {
    override def exactEqual(intA: Int, intB: Int): Boolean = intA == intB
  }
  //==================================================================================

  case class User(name: String, age: Int)
  case class Student(id: Int, name: String)

  println(Equal(User("user1", 13), User("user1", 13)))
  println(Equal[User](User("user1", 13), User("user1", 14)))

  println(Equal(Student(20146015, "student1"), Student(20146015, "student1")))
  println(Equal[Student](Student(20146015, "student1"), Student(20146015, "student2")))

  println(Equal(1,1))
  println(Equal(1,2))

}
