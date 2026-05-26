package com.example.sharedprefapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var et1: EditText
    private lateinit var et2: EditText
    private lateinit var et3: EditText

    private lateinit var bt: Button

    private val myPreferences = "MyPrefs"
    private val name = "nameKey"
    private val password = "passkey"
    private val email = "emailKey"

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        et1 = findViewById(R.id.editName)
        et2 = findViewById(R.id.editPassword)
        et3 = findViewById(R.id.editEmail)
        bt = findViewById(R.id.btnSave)

        sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)

        bt.setOnClickListener {
            val n = et1.text.toString()
            val pw = et2.text.toString()
            val e = et3.text.toString()

            val editor = sharedPref.edit()
            
            editor.putString(name, n)
            editor.putString(password, pw)
            editor.putString(email, e)
            
            editor.commit()

            Toast.makeText(this@MainActivity, "Thanks", Toast.LENGTH_LONG).show()
        }
    }
}
