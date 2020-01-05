package com.example.a4inarow.connect4
object ChipFactory {

    private val chipMap = HashMap<String, Chip>()

    fun getChip(color: String): Chip {
        var chip: Chip? = chipMap[color]

        if (chip == null) {
            when {
                color.equals("red", ignoreCase = true) -> chip = RedChip()
                color.equals("yellow", ignoreCase = true) -> chip = YellowChip()
                else -> chip = NullChip()
            }

            chipMap[color] = chip
        }

        return chip
    }

}
