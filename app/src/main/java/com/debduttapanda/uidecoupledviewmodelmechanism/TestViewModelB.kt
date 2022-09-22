package com.debduttapanda.uidecoupledviewmodelmechanism

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class TestViewModelB: ViewModel() {
    private var navigateTo = mutableStateOf("")
    private var index = 0L
    private val inputText = mutableStateOf("")
    private val pageColor = mutableStateOf(Color.LightGray)
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
                navigateTo.value = "page_a"
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
    }
}