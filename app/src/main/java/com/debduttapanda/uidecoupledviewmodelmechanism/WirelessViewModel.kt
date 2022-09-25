package com.debduttapanda.uidecoupledviewmodelmechanism

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color

class Resolver{
    private val map: MutableMap<Any,Any?> = mutableMapOf()
    fun <T>get(key: Any): T{
        return map[key] as T
    }

    fun set(key: Any, value: Any?){
        map[key] = value
    }
}

data class NotificationService(
    val notify: (Any,Any?)->Unit
)
val LocalResolver = compositionLocalOf { Resolver() }

val LocalNotificationService = compositionLocalOf { NotificationService{ _, _->} }

val LocalSuffix = compositionLocalOf { "" }
@Composable
fun suffix(): String{
    return LocalSuffix.current
}

@Composable
fun suffix(
    suffix: String,
    content: @Composable () -> Unit
){
    CompositionLocalProvider(
        values = arrayOf(LocalSuffix provides "${LocalSuffix.current}$suffix"),
        content
    )
}


@Composable
fun stringState(key: Any): State<String> {
    return LocalResolver.current.get(key)
}
@Composable
fun colorState(key: Any): State<Color> {
    return LocalResolver.current.get(key)
}
@Composable
fun boolState(key: Any): State<Boolean> {
    return LocalResolver.current.get(key)
}
@Composable
fun <T>listState(key: Any): SnapshotStateList<T> {
    return LocalResolver.current.get(key)
}
@Composable
fun <T>tState(key: Any): State<T> {
    return LocalResolver.current.get(key)
}

@Composable
fun notifier():NotificationService{
    return LocalNotificationService.current
}