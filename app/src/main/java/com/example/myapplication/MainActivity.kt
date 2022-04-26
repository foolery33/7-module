package com.example.myapplication

import android.R.attr.data
import android.util.Log
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity




val variables = makeMutableMap()

var success = true

fun makeMutableMap() : MutableMap<String, Int> {
    var mutableMap = mutableMapOf("1" to 1)
    mutableMap.remove("1")
    return mutableMap
}

fun addToMap(name: String, value: Int) {
    if(variables.containsKey(name)) {
        variables.set(name, value)
    }
    else {
        variables.put(name, value)
    }
}

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(variables)
        var newInt = IntVariable("asd_")
        var newI = IntVariable("lae,lav,lab  ,laf")
        var newIn = IntVariable("a")
        println(variables)
        var new = IntVariable("name")
        println(variables)
        var operation = AssignmentOperation("asd_", "asd_ + -13 + name * 2 + 11 - 4/ 2")
        var operation1 = AssignmentOperation("asd_", "((-a) + (b))")
    }

}
