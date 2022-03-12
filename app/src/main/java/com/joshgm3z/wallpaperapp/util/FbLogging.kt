package com.joshgm3z.wallpaperapp.util

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FbLogging(context: Context) {

    private val mFirebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    companion object {
        const val SET_WALLPAPER = "set_wallpaper"
        const val DOWNLOAD_WALLPAPER = "download_wallpaper"
        const val UPLOAD_WALLPAPER = "upload_wallpaper"
        const val ERROR = "error"
        const val CLICK_ACTION = "click_action"
        const val BUTTON_CLICK = "button_click"
    }

    public fun logEntry() {
        val element = Thread.currentThread().stackTrace[3]
        var className = element.className
        className = className.substring(className.lastIndexOf(".") + 1, className.length)
        val methodName = element.methodName
//        val message = "$className $methodName >>> Entry"
        val name = "$className"
        val params = Bundle().apply {
            putString("method", methodName)
            putString("action", "Entry")
        }
        mFirebaseAnalytics.logEvent(name, params)
    }

    fun logExit() {
        val element = Thread.currentThread().stackTrace[3]
        var className = element.className
        className = className.substring(className.lastIndexOf(".") + 1, className.length)
        val methodName = element.methodName
        val name = "$className"
        val params = Bundle().apply {
            putString("method", methodName)
            putString("action", "Exit")
        }
        mFirebaseAnalytics.logEvent(name, params)

    }

    fun exceptionLog(message: String) {
        val element = Thread.currentThread().stackTrace[3]
        var className = element.className
        className = className.substring(className.lastIndexOf(".") + 1, className.length)
        val methodName = element.methodName
        val logMessage = "$className $methodName: $message"
        mFirebaseAnalytics.logEvent(message, null)
    }

    fun logInfo(message: String) {
        val element = Thread.currentThread().stackTrace[3]
        var className = element.className
        className = className.substring(className.lastIndexOf(".") + 1, className.length)
        val methodName = element.methodName
        val name = "$className"
        val params = Bundle().apply {
            putString("method", methodName)
            putString("info", message)
        }
        mFirebaseAnalytics.logEvent(name, params)
    }

    fun logEvent(eventName: String) {
        val element = Thread.currentThread().stackTrace[3]
        var className = element.className
        className = className.substring(className.lastIndexOf(".") + 1, className.length)
        val methodName = element.methodName
        val name = "$className"
        val params = Bundle().apply {
            putString("class_name", className)
            putString("method_name", methodName)
        }
        mFirebaseAnalytics.logEvent(eventName, params)
    }
}