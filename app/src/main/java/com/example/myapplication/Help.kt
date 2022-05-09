package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class HelpActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val ButtonReturn: ImageButton = findViewById(R.id.return_button)

        ButtonReturn.setOnClickListener{
            val intent = Intent(this@HelpActivity, InterfaceActivity::class.java)
            startActivity(intent)
        }
    }
}