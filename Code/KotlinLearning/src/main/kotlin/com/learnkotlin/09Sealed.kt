package com.learnkotlin

import kotlin.properties.Delegates

fun main() {
    val player = Player()

    player.play("http://ws.stream.qqmusic.com/C2000012Ppbd3hjGOK.m4a")
    player.pause()
    player.resume()
    player.seekTo(30000)
    player.stop()
}

class Player {
    private var state: State by Delegates.observable(State.IDLE, { property, oldValue, newValue ->
        println("$oldValue -> $newValue")

        onPlayerStateChangeListener?.onStateChanged(oldValue, newValue)
    })

    private fun sendCmd(cmd: PlayCmd) {
        when (cmd) {
            is PlayCmd.Play -> {
                println("\nPlay ${cmd.url} from ${cmd.position}ms")
                state = State.PLAYING
                doPlay(cmd.url, cmd.position)
            }

            is PlayCmd.Resume -> {
                println("\nResume.")
                state = State.PLAYING
                doResume()
            }

            is PlayCmd.Pause -> {
                println("\nPause.")
                state = State.PAUSE
                doPause()
            }

            is PlayCmd.Stop -> {
                println("\nStop.")
                state = State.IDLE
                doStop()
            }

            is PlayCmd.Seek -> {
                println("\n Seek to ${cmd.position} ms state: $state")
            }

        }
    }

    private fun doPlay(url: String, position: Long) {
        // todo do sth
    }

    private fun doResume() {
        // todo do sth
    }

    private fun doPause() {
        // todo do sth
    }

    private fun doStop() {
        // todo do sth
    }

    /*------------------------------- region api ------------------------------------*/

    interface OnPlayerChangeListener {
        fun onStateChanged(oldState: State, newState: State)
    }

    private var onPlayerStateChangeListener: OnPlayerChangeListener? = null

    fun play(url: String, position: Long = 0) {
        sendCmd(PlayCmd.Play(url, position))
    }

    fun resume() {
        sendCmd(PlayCmd.Resume)
    }

    fun pause() {
        sendCmd(PlayCmd.Pause)
    }

    fun stop() {
        sendCmd(PlayCmd.Stop)
    }

    fun seekTo(position: Long) {
        sendCmd(PlayCmd.Seek(position))
    }


    /*------------------------------- region api ------------------------------------*/
}




/**
 * sealed class   可以存在多个类型的枚举(单个使用 object, 多个使用 class)
 *  自己的构造方法是私有的
 */
sealed class PlayCmd {
    class Play(val url: String, val position: Long = 0) : PlayCmd()
    class Seek(val position: Long) : PlayCmd()

    object Pause : PlayCmd()
    object Resume : PlayCmd()
    object Stop : PlayCmd()
}



/**
 * 枚举类, 播放的状态
 */
enum class State {
    IDLE, PAUSE, PLAYING
}
