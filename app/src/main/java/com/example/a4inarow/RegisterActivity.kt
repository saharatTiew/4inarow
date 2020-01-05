package com.example.a4inarow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a4inarow.connect4.Player
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var player_login: Player? = null
        btConfirm.setOnClickListener {
            var username = etUser.text.toString()
            var nickname = etNickname.text.toString()
            player_login = Player(username, nickname)

//            postJson(player_login!!, username, nickname)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun postJson(playerJson: Player, username: String, nickname: String) {
        if (username.isEmpty() && nickname.isEmpty()) {
            AlertDialog("Registration", "12").show(supportFragmentManager, "popup_info")
        } else if (username.isEmpty()) {
            AlertDialog("Registration", "1").show(supportFragmentManager, "popup_info")
        } else if (nickname.isEmpty()) {
            AlertDialog("Registration", "2").show(supportFragmentManager, "popup_info")
        } else if (username.isNotEmpty() && nickname.isNotEmpty())
            Fuel.post("")
                .header("Content-Type" to "application/json")
                .jsonBody(playerJson.setJsonString())
                .response { _, _, result ->
                    result.fold(success = {
                        AlertDialog("Registration", true).show(supportFragmentManager, "popup_info")
                    }, failure = {
                        AlertDialog("Registration", false).show(supportFragmentManager, "popup_info")
                    })
                }
    }
}
