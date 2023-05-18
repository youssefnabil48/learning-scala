package org.learning
package advanced.Section5Implicits

import java.util.Date

object JsonSerialization extends App {

  case class User(name: String, age: Int, email: String)
  case class Post(id: Int, content: String, createdAt: Date)
  case class Feed(user: User, posts: List[Post])

  // Implementation of TC pattern
  trait JSONSerializer[T] {
    def serialize(value: T): String
  }

  implicit object StringSerializer extends JSONSerializer[String] {
    override def serialize(value: String): String = s"\"$value\""
  }

  implicit object UserSerializer extends JSONSerializer[User] {
    override def serialize(user: User): String =
      s"{name :${StringSerializer.serialize(user.name)} ," +
        s"age:${user.age.toString}, " +
        s"email:${StringSerializer.serialize(user.email)}" +
        s"}"
  }

  implicit object PostSerializer extends JSONSerializer[Post] {
    override def serialize(post: Post): String =
      s"{id:${post.id.toString}, " +
        s"content:${StringSerializer.serialize(post.content)}, " +
        s"createdAt:${post.createdAt.toString}" +
        s"}"
  }

  implicit object FeedSerializer extends JSONSerializer[Feed] {
    override def serialize(feed: Feed): String =
      s"{user:${UserSerializer.serialize(feed.user)}," +
        s"posts:${feed.posts.map(post => {
          PostSerializer.serialize(post)
        }).mkString("[", ",", "]")}" +
        s"}"
  }

  implicit class JsonEnrichment[T](value: T) {
    def toJson(implicit jsonSerializer: JSONSerializer[T]): String = jsonSerializer.serialize(value)
  }


  val user = User("ahmed", 10, "a@a.com")
  val post = Post(1, "post1", new Date())
  val posts = List(post, post)
  val feed = Feed(user, posts)
  println(user.toJson)
  println(post.toJson)
  println(feed.toJson)
//  println(posts.toJson) throws an error cannot find implicit implemnetation for List[Post]





}
