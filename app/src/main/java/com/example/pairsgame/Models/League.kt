package com.example.pairsgame.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "leagues",
    foreignKeys = [ForeignKey(entity = Sport::class,
        parentColumns = ["sport_id"],
        childColumns = ["sport_id"],
        onDelete = ForeignKey.CASCADE)])
data class League(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "league_id") val leagueId: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "sport_id") val sportId: Int
)
