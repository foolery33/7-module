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
        IntVariable("variable")
        AssignmentOperation("variable", "123")
        OutputBlock("variable")
        StaticArray("array", "10")
        InputBlock("array[0],array[1],array[2],array[3]", "1,2,3,4")
        AssignmentOperation("array[4]","array[0]+array[1]+array[2]+array[3]")
        OutputBlock("array[array[0]+array[2]]")
        OutputBlock("array[4]")
    }

}
