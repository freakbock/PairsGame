package com.example.pairsgame.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pairsgame.Models.Sport

@Dao
interface SportsDao {

    @Insert
    fun insertSport(sport: Sport)

    @Delete
    fun deleteSport(sport:Sport)

    @Query("SELECT * FROM sports")
    fun getAllSports(): List<Sport>

    @Query("SELECT * FROM sports WHERE sport_id = :sportId")
    fun getSportById(sportId: Long): Sport?

}