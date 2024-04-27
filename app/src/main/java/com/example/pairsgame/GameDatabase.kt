package com.example.pairsgame

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pairsgame.DAO.CardsDao
import com.example.pairsgame.DAO.LeaguesDao
import com.example.pairsgame.DAO.SportsDao
import com.example.pairsgame.Models.Card
import com.example.pairsgame.Models.League
import com.example.pairsgame.Models.Sport

@Database(entities = [Sport::class, League::class, Card::class], version = 2, exportSchema = false)
abstract class GameDatabase() : RoomDatabase() {
    abstract fun sportsDao(): SportsDao
    abstract fun leaguesDao(): LeaguesDao
    abstract fun cardsDao(): CardsDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}