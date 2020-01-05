package com.example.a4inarow

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_alert_dialog.*


class AlertDialog(activityName: String, check: Boolean) : DialogFragment() {
    var unnamedPlayer = ""

    constructor(activityName: String, unnamedPlayer: String) : this(activityName, false) {
        this.unnamedPlayer = unnamedPlayer
    }

    private var activity = activityName
    var check = check
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.dialog!!.setCancelable(false)
        return inflater.inflate(R.layout.fragment_alert_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (this.activity) {
            "Registration" -> {
                when (unnamedPlayer) {
                    "12" -> {
                        setText("Incorrect Username", "Please put username and nickname")
                    }
                    "1" -> {
                        setText("Username not found", "Please put username")
                    }
                    "2" -> {
                        setText("Nickname not found", "Please put nickname")
                    }
                    else -> {
                        if (this.check) {
                            setText("Thank you!", "Successful Registration")
                            btDone.setOnClickListener {
                                var intent = Intent(this.context, LoginActivity::class.java)
                                startActivity(intent)
                                this.check = false
                            }
                        } else {
                            setText("Username has been used", "Please put username")
                        }
                    }
                }
                if (!this.check && unnamedPlayer == "") {
                    btDone.setOnClickListener { dismiss() }
                }
            }
            "Login" -> {
                when (unnamedPlayer) {
                    "12" -> {
                        setText("Incorrect Username", "Please put username1 and username2")
                    }
                    "1" -> {
                        setText("Incorrect Username ", "Please put username1")
                    }
                    "2" -> {
                        setText("Incorrect Username", "Please put username2")
                    }
                    else -> {
                        setText("Incorrect Username", "Please try it again")
                    }
                }
                btDone.setOnClickListener { dismiss() }
            }
        }
    }

    private fun setText(title: String, body: String) {
        tvTitle_dialog.text = title
        tvBody_dialog.text = body
    }

}
