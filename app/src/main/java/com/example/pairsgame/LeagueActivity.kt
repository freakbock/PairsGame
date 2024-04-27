package com.example.pairsgame

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LeagueActivity: AppCompatActivity() {

    lateinit var leagues_parent: LinearLayout
    lateinit var database: GameDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = GameDatabase.getDatabase(this)
        val sportId = intent.getIntExtra("sportId", -1)
        if(sportId!=-1)
            LoadLeagues(sportId)
        setContentView(R.layout.league_choose)
        leagues_parent = findViewById(R.id.leagues_parent)
    }

    fun LoadLeagues(sportId: Int){

        CoroutineScope(Dispatchers.IO).launch {

            val leagues = database.leaguesDao().getAllLeagues().filter {it.sportId == sportId}

            withContext(Dispatchers.Main){
                leagues_parent.removeAllViews()
                for(league in leagues){
                    val button = Button(this@LeagueActivity)
                    button.setText(league.name)
                    button.setTextSize(16f)
                    button.setTextColor(getColor(R.color.black))
                    button.setBackgroundColor(getColor(R.color.white))
                    button.setOnClickListener {

                        println("Запускаем игру")
                        val intent = Intent(this@LeagueActivity, GameActivity::class.java)
                        intent.putExtra("league_id", league.leagueId)
                        startActivity(intent)
                        println("Запустили игру")
                    }
                    val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150)
                    layoutParams.setMargins(40,5,40,20)
                    button.layoutParams = layoutParams

                    leagues_parent.addView(button)
                }
            }

        }

    }

}