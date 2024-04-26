package com.example.myapplication

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.food.Food
import com.example.myapplication.data.food.FoodEvent
import kotlinx.coroutines.flow.StateFlow
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.data.food.FoodState

@Composable
fun LogPage(
    state: FoodState,
    onEvent: (FoodEvent) -> Unit,
) {
    var showAddFood by remember { mutableStateOf(false) }
    if (showAddFood) {
        AddFoodPage(onEvent, goBack = { showAddFood = false })
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Food's Log",
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.foods) { food ->
                FoodBar(food, onEvent)
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Gray,
                    thickness = 0.2.dp
                )
            }
        }

        Button(
            onClick = {
                showAddFood = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add Food")
        }
    }
}


@Composable
fun AddFoodPage(onEvent: (FoodEvent) -> Unit, goBack: () -> Unit) {
    val context = LocalContext.current
    val foodName = remember { mutableStateOf("") }
    val calories = remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        FormTextInput(
            heading = "Food",
            placeholder = "Enter Food Here",
            taskName = foodName.value
        ) {
            foodName.value = it
        }
        FormTextInputNumber(
            heading = "Calories",
            placeholder = "Enter Calories Here",
            taskName = calories.value.toString()
        ) {
            calories.intValue = it.toInt()
        }

        SubmitButton(title = "Submit Food") {
            if (foodName.value.isEmpty() || calories.intValue <= 0) {
                Toast.makeText(context, "Invalid Data", Toast.LENGTH_LONG).show()
                return@SubmitButton
            }
            val food = Food(foodName = foodName.value, calories = calories.intValue)
            onEvent(FoodEvent.SaveFood(food))
            Toast.makeText(context, "Added Food To Database", Toast.LENGTH_LONG).show()
            goBack()
        }

    }
}

@Composable
fun FoodBar(food: Food, onEvent: (FoodEvent) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Food Name: ${food.foodName}",
            fontSize = 16.sp,
            modifier = Modifier
                .padding(bottom = 2.dp)
                .background(Color(0xFFE0E0E0))
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Calories: ${food.calories}",
            fontSize = 16.sp,
            modifier = Modifier
                .padding(bottom = 2.dp)
                .background(Color(0xFFE0E0E0))
                .padding(8.dp)
        )
    }
}
