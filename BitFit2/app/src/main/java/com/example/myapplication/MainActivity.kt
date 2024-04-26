package com.example.myapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.data.food.FoodDatabase
import androidx.room.Room
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel

import com.example.myapplication.data.food.FoodViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.food.Food
import com.example.myapplication.data.food.FoodEvent
import com.example.myapplication.data.food.FoodState
import kotlinx.coroutines.flow.StateFlow

class MainActivity : ComponentActivity() {
    private val foodDB by lazy { Room.databaseBuilder( applicationContext, FoodDatabase::class.java, "foods.db" ).build() }
    private val foodViewModel by viewModels<FoodViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FoodViewModel(foodDB.foodDao) as T
                }
            }
        }
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val foodsState by foodViewModel.state.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent(
                        state = foodsState,
                        onEvent = foodViewModel::onEvent,
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppContent(
    state: FoodState,
    onEvent: (FoodEvent) -> Unit
) {
    var currPageInd by remember { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = { BottomNavigationBar(currPageInd) { index -> currPageInd = index } }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currPageInd) {
                0 -> LogPage(state,onEvent)
                1 -> DashboardPage(state,onEvent)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    val items = listOf("Log","Dashboard")
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (index == 0) Icons.Filled.List
                        else Icons.Filled.AccountBox,
                        contentDescription = item
                    )
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    onItemSelected(index)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}