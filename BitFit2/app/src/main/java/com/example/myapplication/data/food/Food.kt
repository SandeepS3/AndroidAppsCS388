package com.example.myapplication.data.food

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Food(
    val foodName: String,
    val calories: Int,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
