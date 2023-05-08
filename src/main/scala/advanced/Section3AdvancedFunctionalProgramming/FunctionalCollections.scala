package org.learning
package advanced.Section3AdvancedFunctionalProgramming


/**
  Functional collections are collections that follow the principles of functional programming,such as immutability and pure functions.
  In Scala, the standard library provides several functional collections that are designed to be used in a functional programming style.

  Some examples of functional collections in Scala include:

  1. List: A linked list that is immutable and provides functional methods for creating new lists by transforming the original list.

  2. Vector: An indexed sequence that provides efficient random access and is also immutable.

  3. Set: An unordered collection that contains no duplicate elements and is immutable.

  4. Map: A collection of key-value pairs where each key is unique and maps to a corresponding value.
          Maps are immutable and provide functional methods for creating new maps by transforming the original map.

  Functional collections in Scala are designed to be used with higher-order functions such as map, filter, and reduce.
  These functions take a function as an argument,
  and the functional collections provide methods that allow you to transform, filter, and aggregate the elements of the collection
  using these functions.

  Functional collections are typically immutable, which means that they cannot be modified in-place.
  Instead, operations on a functional collection create a new collection that shares some structure with the original collection.
  This makes functional collections well-suited for use in concurrent and distributed systems,
  where immutable data structures can be shared safely between multiple threads or processes.

  In summary, functional collections are collections that follow the principles of functional programming
  and are designed to be used with higher-order functions.
  They are typically immutable and provide functional methods for transforming, filtering, and aggregating
  the elements of the collection.

 */
object FunctionalCollections {}