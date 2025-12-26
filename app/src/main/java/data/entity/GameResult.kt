package com.example.cardgame500.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_results")
data class GameResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val score: Int,
    val date: Long
)
