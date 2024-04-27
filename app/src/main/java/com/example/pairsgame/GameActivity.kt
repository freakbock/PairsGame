package com.example.pairsgame

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.pairsgame.Models.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.util.Locale

class GameActivity : AppCompatActivity(){

    lateinit var selectSizeLayout: ConstraintLayout
    lateinit var gameInfo: TextView
    lateinit var cards_parent: GridLayout
    lateinit var win_info: TextView
    lateinit var winLayout: ConstraintLayout

    lateinit var database: GameDatabase

    var game_complexity: Int = 0
    var league_id: Int = -1
    var time: Int = 0
    var pairs: Int = 0

    lateinit var cards: List<Card>
    private var running = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        database = GameDatabase.getDatabase(this)
        selectSizeLayout = findViewById(R.id.selectSizeLayout)
        gameInfo = findViewById(R.id.gameInfo)
        cards_parent = findViewById(R.id.cards_parent)
        win_info = findViewById(R.id.win_info)
        winLayout = findViewById(R.id.winLayout)

        val intent = getIntent()
        league_id = intent.getIntExtra("league_id", -1)
        println("league_id ==== $league_id")
        if(league_id != -1){
            CoroutineScope(Dispatchers.IO).launch {
                cards = database.cardsDao().getAllCards().filter { it.leagueId == league_id }
            }
            selectSizeLayout.visibility = View.VISIBLE
        }
        else{
            finish()
        }
    }

    var selectCard: Card? = null
    var pairsList: MutableList<Pair<Int, Int>> = mutableListOf()
    var selectCardIndex: Int = -1
    var isDelayInProgress = false

    fun CheckCard(cards: List<Card>, button: Button, card: Card) {

        if(!isDelayInProgress){
            val index = cards_parent.indexOfChild(button)
            //button.setText(card.name)
            button.setBackgroundResource(cards[index].image)
            if (selectCard != null) {
                if (selectCard!!.cardId == card.cardId && index != selectCardIndex) {
                    println("Карты совпали: ${selectCard?.name} и ${card.name}")
                    button.setOnClickListener(null)
                    selectCard = null
                    pairs++
                    CheckWin()
                } else {
                    isDelayInProgress = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        //button.setText("?")
                        button.setBackgroundResource(R.drawable.close_card)
                        isDelayInProgress = false
                    }, 1000)

                    for (i in 0 until cards_parent.childCount) {
                        val child = cards_parent.getChildAt(i) as Button
                        if (i == selectCardIndex) {
                            val localCard = cards.get(i)
                            isDelayInProgress = true
                            Handler(Looper.getMainLooper()).postDelayed({
                                //child.setText("?")
                                child.setBackgroundResource(R.drawable.close_card)
                                isDelayInProgress = false
                            }, 1000)
                            child.setOnClickListener {
                                CheckCard(cards, child, localCard)
                            }
                        }
                    }
                    selectCard = null
                }
            } else {
                selectCardIndex = index
                selectCard = card
            }
        }
    }


    fun StartGame(){
        pairs = 0
        time = 0
        selectCard = null
        cards_parent.removeAllViews()

        if(game_complexity == 0){
            val screenWidth = resources.displayMetrics.widthPixels
            val screenHeight = resources.displayMetrics.heightPixels- 400
            val buttonWidth = screenWidth / 2 - (2 * 5)
            val buttonHeight = screenHeight / 4 - (2 * 5)

            cards_parent.rowCount = 4
            cards_parent.columnCount = 2
            val played_cards = mutableListOf<Card>()
            val first_cards = cards.take(4)
            for(card in first_cards){
                played_cards.add(card)
                played_cards.add(card)
            }
            played_cards.shuffle()

            played_cards.forEach { card ->
                val button = Button(this)
                button.setBackgroundResource(R.drawable.close_card)
                //button.setText("?")
                button.id = View.generateViewId()
                val layoutParams = GridLayout.LayoutParams()
                layoutParams.width = buttonWidth
                layoutParams.height = buttonHeight
                layoutParams.setMargins(5, 5, 5, 5) // Отступы между кнопками
                button.layoutParams = layoutParams

                val localCard = card

                pairsList.add(Pair(button.id, localCard.cardId))
                button.setOnClickListener {
                    CheckCard(played_cards, button, localCard)
                }
                cards_parent.addView(button)
            }
        }
        else if(game_complexity == 1){

            val screenWidth = resources.displayMetrics.widthPixels
            val screenHeight = resources.displayMetrics.heightPixels- 400
            val buttonWidth = screenWidth / 3 - (2 * 5)
            val buttonHeight = screenHeight / 6 - (2 * 5)

            cards_parent.rowCount = 6
            cards_parent.columnCount = 3

            val played_cards = mutableListOf<Card>()
            val first_cards = cards.take(9)
            for(card in first_cards){
                played_cards.add(card)
                played_cards.add(card)
            }
            played_cards.shuffle()

            played_cards.forEach { card ->
                val button = Button(this)
                //button.setText("?")
                button.setBackgroundResource(R.drawable.close_card)
                button.id = View.generateViewId()
                val layoutParams = GridLayout.LayoutParams()
                layoutParams.width = buttonWidth
                layoutParams.height = buttonHeight
                layoutParams.setMargins(5, 5, 5, 5) // Отступы между кнопками
                button.layoutParams = layoutParams

                val localCard = card

                pairsList.add(Pair(button.id, localCard.cardId))
                button.setOnClickListener {
                    CheckCard(played_cards, button, localCard)
                }
                cards_parent.addView(button)
            }
        }
        else{
            val screenWidth = resources.displayMetrics.widthPixels
            val screenHeight = resources.displayMetrics.heightPixels- 400
            val buttonWidth = screenWidth / 4 - (2 * 5)
            val buttonHeight = screenHeight / 6 - (2 * 5)

            cards_parent.rowCount = 6
            cards_parent.columnCount = 4

            val played_cards = mutableListOf<Card>()
            val first_cards = cards.take(12)
            for(card in first_cards){
                played_cards.add(card)
                played_cards.add(card)
            }
            played_cards.shuffle()

            played_cards.forEach { card ->
                val button = Button(this)
                //button.setText("?")
                button.setBackgroundResource(R.drawable.close_card)
                button.id = View.generateViewId()
                val layoutParams = GridLayout.LayoutParams()
                layoutParams.width = buttonWidth
                layoutParams.height = buttonHeight
                layoutParams.setMargins(5, 5, 5, 5) // Отступы между кнопками
                button.layoutParams = layoutParams

                val localCard = card

                pairsList.add(Pair(button.id, localCard.cardId))
                button.setOnClickListener {
                    CheckCard(played_cards, button, localCard)
                }
                cards_parent.addView(button)
            }
        }

        time = 0
        running = true
        runTimer()
    }
    var handler: Handler = Handler(Looper.getMainLooper())
    fun runTimer(){
        handler.post(object : Runnable {
            override fun run() {

                val info = StringBuilder()
                info.append("GRID: ")
                if(game_complexity == 0){
                    info.append("2 X 4   ")
                    info.append("PAIRS: $pairs / 4   ")
                }
                else if(game_complexity == 1){
                    info.append("3 X 6   ")
                    info.append("PAIRS: $pairs / 9   ")
                }
                else{
                    info.append("4 X 6   ")
                    info.append("PAIRS: $pairs / 12   ")
                }

                info.append("TIME: $time   ")

                gameInfo.setText(info.toString())

                if (running) {
                    time++
                }

                handler.postDelayed(this, 1000)
            }
        })
    }

    fun ShowWin(){
        winLayout.visibility= View.VISIBLE
        win_info.setText("Вы победили!\nВаше время: $time")
    }

    fun CheckWin(){
        if(game_complexity == 0){
            if(pairs == 4){
                running = false
                ShowWin()
            }
        }
        else if(game_complexity == 1){
            if(pairs == 9){
                running = false
                ShowWin()
            }
        }
        else{
            if(pairs == 12){
                running = false
                ShowWin()
            }
        }
    }

    fun OpenSettings(view: View){
        running = false
        winLayout.visibility = View.GONE
        selectSizeLayout.visibility = View.VISIBLE
    }
    fun SelectSize(view: View){
        if(view.id == R.id.easy_size_button){
            game_complexity = 0
            handler.removeCallbacksAndMessages(null)
            StartGame()
            selectSizeLayout.visibility = View.GONE
        }
        else if(view.id == R.id.medium_size_button){
            game_complexity = 1
            handler.removeCallbacksAndMessages(null)
            StartGame()
            selectSizeLayout.visibility = View.GONE
        }
        else if(view.id == R.id.hard_size_button){
            game_complexity = 2
            handler.removeCallbacksAndMessages(null)
            StartGame()
            selectSizeLayout.visibility = View.GONE
        }

    }

    fun ReloadGame(view: View){
        handler.removeCallbacksAndMessages(null)
        winLayout.visibility = View.GONE
        StartGame()
    }

    fun GoBack(view: View){
        finish()
    }

}