package com.learnkotlin

/**
 * a_b_c_d_e_f_g
 */
fun main(vararg args: String) {
    args.flatMap {
        it.split("_")
    }.map {
        // $ 字符串模板
        print("$it ")
    }
}