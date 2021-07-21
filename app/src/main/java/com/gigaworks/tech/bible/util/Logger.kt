package com.gigaworks.tech.bible.util

import android.util.Log
import com.gigaworks.tech.bible.BuildConfig.DEBUG

// Tag for logs
const val TAG = "PilgrimsManna"

fun printLogD(className: String, message: String?) {
    if (DEBUG) {
        Log.d("$TAG:DEBUG", "$className: $message")
    }
}

fun printLogE(className: String, message: String?) {
    if (DEBUG) {
        Log.d("$TAG:DEBUG", "$className: $message")
    }
}