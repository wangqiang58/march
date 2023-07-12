package com.xdf.plugin.utils

import java.util.concurrent.Executors

object Log {

    private val logThreadExecutor = Executors.newSingleThreadExecutor()

    fun log(log: Any?) {
        logThreadExecutor.submit {
            println("ASM: $log")
        }
    }
}