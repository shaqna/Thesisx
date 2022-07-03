package com.ngedev.thesisx.utils

import android.util.Log

object Helper {
    fun generateRandomKey(): String {
        val number = (1000..9999).random()
        val key = number.toString()
        Log.d("MyKEY", number.toString())
        return key
    }
}