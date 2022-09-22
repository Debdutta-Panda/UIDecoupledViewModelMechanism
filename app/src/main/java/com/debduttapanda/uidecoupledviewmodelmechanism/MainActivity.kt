package com.debduttapanda.uidecoupledviewmodelmechanism

import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "page_a") {
                        composable("page_a") {
                            PageA(navController)
                        }
                        composable("page_b") {
                            PageB(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PageA(navHostController: NavHostController) {
    val s: TestViewModelA = viewModel()
    CompositionLocalProvider(
        LocalResolver provides s.resolver,
        LocalNotificationService provides s.notificationService
    ) {
        MainUI(navHostController)
    }
}

@Composable
fun PageB(navHostController: NavHostController) {
    val s: TestViewModelB = viewModel()
    CompositionLocalProvider(
        LocalResolver provides s.resolver,
        LocalNotificationService provides s.notificationService
    ) {
        MainUI(navHostController)
    }
}

@Composable
fun MainUI(
    navHostController: NavHostController,
    navigateTo: String = stringState(TestIds.NAVIGATE_TO).value,
    inputText: String = stringState(TestIds.INPUT_TEXT).value,
    pageColor: Color = colorState(TestIds.PAGE_COLOR).value,
    tasks: List<Task> = listState(TestIds.TASKS),
    notifier: NotificationService = notifier()
) {
    LaunchedEffect(key1 = navigateTo){
        if(navigateTo.isEmpty()){
            return@LaunchedEffect
        }
        if(navigateTo=="back"){
            navHostController.popBackStack()
        }
        else{
            navHostController.navigate(navigateTo)
        }
        notifier.notify(TestIds.NAVIGATED,null)
    }
    Column(
        modifier = Modifier
            .background(pageColor)
            .fillMaxSize()
            .padding(24.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Button(
                onClick = {
                    notifier.notify(TestIds.NAVIGATE,null)
                },
                modifier = Modifier
                    .fillMaxWidth().weight(1f)
            ) {
                Text("Navigate")
            }
            Button(
                onClick = {
                    notifier.notify(TestIds.NAVIGATE_BACK,null)
                },
                modifier = Modifier
                    .fillMaxWidth().weight(1f)
            ) {
                Text("Back")
            }
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = inputText,
            onValueChange = {
                notifier.notify(TestIds.INPUT_TEXT,it)
            },
            placeholder = {
                Text("New Task")
            }
        )
        Button(
            onClick = {
                notifier.notify(TestIds.ADD_TASK,null)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Add task")
        }
        if(tasks.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text("No tasks yet")
            }
        }
        else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    bottom = 24.dp
                )
            ){
                items(
                    tasks,
                    key = {it.uid}
                ){
                    Row(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(it.name)
                        IconButton(onClick = {
                            notifier.notify(TestIds.DELETE_ITEM,it)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                    }
                }
            }
        }
    }
}