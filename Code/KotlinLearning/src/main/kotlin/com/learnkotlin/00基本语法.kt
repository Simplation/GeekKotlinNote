package com.learnkotlin

fun main() {
    val str = "Hello Kotlin."
    // kotlin 使用 $ 来进行字符串的拼接
    println("str is $str")


    // 条件表达式
    /*
    fun maxOf(a: Int, b: Int): Int {
        if (a > b) {
            print(a)
        } else {
            print(b)
        }
    }
    */
    fun maxOf(a: Int, b: Int) = if (a > b) {
        println(a)
    } else {
        println(b)
    }

    // 调用已定义的 maxOf 方法
    maxOf(4, 8)


    // as? 避免安全转换问题

    // 类型检查: is
    fun getStringLength(obj: Any): Int? {
        return if (obj is String) {
            obj.length
        } else {
            null
        }
    }

    getStringLength("Simplation")


    // 遍历集合
    val items = listOf("apple", "banana", "pear", "watermelon", "lemon")
    // 支持过滤、转换等相关的操作
    items.filter { it.startsWith("a") }
            .sortedBy { it }
            .map { it.toUpperCase() }

    // for 循环
    for (item in items) {
        println("| $item")
    }


    // while 循环
    val fruits = listOf("apple", "banana", "pear", "watermelon", "lemon")
    var index = 0
    while (index < fruits.size) {
        println("item at $index is ${items[index]}")
        index++
    }


    // when 表达式：代替 switch 语句
    fun description(obj: Any): String =
            when (obj) {
                1 -> "is Int"
                "Hello" -> "is String"
                else -> "Unknown"
            }

    description("Hello")

}