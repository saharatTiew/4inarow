package com.example.a4inarow

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.a4inarow.connect4.*
import com.github.kittinunf.fuel.Fuel
import connect4.NormalCheck
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.option_dialog.view.*
import kotlinx.android.synthetic.main.winner_dialog.view.*

class MainActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null
    var row = 6
    var column = 7
    var isWon = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.my_toolbar)
        toolbar!!.setBackgroundResource(R.color.redChip)

        setPlayerFromJson(player1!!)
        setPlayerFromJson(player2!!)

        //set all of buttons Id
        val buttonArray: Array<Button> = arrayOf(
            bt00,
            bt01,
            bt02,
            bt03,
            bt04,
            bt05,
            bt06,
            bt10,
            bt11,
            bt12,
            bt13,
            bt14,
            bt15,
            bt16,
            bt20,
            bt21,
            bt22,
            bt23,
            bt24,
            bt25,
            bt26,
            bt30,
            bt31,
            bt32,
            bt33,
            bt34,
            bt35,
            bt36,
            bt40,
            bt41,
            bt42,
            bt43,
            bt44,
            bt45,
            bt46,
            bt50,
            bt51,
            bt52,
            bt53,
            bt54,
            bt55,
            bt56
        )

        var buttonList = Array(row) { Array(column) { bt00 } }
        buttonList = setButtonList(buttonArray, buttonList)

        var board = Board(row, column, buttonList)
        player1!!.chip = ChipFactory.getChip("red")
        player2!!.chip = ChipFactory.getChip("yellow")
        var connectFour = ConnectFour(player1!!, player2!!, 0, 0, board)
        toolbar_tiltle.text = player1?.chip?.color?.toUpperCase() + " TURN"

        connectFour.setStrategy(NormalCheck(board, 4))

        buttonList.map { i ->
            i.map { button ->
                button.setOnClickListener {
                    if (connectFour.isFull()) {
                        connectFour.updateScore(true)
                        showWinner(connectFour, null)
                    } else {
                        var buttonColumn = connectFour.findButtonColumn(button)
                        var buttonRow = connectFour.findButtonRow(buttonColumn)
                        if (buttonRow != -1) {
                            if (!isWon) {
                                connectFour.addChip(buttonColumn)
                                var winner = connectFour.checkWinner(buttonRow, buttonColumn)
                                setPlayerTurn(connectFour.playerTurn.chip.color.toString())
                                if (connectFour.allResult!!.isNotEmpty()) {
                                    isWon = true
                                    connectFour.updateScore(false)
                                    connectFour.showResult()
                                    tvScore1.text = connectFour.scoreWinPlayer1.toString()
                                    tvScore2.text = connectFour.scoreWinPlayer2.toString()
                                    Handler().postDelayed({
                                        showWinner(connectFour, winner!!)
                                    }, 3500)
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    private fun setPlayerFromJson(player: Player, tvWinner: TextView? = null) {
//        Fuel.get(
//            "",
//            listOf("playerId" to player.playerId)
//        )
//            .responseObject(Player.Deserializer()) { req, res, result ->
//                result.fold({ player ->
//                    tvWinner?.setText("${player.name} Win!!")
//                    setUser(player)
//                }, { error -> Log.e("Error", error.message) })
//            }
    }

    private fun setUser(player: Player) {
        when (player.playerId) {
            player1Id -> {
                tvNickname1.text = player.name
                tvWinRate1.text = "Win rate: ${"%.2f".format(player.winRate)}%"
            }
            player2Id -> {
                tvNickname2.text = player.name
                tvWinRate2.text = "Win rate: ${"%.2f".format(player.winRate)}%"
            }
        }
    }

    private fun setButtonList(buttonArray: Array<Button>, buttonList: Array<Array<Button>>): Array<Array<Button>> {
        var i = -1
        for (row in 0 until row) {
            for (column in 0 until column) {
                i++
                buttonList[row][column] = buttonArray[i]
            }
        }
        return buttonList
    }

    private fun alertDialog(connectFour: ConnectFour) {
        val intent = Intent(this, LoginActivity::class.java)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.option_dialog, null)
        val mDialog = Dialog(this)
        mDialog.setContentView(mDialogView)
        mDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        mDialog.setCancelable(false)
        mDialog.show()
        mDialogView.btPlayAgain_dialog.setOnClickListener {
            isWon = false
            mDialog.dismiss()
            connectFour.clean()
        }
        mDialogView.btExit_dialog.setOnClickListener {
            startActivity(intent)
        }
    }

    private fun showWinner(connectFour: ConnectFour, winner: Player?) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.winner_dialog, null)
        val mDialog = Dialog(this)
        mDialog.setContentView(mDialogView)
        mDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        mDialog.setCancelable(false)
        var tvWinner = mDialogView.tvWinner
        if (winner == null) {
            tvWinner.text = "Draw"
        } else {
//            setPlayerFromJson(winner, tvWinner)
        }
        mDialog.show()
        Handler().postDelayed({
            mDialog.dismiss()
            alertDialog(connectFour)
        }, 3000)
    }

    private fun setPlayerTurn(color: String) {
        if (color == "red") {
            toolbar_tiltle.text = color.toUpperCase() + " TURN"
            toolbar?.setBackgroundColor(getColor(R.color.redChip))
            ivChip.setBackgroundResource(R.drawable.red_chip)
            ivLine.setBackgroundResource(R.drawable.red_line)
        } else if (color == "yellow") {
            toolbar_tiltle.text = color.toUpperCase() + " TURN"
            toolbar!!.setBackgroundColor(getColor(R.color.yellow))
            ivChip.setBackgroundResource(R.drawable.yellow_chip)
            ivLine.setBackgroundResource(R.drawable.yellow_line)
        }
    }
}

