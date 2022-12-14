package com.debduttapanda.uidecoupledviewmodelmechanism

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class Task(
    val name: String,
    val uid: Long
)

class TestViewModelA: ViewModel() {
    val list = mutableStateListOf<Int>()
    private var navigateTo = mutableStateOf("")
    private var index = 0L
    private val pageColor = mutableStateOf(Color.White)
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
            TestIds.NAVIGATE->{
                navigateTo.value = "page_b"
            }
            TestIds.NAVIGATE_BACK->{
                navigateTo.value = "back"
            }
            TestIds.NAVIGATED->{
                navigateTo.value = ""
            }
        }
    }
    init {
        resolver.set(TestIds.INPUT_TEXT,inputText)
        resolver.set(TestIds.TASKS,tasks)
        resolver.set(TestIds.PAGE_COLOR,pageColor)
        resolver.set(TestIds.NAVIGATE_TO,navigateTo)
        list.addAll(listOf(1,2,3,4,5))
        viewModelScope.launch {
            while (true){
                delay(2000)
                list[1] = (System.currentTimeMillis()%1000).toInt()
            }
        }
    }
}