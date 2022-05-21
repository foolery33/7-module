package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.namespace.R

open class HelpActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val buttonReturn: ImageButton = findViewById(R.id.return_button)

        buttonReturn.setOnClickListener{
            val intent = Intent(this@HelpActivity, MainActivity::class.java)
            this.onDestroy()
            startActivity(intent)
        }
    }
}