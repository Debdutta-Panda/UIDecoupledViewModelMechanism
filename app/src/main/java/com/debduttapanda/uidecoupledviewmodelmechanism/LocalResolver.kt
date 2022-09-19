package com.debduttapanda.uidecoupledviewmodelmechanism

import android.util.Log
import androidx.compose.runtime.*



class Resolver{
    private val map: MutableMap<Int,Any?> = mutableMapOf()
    fun <T>get(key: Int): T{
        return map[key] as T
    }

    fun set(key: Int, value: Any?){
        map[key] = value
    }
}
data class NotificationService(
    val notify: (Any,Any?)->Unit
)

val LocalNotificationService = compositionLocalOf { NotificationService{ _, _->} }
val LocalResolver = compositionLocalOf { Resolver() }

object r {
    @Composable
    fun <T>get(key: Int): T{
        Log.d("fjldfjdkf","running")
        return LocalResolver.current.get(key)
    }

    @Composable
    fun notifier(): (Any,Any?)->Unit {
        return LocalNotificationService.current.notify
    }
}