package com.example.myapplication

import android.widget.TextView

class StaticArray(name: String, length: String, text: TextView): MainActivity() {

    var name = ""
    var length = ""
    var success = true
    var errors = mutableListOf<String>()

    init {
        this.name = name.replace(" ", "")
        this.length = length.replace(" ", "")
        if(isCorrectName(this.name)) {
            var lengthExpression = Expression(length, text)
            if(lengthExpression.success) {
                if(lengthExpression.valueOfExpression.toInt() <= 0) {
                    this.success = false
                    this.errors.add("Length of array can't be less than zero")
                }
            }
            else {
                this.success = false
                this.errors.add("Incorrect length of array `${this.length}`")
            }

            if(this.success) {
                if(!intArrays.containsKey(this.name)) {
                    if(intVariables.containsKey(this.name)) {
                        this.success = false
                        this.errors.add("Variable ${this.name} has the same name as array ${this.name}")
                    }
                    else {
                        intArrays[this.name] = Array(this.length.toInt()) { -92314123 }
                    }
                }
                else {
                    this.success = false
                    this.errors.add("Array $this.name has already been initialized")
                }
            }
        }
        else {
            this.success = false
            this.errors.add("Incorrect name of array `$this.name`")
        }
    }

    fun isCorrectName(name: String): Boolean {
        return Regex("[a-zA-Z]\\w*").matchEntire(name) != null
    }

}