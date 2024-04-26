package com.example.myapplication.data.food

sealed interface FoodEvent {
    data class SaveFood(val food: Food): FoodEvent
    data class DeleteFood(val food: Food): FoodEvent
    data object DeleteAllFoods : FoodEvent
}
