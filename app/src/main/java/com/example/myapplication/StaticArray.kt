package com.example.myapplication

class StaticArray(name: String, length: String): MainActivity() {

    var name = ""
    var length = 0
    var success = true
    var errors = mutableListOf<String>()

    init {

        if(isCorrectName(name)) {
            this.name = name
        }
        else {
            this.success = false
            this.errors.add("Incorrect name of array `$name`")
        }

        var expression = Expression(length)
        if(expression.success) {
            if(expression.valueOfExpression.toInt() > 0) {
                this.length = expression.valueOfExpression.toInt()
            }
            else {
                this.success = false
                this.errors.add("Length of array can't be less than zero")
            }
        }
        else {
            this.success = false
            this.errors.add("Incorrect length of array `$length`")
        }

        if(this.success) {
            if(!intArrays.containsKey(this.name)) {
                if(intVariables.containsKey(this.name)) {
                    this.success = false
                    this.errors.add("Variable ${this.name} has the same name")
                }
                else {
                    intArrays[this.name] = Array(this.length) { -92314123 }
                }
            }
            else {
                this.success = false
                this.errors.add("Array ${this.name} has already been initialized")
            }
        }
    }

    fun isCorrectName(name: String): Boolean {
        return Regex("[a-zA-Z]\\w*").matchEntire(name) != null
    }
}