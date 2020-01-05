package connect4

import com.example.a4inarow.connect4.Chip

interface CheckStraightLine {
    fun getWinChipIndex(checkedChip: Chip, row: Int, column: Int) : MutableList<IntArray>
}