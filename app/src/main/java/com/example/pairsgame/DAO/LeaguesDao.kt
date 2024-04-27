package com.example.pairsgame.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pairsgame.Models.League

@Dao
interface LeaguesDao {
    @Insert
    fun insertLeague(league: League)

    @Delete
    fun deleteLeague(league: League)

    @Query("SELECT * FROM leagues")
    fun getAllLeagues(): List<League>

    @Query("SELECT * FROM leagues WHERE league_id = :leagueId")
    fun getLeagueById(leagueId: Long): League?
}