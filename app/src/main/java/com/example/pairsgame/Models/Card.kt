package com.example.pairsgame.Models
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "cards",
    foreignKeys = [ForeignKey(entity = League::class,
        parentColumns = ["league_id"],
        childColumns = ["league_id"],
        onDelete = ForeignKey.CASCADE)])
data class Card(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "card_id") val cardId: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: Int,
    @ColumnInfo(name = "league_id") val leagueId: Int
)
