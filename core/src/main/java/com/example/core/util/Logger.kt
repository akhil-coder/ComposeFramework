package com.example.core.util

import com.example.core.printLogD

class Logger(
    private val tag: String,
    private val isDebug: Boolean
) {
    fun log(msg: String) {
        if (!isDebug) {

        } else {
            printLogD(tag, msg)
        }

    }

    companion object Factory {
        fun buildDebug(tag: String): Logger {
            return Logger(
                tag = tag,
                isDebug = true
            )
        }

        fun buildRelease(tag: String): Logger {
            return Logger(
                tag = tag,
                isDebug = false
            )
        }
    }
}