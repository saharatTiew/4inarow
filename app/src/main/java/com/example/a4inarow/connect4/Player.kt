package com.example.a4inarow.connect4

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Player(
    var playerId: String,
    var name: String,
    var numberOfWin: Int,
    var numberOfLoss: Int,
    var numberOfDraw: Int,
    var winRate: Double,
    var chip: Chip = ChipFactory.getChip("null")
) {

    constructor(playerId: String,name: String="") : this(playerId,name,0,0,0,0.00)

    class Deserializer : ResponseDeserializable<Player> {
        override fun deserialize(content: String) = Gson().fromJson(content, Player::class.java)
    }

    fun setJsonString(): String {
        return "{\"userName\" : \"$playerId\",\"name\" : \"$name\"}"
    }
}