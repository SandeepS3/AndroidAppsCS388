package com.example.myapplication.data.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FoodViewModel(private val dao: FoodDao): ViewModel() {
    private val _foods = dao.getFoods().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(FoodState())

    val state = combine(_state, _foods) {state,foods ->
        state.copy(foods=foods)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FoodState())

    fun onEvent(event: FoodEvent){
        when(event){
            is FoodEvent.DeleteAllFoods -> {
                viewModelScope.launch {
                    dao.deleteAllFoods()
                }
            }
            is FoodEvent.SaveFood -> {
                viewModelScope.launch {
                    dao.addFood(event.food)
                }
            }
            else -> {}
        }
    }
}
