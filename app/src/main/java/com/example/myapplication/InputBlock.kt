package com.example.myapplication

import android.widget.TextView

class InputBlock(name: String, text: TextView) : MainActivity() {

    var name = ""
    var textView = text
    var value = ""
    var success = true
    var errors = mutableListOf<String>()

    init {
        this.name = name.replace(" ", "")
        if (inputValues[this.name] != null) {
            this.value = inputValues[this.name]!!
            processData(this.name, this.value)
        } else {
            this.success = false
            this.errors.add("There are no such variable(-s) as $name")
        }
    }

    fun processData(name: String, value: String) {
        var names = name.split(",")
        var values = value.split(",")
        if (names.size != values.size) {
            this.errors.add("Number of variables doesn't match to number of values")
            this.success = false
        } else {
            for (i in names.indices) {
                var a = AssignmentOperation(names[i], values[i], textView)
                if (!a.success) {
                    this.success = false
                    for (i in a.errors) {
                        this.errors.add(i)
                    }
                    break
                }
            }
        }
    }

}