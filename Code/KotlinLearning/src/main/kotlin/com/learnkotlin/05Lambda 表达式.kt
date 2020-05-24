package com.learnkotlin

import io.reactivex.Observable
import java.io.File
import java.util.concurrent.Executors

fun main(args: Array<String>) {
    // 读取 resource 文件夹中的 input 文件中的内容
    val text = File(ClassLoader.getSystemResource("input").path).readText()

    Observable.fromIterable(text.toCharArray().asIterable())
        .filter { !it.isWhitespace() }
        .groupBy { it }
        .map { o ->
            o.count().doOnSubscribe {
                println("${o.key} -> $it")
            }
        }
        .subscribe()

    val worker = Executors.newCachedThreadPool()
    worker.execute {
        println("hello")
    }
}
