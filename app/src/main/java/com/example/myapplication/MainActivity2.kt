package com.example.myapplication
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main2.*

var player1WinCount = 0
var player2WinCount = 0
class MainActivity2 : AppCompatActivity() {

    //---------------------Variables--------------------------------------

    private var playerOneTurn = true
    private var playerOneMoves = mutableListOf<Int>()
    private var playerTwoMoves = mutableListOf<Int>()
     private var playerOneName : String = ""
     private var playerTwoName : String = ""
    private var message1 : String = "" // for player turn
    private var message2 : String = ""
    private val possibleWins: Array<List<Int>> = arrayOf(
        //horizontal lines
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9),

        //vertical lines
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(3, 6, 9),

        //diagonal lines
        listOf(1, 5, 9),
        listOf(3, 5, 7)
    )



    //---------------------Functions--------------------------------------



    private fun resetWinCount(){
        player1WinCount = 0
        player2WinCount = 0
        player1count.text = "0"
        player2count.text = "0"
    }
    private fun gotoPvsActivity(){
        val intent  = Intent(this,MainActivity::class.java)
        finish()
        startActivity(intent)
        resetWinCount()
    }
    private fun reStartActivity(){
        val intent = intent
        finish()
        startActivity(intent)
    }
    private fun gameback(){
        val builder = AlertDialog.Builder(this)
        with(builder){
            setTitle("EXIT")
            setMessage(getString(R.string.exit_message))
            setPositiveButton(getString(R.string.yes)
            ) { dialog, which -> gotoPvsActivity()}
            setNegativeButton(getString(R.string.no)){
                    dialog,which ->
            }
        }
        builder.create().show()
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?):Boolean{
        if(keyCode==KeyEvent.KEYCODE_BACK){
            gameback()
            resetWinCount()
        }
        return false
    }
    private fun restartGame(){
        val builder = AlertDialog.Builder(this)
        with(builder){
            setTitle(getString(R.string.restartGame))
            setMessage(getString(R.string.Restart_message))
            setPositiveButton(getString(R.string.yes)
            ) { dialog, which -> reStartActivity() }
            setNegativeButton(getString(R.string.no)
            ) { dialog, which -> gotoPvsActivity() }
        }
        builder.create().show()
    }
    private fun recordMove(view: View){
        val button = view as Button
        val id = button.id
        if(playerOneTurn){
            playerOneMoves.add(id)
            playermove.text = message2
            button.text = "O"
            button.isEnabled = false
            if(checkWin(playerOneMoves)){
                showWinMessage(playerOneName)
                ++player1WinCount
                player1count.text = player1WinCount.toString()
                return
            } else{
                playerOneTurn = false
            }

        } else{
            playerTwoMoves.add(id)
            playermove.text = message1
            button.text = "X"
            button.isEnabled = false
            if(checkWin(playerTwoMoves)){
                showWinMessage(playerTwoName)
                ++player2WinCount
                player1count.text = player2WinCount.toString()
                return
            } else{
                playerOneTurn = true
            }
        }
        if(playerOneMoves.size==5){
            Toast.makeText(this,"Game Tie",Toast.LENGTH_SHORT).show()
            restartGame()
        }
    }
    private fun checkWin(moves: List<Int>): Boolean{
        var won = false
        if(moves.size >= 3){
            run loop@{
                possibleWins.forEach {
                    if (moves.containsAll(it)) {
                        won = true
                        return@loop
                    }
                }
            }
        }
        return won
    }
    private fun showWinMessage(playerName: String){
        Toast.makeText(this, "Congratulations! $playerName You Won", Toast.LENGTH_SHORT).show()
        restartGame()
    }


    //------------------------------------------------------------------------




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
         val board:LinearLayout = findViewById(R.id.board)
        val player1:String? = intent.getStringExtra("player1")
        val player2:String? = intent.getStringExtra("player2")
        player1?.let {
            val p1 = findViewById<TextView>(R.id.player1name)
                p1.text = it
                playerOneName = it
        }
        player2?.let {
            val p2 = findViewById<TextView>(R.id.player2name)
            p2.text = it
            playerTwoName= it
        }

        message1 = "$playerOneName turn"
        message2 = "$playerTwoName turn"

        playermove.text = message1
        player1count.text = player1WinCount.toString()
        player2count.text = player2WinCount.toString()

        boardSetup(board)

        //----------------Buttons------------------------------------------------


        resetwins.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            with(builder){
                setTitle("RESET SCORE")
                setMessage(getString(R.string.Reset_message))
                setPositiveButton(getString(R.string.yes)
                ) { dialog, which -> resetWinCount()}
                setNegativeButton(getString(R.string.no)){
                    dialog,which ->
                }
            }
            builder.create().show()
        }
        backbutton.setOnClickListener{
           gameback()
        }
        resetgamebutton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            with(builder){
                setTitle("RESET")
                setMessage(getString(R.string.Reset_message))
                setPositiveButton(getString(R.string.yes)
                ) { dialog, which -> reStartActivity()}
                setNegativeButton(getString(R.string.no)){
                        dialog,which ->
                }
            }
            builder.create().show()
        }
    }



    //----------------Board Setup---------------------------------------



    private fun boardSetup(board:LinearLayout){
        var count = 1
        val p1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,0
        )
        val p2 = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        for(i in 1..3){
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = p1
            linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700))
            p1.weight  = 1.0F

            for(j in 1..3){
                val button = Button(this)
                button.id = count
                button.textSize = 42.0F
                button.setTextColor(ContextCompat.getColor(this, R.color.purple_700))
                button.layoutParams = p2
                p2.weight = 1.0F
                button.setOnClickListener{
                    recordMove(it)
                }
                linearLayout.addView(button)
                count++
            }
            board.addView(linearLayout)
        }
    }
}