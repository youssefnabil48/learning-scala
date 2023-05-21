package org.learning
package advanced.Section6TypesMastring

import advanced.Section6TypesMastring.TypeMembers.Animal

import org.learning.advanced.Section6TypesMastring.FBoundedPolymorphism.TypeClassMale

/**
 * The Goal here is to define a constraint to the compiler that whoever extends this trait must return the same type when overriding eat method
 * 1. recursive type is when the class/trait itself appear in the class/trait signature
 */
object FBoundedPolymorphism extends App {
  // basic example we want to enforce the return type to be the same as the class itself by the compiler
//  trait Animal {
//    def eat: List[Animal]
//  }
//  class Cat extends Animal {
//    override def eat: List[Cat] = List(new Cat)
//  }
//  class Dog extends Animal {
//    override def eat: List[Dog] = List(new Dog)
//  }

  // trying to add a type param to the trait to enforce the return type of eat to return the same type declared in the class signature
  // but then you can then add another type doesn't belong to AnotherPerson trait all together like int
  trait AnotherPerson[A] {
    def eat: List[A]
  }

  class AnotherMale extends AnotherPerson[AnotherMale] {
    override def eat: List[AnotherMale] = List(new AnotherMale)
  }

  class AnotherFemale extends AnotherPerson[Int] {
    override def eat: List[Int] = List(1)
  }

  // Another step is to bound the type param A so A must be subtype of Person (some type extending person)
  // the F-Bounded solution is good but as long as the class signature "extends Person[Type] is right
  trait FBoundedPerson[A <: FBoundedPerson[A]] {
    def eat: List[FBoundedPerson[A]]
  }
  class FBoundedMale extends FBoundedPerson[FBoundedMale] {
    override def eat: List[FBoundedPerson[FBoundedMale]] = List(new FBoundedMale)
  }
  class FBoundedFemale extends FBoundedPerson[FBoundedFemale] {
    override def eat: List[FBoundedPerson[FBoundedFemale]] = List(new FBoundedFemale)
  }
  //  the f-bounded polymorphism allow this wrong signature
  //  class Female extends Person[Male] {
  //    override def eat: List[Person[Male]] = List(new Male)
  //  }


  // by adding a self type constraint with the same type A,
  // there is no class will satisfy the two constraint except the same type that we are currently defining
  // because whoever extends person need to implement the self constraint in this case the same type "Person" at the same time
  trait Person[A <: Person[A]] { self: A =>
    def eat: List[A]
  }
  class Male extends Person[Male] {
    override def eat: List[Male] = List(new Male)
  }
  class Female extends Person[Female] {
    override def eat: List[Female] = List(new Female)
  }
//  COMPILATION ERROR
//  class Female extends Person[Male]


//  the tutorial continues with this example But i find it overkill
//  because the problem here is that it went another level and it's so obvious that if you are extending fish it should return a list of fish
//  but a type class solution is introduced to solve this problem (BUT I FIND THE ABOVE SOLUTION SUFFICIENT ENOUGH)

  //  trait Fish extends Animal[Fish]
  //  class Shark extends Fish {
  //    override def breed: List[Animal[Fish]] = List(new Cod)
  //  }
  //
  //  class Cod extends Fish {
  //    override def breed: List[Animal[Fish]] = ???
  //  }


  // type class solution
  trait TypeClassPerson[A] {
    def eat: List[A]
  }

  class TypeClassMale
  implicit object ImplicitTypeClassMale extends TypeClassPerson[TypeClassMale] {
    override def eat: List[TypeClassMale] = List(new TypeClassMale)
  }
  class TypeClassFemale
  implicit object ImplicitTypeClassFemale extends TypeClassPerson[TypeClassFemale] {
    override def eat: List[TypeClassFemale] = List(new TypeClassFemale)
  }

  implicit class TypeClassPersonEnrichment[T](value: T) {
    def eat(implicit typeClassPerson: TypeClassPerson[T]): List[T] = typeClassPerson.eat
  }

  val male = new TypeClassMale
  println(male.eat)

  val female = new TypeClassFemale
  println(female.eat)



  // tutorial implementation
  trait Animal[A] {
    def breed(a: A): List[A]
  }
  class Dog
  object Dog {
    implicit object DogAnimal extends Animal[Dog] {
      override def breed(a: Dog): List[Dog] = List()
    }
  }
  class Cat
  object Cat {
    implicit object CatAnimal extends Animal[Dog] {
      override def breed(a: Dog): List[Dog] = List()
    }
  }
  implicit class AnimalOps[A](animal: A) {
    def breed(implicit animalTypeClassInstance: Animal[A]): List[A] =
      animalTypeClassInstance.breed(animal)
  }

  val dog = new Dog
  dog.breed
}
