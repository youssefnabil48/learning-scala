package org.learning
package advanced.Section5Implicits

object TypeClasses extends App {

  //==================================================================================
  // Type class Example
  //==================================================================================

  /**
   * the main contract
   */
  private trait JsonSerializer[T] {
    def serialize(value: T): String
  }

  /**
   * the companion object
   */
  private object JsonSerializer {
    def serialize[T](value: T)(implicit serializer: JsonSerializer[T]): String = serializer.serialize(value)

    def apply[T](implicit serializer: JsonSerializer[T]): JsonSerializer[T] = serializer
  }

  private implicit object UserSerializer extends JsonSerializer[User] {
    override def serialize(user: User): String = s"{name: ${user.name}, age: ${user.age}, email: ${user.email}}"
  }

  private implicit object StudentSerializer extends JsonSerializer[Student] {
    override def serialize(student: Student): String = s"{name: ${student.name}, id: ${student.id}, grade: ${student.grade}}"
  }

  private implicit object IntSerializer extends JsonSerializer[Int] {
    override def serialize(value: Int): String = s"{value: $value}"
  }

  /**
   * the function apply implicitly determine the proper implementor by specifying the type in the function generic type
   * ex: this returns an instance of int serializer
   */
  println(JsonSerializer[Int])

  case class User(name: String, age: Int, email: String)
  private case class Student(id: Int, name: String, grade: String)


  val user = User("john", 18, "john@user.com")
  println(JsonSerializer.serialize(10))
  println(JsonSerializer.serialize(user))
  private val student = Student(20146015, "ahmed" , "grade 10")
  println(JsonSerializer.serialize(student))

  /**
   * direct calls of implementors is allowed
   */
  private val userJson = UserSerializer.serialize(user)
  private val studentJson = StudentSerializer.serialize(student)
  println(userJson)
  println(studentJson)


}
