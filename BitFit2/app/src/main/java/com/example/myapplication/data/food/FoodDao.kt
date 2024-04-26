package com.example.myapplication.data.food

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert
    suspend fun addFood(food: Food) // suspend waits to finish opperation and then moves on (sync)

    @Query("DELETE FROM Food")
    suspend fun deleteAllFoods()

    @Query("SELECT * from FOOD")
    fun getFoods(): Flow<List<Food>>
}
