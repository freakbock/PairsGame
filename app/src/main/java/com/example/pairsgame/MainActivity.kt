package com.example.pairsgame

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.pairsgame.Models.Card
import com.example.pairsgame.Models.League
import com.example.pairsgame.Models.Sport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var database: GameDatabase
    lateinit var sports_parent: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sports_parent = findViewById(R.id.sports_parent)
        database = GameDatabase.getDatabase(this)

        AddData()
    }

    fun AddData(){
        CoroutineScope(Dispatchers.IO).launch {

            val sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
            if(!sharedPreferences.contains("first")){
                database.sportsDao().insertSport(Sport(0, "Футбол"))
                database.sportsDao().insertSport(Sport(0, "Баскетбол"))
                database.sportsDao().insertSport(Sport(0, "Гольф"))

                database.leaguesDao().insertLeague(League(0, "Лига 1", 1))
                database.leaguesDao().insertLeague(League(0, "Лига 2", 1))
                database.leaguesDao().insertLeague(League(0, "Лига 3", 1))
                database.leaguesDao().insertLeague(League(0, "Лига 4", 1))
                database.leaguesDao().insertLeague(League(0, "Лига 5", 1))
                database.leaguesDao().insertLeague(League(0, "Лига 6", 1))
                database.leaguesDao().insertLeague(League(0, "Лига 7", 1))
                database.leaguesDao().insertLeague(League(0, "Лига 8", 1))

                database.cardsDao().insertCard(Card(0, "Карта 1", R.drawable.card_1, 1))
                database.cardsDao().insertCard(Card(0, "Карта 2", R.drawable.card_2, 1))
                database.cardsDao().insertCard(Card(0, "Карта 3", R.drawable.card_3, 1))
                database.cardsDao().insertCard(Card(0, "Карта 4", R.drawable.card_4, 1))
                database.cardsDao().insertCard(Card(0, "Карта 5", R.drawable.card_5, 1))
                database.cardsDao().insertCard(Card(0, "Карта 6", R.drawable.card_6, 1))
                database.cardsDao().insertCard(Card(0, "Карта 7", R.drawable.card_7, 1))
                database.cardsDao().insertCard(Card(0, "Карта 8", R.drawable.card_8, 1))
                database.cardsDao().insertCard(Card(0, "Карта 9", R.drawable.card_9, 1))
                database.cardsDao().insertCard(Card(0, "Карта 10", R.drawable.card_10, 1))
                database.cardsDao().insertCard(Card(0, "Карта 11", R.drawable.card_11, 1))
                database.cardsDao().insertCard(Card(0, "Карта 12", R.drawable.card_12, 1))

                val editor =sharedPreferences.edit()
                editor.putBoolean("first", true)
                editor.apply()
            }
            LoadSports()

        }
    }

    fun LoadSports(){
        CoroutineScope(Dispatchers.IO).launch {

            val sports = database.sportsDao().getAllSports()

            withContext(Dispatchers.Main){
                sports_parent.removeAllViews()
                for(sport in sports){
                    val button = Button(this@MainActivity)
                    button.setText(sport.name)
                    button.setTextSize(16f)
                    button.setTextColor(getColor(R.color.black))
                    button.setBackgroundColor(getColor(R.color.white))
                    button.setOnClickListener {
                        val intent = Intent(this@MainActivity, LeagueActivity::class.java)
                        intent.putExtra("sportId", sport.sportId)
                        startActivity(intent)
                    }
                    val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150)
                    layoutParams.setMargins(40,5,40,20)
                    button.layoutParams = layoutParams

                    sports_parent.addView(button)
                }
            }

        }
    }
}