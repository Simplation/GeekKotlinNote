package com.learnkotlin

fun main(args: Array<String>) {
    /*args.map {
        // it 代表迭代器的意思
        println(it)
    }*/

    // 效果等价于上边的代码, 下边代码更加简单
    args.map(::println)

    // 等价于 java 代码中的 for 循环
    /*
    for (arg in args) {
        println(arg)
    }*/

}