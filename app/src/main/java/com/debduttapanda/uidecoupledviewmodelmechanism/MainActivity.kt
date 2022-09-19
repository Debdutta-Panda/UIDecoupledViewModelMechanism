package com.debduttapanda.uidecoupledviewmodelmechanism

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.debduttapanda.uidecoupledviewmodelmechanism.ui.theme.UIDecoupledViewModelMechanismTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UIDecoupledViewModelMechanismTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val s: TestViewModel = viewModel()
                    CompositionLocalProvider(
                        LocalResolver provides s.resolver,
                        LocalNotificationService provides s.notificationService
                    ) {
                        MainUI()
                    }
                }
            }
        }
    }

    @Composable
    fun MainUI() {
        TestUI(r.get<State<TestData>>(0).value)
    }
}

data class TestData(
    val text: String
)

data class TestData1(
    val text: String
)

@Composable
fun TestUI(
    data: TestData
){
    Column {
        val notifier = r.notifier()
        Text(
            data.text,
            modifier = Modifier
                .background(r.get<State<Color>>(key = 2).value)
                .padding(50.dp)
        )
        TestUI1(r.get<State<TestData1>>(1).value)
        Button(onClick = {
            notifier("click_me",null)
        }) {
            Text("Click Me")
        }
    }
}

@Composable
fun TestUI1(
    data: TestData1
){
    Text(
        data.text,
            modifier = Modifier
                .background(r.get<State<Color>>(key = 3).value)
                .padding(50.dp)
    )
}