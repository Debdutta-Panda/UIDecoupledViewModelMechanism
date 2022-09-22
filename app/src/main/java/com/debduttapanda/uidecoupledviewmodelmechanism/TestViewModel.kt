package com.debduttapanda.uidecoupledviewmodelmechanism

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class TestIds{
    INPUT_TEXT,
    ADD_TASK,
    TASKS,
    DELETE_ITEM,
}

data class Task(
    val name: String,
    val uid: Long
)

class TestViewModel: ViewModel() {
    private var index = 0L
    private val inputText = mutableStateOf("")
    private val tasks = mutableStateListOf<Task>()
    val resolver = Resolver()
    val notificationService = NotificationService{id,arg->
        when(id){
            TestIds.INPUT_TEXT->{
                inputText.value = (arg as? String)?:""
            }
            TestIds.ADD_TASK->{
                if(inputText.value.isNotEmpty()){
                    tasks.add(Task(
                        name = inputText.value,
                        uid = ++index
                    ))
                    inputText.value = ""
                }
            }
            TestIds.DELETE_ITEM->{
                tasks.remove((arg as? Task)?:return@NotificationService)
            }
        }
    }
    init {
        resolver.set(TestIds.INPUT_TEXT,inputText)
        resolver.set(TestIds.TASKS,tasks)
    }
}