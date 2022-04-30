package com.example.myapplication

import android.R.attr.data
import android.util.Log
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class MainActivity : AppCompatActivity() {

    companion object {
        @JvmField
        var variables = mutableMapOf<String, Int>()
        var arrays = mutableMapOf<String, Array<Int>>()

        var ifConditionsCounter = -1
        var ifConditions = mutableListOf<IfConditions>()
        var elseConditionsCounter = -1
        var elseConditions = mutableListOf<ElseConditions>()

        fun addToMap(name: String, value: Int) {
            if(variables.containsKey(name)) {
                variables[name] = value
            }
            else {
                variables[name] = value
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val a = findViewById<EditText>(R.id.aaa)
        Log.i("ASD", a.getText().toString())
    }

}
