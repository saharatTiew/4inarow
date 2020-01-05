package com.example.a4inarow.connect4

import android.widget.Button
import com.example.a4inarow.R

class Board(var row: Int, var column: Int, var buttonList: Array<Array<Button>>) {
     var boardArray: Array<Array<Chip>> =  Array(row) { Array(column) { ChipFactory.getChip("null") } }
    private val nullChip: NullChip? = ChipFactory.getChip("null") as? NullChip

    fun clean() {

        for (i in 0 until row) {
            for (j in 0 until column) {
                boardArray[i][j] = nullChip as Chip
                buttonList[i][j].setBackgroundResource(R.drawable.circle_shape)
            }
        }

    }

    fun isFull(): Boolean {

        boardArray.forEach { row ->
            row.forEach { chip ->
                if (chip == nullChip) {
                    return false
                }
            }
        }
        return true
    }

    fun addChip(chip: Chip, column: Int): Boolean {

        for (i in this.row - 1 downTo 0) {
            if (boardArray[i][column] == nullChip) {
                boardArray[i][column] = chip
                changeButtonColor(chip, buttonList[i][column])
                return true
            }
        }
        return false
    }

    fun findButtonRow(column: Int): Int {
        for (i in this.row - 1 downTo 0) {
            if (boardArray[i][column] == nullChip) {
                return i
            }
        }
        return -1
    }

    fun findButtonColumn(button: Button): Int {
        for (i in 0 until row) {
            for (j in 0 until column) {
                if (buttonList[i][j] == button) {
                    return j
                }
            }
        }
        return -1
    }

    private fun changeButtonColor(chip: Chip, button: Button) {
        if (chip.color.equals("red")) {
            button.setBackgroundResource(R.drawable.red_circle_shape)
        } else if (chip.color.equals("yellow")) {
            button.setBackgroundResource(R.drawable.yellow_circle_shape)
        }
    }
}