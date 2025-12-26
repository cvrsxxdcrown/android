package com.example.cardgame500.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cardgame500.data.entity.GameResult

@Dao
interface GameResultDao {

    @Insert
    suspend fun insert(result: GameResult)

    @Query("SELECT * FROM game_results ORDER BY date DESC")
    suspend fun getAll(): List<GameResult>

    @Query("DELETE FROM game_results")
    suspend fun deleteAll()
}
