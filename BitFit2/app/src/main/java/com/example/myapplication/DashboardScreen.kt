package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.myapplication.data.food.FoodEvent
import com.example.myapplication.data.food.FoodState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardPage(
    state: FoodState,
    onEvent: (FoodEvent) -> Unit,
) {
    val avgCals = (state.foods.map { it.calories }).average()
    val maxCals = state.foods.maxOfOrNull { it.calories } ?: 0
    val minCals = state.foods.minOfOrNull { it.calories } ?: 0
    Column {

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FormTextInput(taskName = "$avgCals",heading = "Average Calories", placeholder = "") {}
        FormTextInput(taskName = "$maxCals",heading = "Maximum Calories", placeholder = "") {}
        FormTextInput(taskName = "$minCals",heading = "Minimum Calories", placeholder = "") {}

        Button(
            onClick = { onEvent(FoodEvent.DeleteAllFoods) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Delete All Foods")
        }
    }
}
