package connect4

import com.example.a4inarow.connect4.Board
import com.example.a4inarow.connect4.Chip
import com.example.a4inarow.connect4.ChipFactory
import com.example.a4inarow.connect4.NullChip

class NormalCheck(private val board: Board, private val requiredChip: Int) : CheckStraightLine {

    private val nullChip: NullChip? = ChipFactory.getChip("null") as? NullChip

    override fun getWinChipIndex(checkedChip: Chip, row: Int, column: Int): MutableList<IntArray> {
        var colorList = mutableListOf<IntArray>()
        colorList = unionList(countHorizontalInARow(checkedChip, row), colorList)
        colorList = unionList(countVerticalInARow(checkedChip, column), colorList)
        colorList = unionList(countLeftDiagonalInARow(checkedChip, row, column), colorList)
        colorList = unionList(countRightDiagonalInARow(checkedChip, row, column), colorList)
        return colorList
    }

    private fun unionList(
        countList: MutableList<IntArray>,
        targetedList: MutableList<IntArray>
    ): MutableList<IntArray> {
        if (countList.size >= requiredChip) {
            return targetedList.union(countList).toMutableList()
        }
        return targetedList.toMutableList()
    }

    private fun transferTempToRealList(
        tempList: MutableList<IntArray>,
        realList: MutableList<IntArray>
    ): MutableList<IntArray> {
        if (tempList.size > realList.size) {
            realList.clear()
            realList.addAll(tempList)
        }
        return realList
    }

    private fun isTerminable(
        isLeftDiagonal: Boolean, isLeftSide: Boolean, matchedIndexList: MutableList<IntArray>
        , currentRow: Int, currentColumn: Int
    ): Boolean {

        return if (isLeftDiagonal) {
            (isLeftSide && matchedIndexList.size >= this.board.row - 1 - currentRow) ||
                    (!isLeftSide && matchedIndexList.size >= currentRow - currentColumn)
        } else {
            (isLeftSide && matchedIndexList.size >= currentColumn) ||
                    (!isLeftSide && matchedIndexList.size > this.board.row - 1 - currentRow)
        }
    }

    private fun countHorizontalInARow(checkedChip: Chip, row: Int): MutableList<IntArray> {
        val tempIndexList = mutableListOf<IntArray>()
        var matchedIndexList = mutableListOf<IntArray>()

        for (i in 0 until this.board.column) {
            if (this.board.boardArray[row][i] != checkedChip) {
                matchedIndexList = transferTempToRealList(tempIndexList, matchedIndexList)
                tempIndexList.clear()

                if (matchedIndexList.size >= this.board.column - i) {
                    return matchedIndexList
                }

            } else {
                tempIndexList.add(intArrayOf(row, i))
            }
        }

        if (this.board.boardArray[row][this.board.column - 1] == checkedChip
            && tempIndexList.size > matchedIndexList.size
        ) {
            return tempIndexList
        }
        return matchedIndexList
    }

    private fun countVerticalInARow(checkedChip: Chip, column: Int): MutableList<IntArray> {
        var matchedIndexList = mutableListOf<IntArray>()
        val tempIndexList = mutableListOf<IntArray>()

        for (i in this.board.row - 1 downTo 0) {
            if (board.boardArray[i][column] == nullChip) {
                if (tempIndexList.size > matchedIndexList.size) {
                    return tempIndexList
                }
                return matchedIndexList

            } else if (board.boardArray[i][column] == checkedChip) {
                tempIndexList.add(intArrayOf(i, column))

            } else {
                matchedIndexList = transferTempToRealList(tempIndexList, matchedIndexList)
                tempIndexList.clear()

                if (matchedIndexList.size >= this.board.row - i) {
                    return matchedIndexList
                }
            }
        }

        if (board.boardArray[0][column] == checkedChip && tempIndexList.size > matchedIndexList.size) {
            return tempIndexList
        }

        return matchedIndexList
    }

    private fun countLeftDiagonalInARow(checkedChip: Chip, row: Int, column: Int): MutableList<IntArray> {
        var currentRow: Int
        var currentColumn: Int
        var matchedIndexList = mutableListOf<IntArray>()
        val tempIndexList = mutableListOf<IntArray>()
        val isLeftSide: Boolean

        if (column > row) {
            currentRow = 0
            currentColumn = column - row
            isLeftSide = false
        } else {
            currentRow = row - column
            currentColumn = 0
            isLeftSide = true
        }

        while (currentColumn < this.board.column && currentRow < this.board.row) {
            if (board.boardArray[currentRow][currentColumn] != checkedChip) {
                matchedIndexList = transferTempToRealList(tempIndexList, matchedIndexList)
                tempIndexList.clear()

                if (isTerminable(true, isLeftSide, matchedIndexList, currentRow, currentColumn)) {
                    return matchedIndexList
                }

            } else {
                tempIndexList.add(intArrayOf(currentRow, currentColumn))
            }
            ++currentColumn
            ++currentRow
        }

        if (board.boardArray[currentRow - 1][currentColumn - 1] == checkedChip
            && tempIndexList.size > matchedIndexList.size
        ) {
            return tempIndexList
        }
        return matchedIndexList
    }

    private fun countRightDiagonalInARow(checkedChip: Chip, row: Int, column: Int): MutableList<IntArray> {
        val middleColumn = this.board.row - 1 - row
        var currentRow: Int
        var currentColumn: Int
        val isLeftSide: Boolean
        var matchedIndexList = mutableListOf<IntArray>()
        val tempIndexList = mutableListOf<IntArray>()

        if (column <= middleColumn) {
            currentRow = 0
            currentColumn = column + row
            isLeftSide = true
        } else {
            currentRow = row - (this.board.column - 1 - column)
            currentColumn = this.board.column - 1
            isLeftSide = false
        }

        while (currentColumn >= 0 && currentRow < this.board.row) {
            if (board.boardArray[currentRow][currentColumn] != checkedChip) {
                matchedIndexList = transferTempToRealList(tempIndexList, matchedIndexList)

                if (isTerminable(false, isLeftSide, matchedIndexList, currentRow, currentColumn)) {
                    return matchedIndexList
                }
                tempIndexList.clear()

            } else {
                tempIndexList.add(intArrayOf(currentRow, currentColumn))
            }
            ++currentRow
            --currentColumn
        }

        if (board.boardArray[currentRow - 1][currentColumn + 1] == checkedChip
            && tempIndexList.size > matchedIndexList.size
        ) {
            return tempIndexList
        }
        return matchedIndexList
    }
}

