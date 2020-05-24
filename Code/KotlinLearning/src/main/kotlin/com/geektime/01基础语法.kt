package com.geektime

fun main() {

    // val name1: String = "Simplation"
    // val age1: Int = 18

    // 自动推断类型
    val name2 = "Simplation"
    val age2 = 18

    val formatStr = String.format("我是 $name2, 今年 $age2 岁")
    println("format later is $formatStr")

    var name3: String = "Simplation"
    var name4: String? = "Simplation"

    /**
     * 区分 ?、!!
     *      ?: 前边参数为空, 则不执行该方法; 反之
     *      !!:断言, 确认前边参数一定不为空
     */
    // name3 = name4
    name3 = name4!!
    name4 = name3


    val name5 = "Simplation"
    println("name5 length is ${name5.length}")

    var name: String? = null
    // println(name?.length)

    // 没有返回值的方法
    fun printLength(name: String?) {
        println(name?.length)
    }

    // 带返回值的参数
    fun printLengths(name: String?): String? {
        println(name?.length)
        return name
    }
    printLength(null)
    printLengths(null)
}