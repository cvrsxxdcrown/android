package com.example.cardgame500.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cardgame500.data.dao.GameResultDao
import com.example.cardgame500.data.entity.GameResult

@Database(
    entities = [GameResult::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gameResultDao(): GameResultDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cardgame500_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
