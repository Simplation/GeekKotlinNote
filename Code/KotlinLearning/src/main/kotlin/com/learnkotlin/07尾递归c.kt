package com.learnkotlin

import java.math.BigInteger

/**
 * 递归：在函数内部调用自己本身，被称为递归。
 * 尾递归：在函数内部调用自己本身，并且调用是在最后一句
 *
 * 尾递归优化：将递归变成迭代的方式进行优化，则不会出现 StackOverFlow 异常
 */

class Result(var value: BigInteger = BigInteger.valueOf(1L))


tailrec fun factorial(num: Int, result: Result) {
    if (num == 0) result.value = result.value.times(BigInteger.valueOf(1L))
    else {
        result.value = result.value.times(BigInteger.valueOf(num.toLong()))
        factorial(num - 1, result)
    }
}


fun main() {
    val result = Result()

    factorial(10000, result)
    print(result.value)
}

