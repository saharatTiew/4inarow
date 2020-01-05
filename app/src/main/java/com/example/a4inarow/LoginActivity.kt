package com.example.a4inarow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a4inarow.connect4.Player
import com.github.kittinunf.fuel.Fuel
import kotlinx.android.synthetic.main.activity_login.*

var player1: Player? = null
var player2: Player? = null
var player1Id: String = ""
var player2Id: String = ""

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        btPlay.setOnClickListener {
            player1Id = etPlayer1.text.toString()
            player2Id = etPlayer2.text.toString()
            player1 = Player(player1Id, "player1")
            player2 = Player(player2Id, "player2")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
//            checkUserId()
        }
    }

    private fun checkUserId() {

        if (player1Id.isEmpty() && player2Id.isEmpty()) {
            AlertDialog("Login", "12").show(supportFragmentManager, "popup_info")
        } else if (player1Id.isEmpty()) {
            AlertDialog("Login", "1").show(supportFragmentManager, "popup_info")
        } else if (player2Id.isEmpty()) {
            AlertDialog("Login", "2").show(supportFragmentManager, "popup_info")
        } else if (player1Id.isNotEmpty() && player2Id.isNotEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            Fuel.get(
                "",
                listOf("playerId" to player1Id)
            )
                .responseObject(Player.Deserializer()) { _, _, result ->
                    result.fold({
                        Fuel.get(
                            "",
                            listOf("playerId" to player2Id)
                        )
                            .responseObject(Player.Deserializer()) { req, res, result ->
                                result.fold({
                                    startActivity(intent)
                                }, {
                                    AlertDialog("Login", true).show(supportFragmentManager, "popup_info")
                                })
                            }
                    }
                        , {
                            AlertDialog("Login", true).show(supportFragmentManager, "popup_info")
                        })
                }
        }
    }
}

