package com.example.a4inarow.connect4

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import com.example.a4inarow.player1Id
import com.example.a4inarow.player2Id
import com.github.kittinunf.fuel.Fuel
import connect4.CheckStraightLine

class ConnectFour(
    var player1: Player,
    var player2: Player,
    var scoreWinPlayer1: Int,
    var scoreWinPlayer2: Int,
    var board: Board
) {

    var playerTurn = player1
    var allResult: MutableList<IntArray>? = null
    private var checkStraightLine: CheckStraightLine? = null

    fun setStrategy(checkStraightLine: CheckStraightLine) {
        this.checkStraightLine = checkStraightLine
    }

    fun addChip(column: Int): Boolean {
        if (board.addChip(playerTurn.chip, column)) {
            return true
        }
        return false
    }

    fun checkWinner(row: Int, column: Int): Player? {
        allResult = checkStraightLine?.getWinChipIndex(playerTurn.chip, row, column)
        if (allResult!!.isEmpty()) {
            this.changePlayer()
        } else {
            return playerTurn

        }
        return null
    }

    fun clean() {
        this.board.clean()
        this.playerTurn = player1
    }

    fun isFull(): Boolean {
        return this.board.isFull()
    }

    fun updateScore(isDraw: Boolean) {
        if (isDraw) {
//            postJsonScore(player1Id, "Draw")
//            postJsonScore(player2Id, "Draw")
        } else {
            if (playerTurn == player1) {
                scoreWinPlayer1++
//                postJsonScore(player1Id, "Win")
//                postJsonScore(player2Id, "Lose")
            } else if (playerTurn == player2) {
                scoreWinPlayer2++
//                postJsonScore(player2Id, "Win")
//                postJsonScore(player1Id, "Lose")
            }
        }
    }

    fun findButtonRow(column: Int): Int {
        return board.findButtonRow(column)
    }

    fun findButtonColumn(button: Button): Int {
        return board.findButtonColumn(button)
    }

    fun showResult() {
        var buttonList = board.buttonList
        allResult!!.map {
            blinkButton(buttonList[it[0]][it[1]])
        }
    }

    //  change Player between player1 and player2
    private fun changePlayer() {
        if (playerTurn.equals(player1)) {
            this.playerTurn = player2
        } else if (playerTurn.equals(player2)) {
            this.playerTurn = player1
        }
    }

    private fun postJsonScore(playerId: String, result: String) {
        var url = ""
        when (result) {
            "Win" -> {
                url += "UpdateWin"
            }
            "Lose" -> {
                url += "UpdateLoss"
            }
            "Draw" -> {
                url += "UpdateDraw"
            }
        }
        Fuel.post(url, listOf("playerId" to playerId))
            .header("Content-Type" to "application/json")
            .response { _, _, result -> }
    }

    private fun blinkButton(button: Button) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 400
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = 4
        button.startAnimation(anim)
    }
}