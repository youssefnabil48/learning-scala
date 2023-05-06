package org.learning
package LeetCode

object MedianOfTwoSortedArrays extends App {

  private def findMedianSortedArrays(nums1: Array[Int], nums2: Array[Int]): Double = {
    val arr = nums1.concat(nums2).sorted
    if (arr.length % 2 == 0) {
      (arr(arr.length / 2 - 1) + arr(arr.length / 2)).asInstanceOf[Double] / 2
    } else
      arr(arr.length / 2)
  }
  println(findMedianSortedArrays(Array(1, 3), Array(2)))
  println(findMedianSortedArrays(Array(1, 2), Array(3, 4)))
}