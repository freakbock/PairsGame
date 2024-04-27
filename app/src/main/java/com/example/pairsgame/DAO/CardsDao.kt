package com.example.pairsgame.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pairsgame.Models.Card

@Dao
interface CardsDao {

    @Insert
    fun insertCard(card: Card)

    @Delete
    fun deleteCard(card: Card)

    @Query("SELECT * FROM cards")
    fun getAllCards(): List<Card>

    @Query("SELECT * FROM cards WHERE card_id = :cardId")
    fun getCardById(cardId: Int): Card?
}