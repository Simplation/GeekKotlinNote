package com.learnkotlin

import com.google.gson.Gson
import java.io.File


fun main(args: Array<String>) {
    val json = File("src/main/resources/singer.json").readText()
    val result = Gson().fromJson(json, Singer::class.java)

    // println(json)

    println(result.getCode())
    println(result.getMessage())
    println(result.getContent()?.id)
    println(result.getContent()?.name)

    val lists = result.getContent()?.songs
    if (lists != null) {
        for (i in lists) {
            println("${i.id} -- ${i.name}")
        }
    }
}


class Singer {
    private var code = 0
    private var message: String? = null
    private var content: Content? = null
    fun setCode(code: Int) {
        this.code = code
    }

    fun getCode(): Int {
        return code
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getMessage(): String? {
        return message
    }

    fun setContent(content: Content?) {
        this.content = content
    }

    fun getContent(): Content? {
        return content
    }

    class Songs {
        var id = 0
        var name: String? = null

    }

    class Content {
        var id = 0
        var name: String? = null
        var songs: List<Songs>? = null
    }
}
