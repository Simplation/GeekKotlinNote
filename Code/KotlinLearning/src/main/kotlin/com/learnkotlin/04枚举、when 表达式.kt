package com.learnkotlin


enum class Language(private val hello: String) {
    ENGLISH("Hello"),
    CHINESE("你好"),
    KOREAN("안녕하세요."),
    JAPANESE("こんにちは");

    // 构造方法的方法体
    init {
        println(hello)
    }

    fun sayHello() {
        // 可以访问属性, 但不能访问变量
        println(hello)
    }

    companion object {
        fun parse(name: String): Language {
            return valueOf(name.toUpperCase())
        }
    }

}


fun main(args: Array<String>) {
    if (args.isEmpty()) return

    val language = Language.parse(args[0])

    println(language)

    // 调用枚举中的类方法
    language.sayHello()

    // 拓展枚举中的类方法(拓展方法)
    language.sayBye()
}


// 拓展方法： 类名.方法名
fun Language.sayBye() {

    // when 表达式是有返回值的，switch 是没有返回值的
    val bye = when(this) {
        Language.ENGLISH -> "bye"
        Language.CHINESE -> "再见"
        Language.KOREAN -> "안녕히"
        Language.JAPANESE -> "さようなら"
    }

    println(bye)
}
