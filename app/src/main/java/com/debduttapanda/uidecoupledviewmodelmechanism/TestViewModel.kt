package com.debduttapanda.uidecoupledviewmodelmechanism

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestViewModel: ViewModel() {
    var white1 = true
    var white2 = false
    val notificationService = NotificationService{id,arg->
        if(id=="click_me"){
            white1 = !white1
            _color.value = if(white1) Color.White else Color.Red
            white2 = !white2
            _color2.value = if(white2) Color.White else Color.Red
        }
    }
    val resolver = Resolver()
    val i = mutableStateOf(0)
    val _testData = mutableStateOf(TestData(""))
    val _testData1 = mutableStateOf(TestData1(""))
    val _color = mutableStateOf(Color.White)
    val _color2 = mutableStateOf(Color.Red)
    init {
        resolver.set(0,_testData)
        resolver.set(1,_testData1)
        resolver.set(2,_color)
        resolver.set(3,_color2)
        viewModelScope.launch {
            while (true){
                delay(2000)
                ++i.value
                _testData.value = TestData(i.value.toString())
                _testData1.value = TestData1((i.value*2).toString())
            }
        }
    }
}