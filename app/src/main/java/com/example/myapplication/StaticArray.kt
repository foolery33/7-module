package com.example.myapplication

class StaticArray(name: String, length: String): MainActivity() {

    var success = true
    var errors = mutableListOf<String>()

    init {
        if(isCorrectName(name)) {
            var lengthExpression = Expression(length)
            if(lengthExpression.success) {
                if(lengthExpression.valueOfExpression.toInt() <= 0) {
                    this.success = false
                    this.errors.add("Length of array can't be less than zero")
                }
            }
            else {
                this.success = false
                this.errors.add("Incorrect length of array `$length`")
            }

            if(this.success) {
                if(!intArrays.containsKey(name)) {
                    if(intVariables.containsKey(name)) {
                        this.success = false
                        this.errors.add("Variable $name has the same name as array $name")
                    }
                    else {
                        intArrays[name] = Array(length.toInt()) { -92314123 }
                    }
                }
                else {
                    this.success = false
                    this.errors.add("Array $name has already been initialized")
                }
            }
        }
        else {
            this.success = false
            this.errors.add("Incorrect name of array `$name`")
        }
    }

    fun isCorrectName(name: String): Boolean {
        return Regex("[a-zA-Z]\\w*").matchEntire(name) != null
    }

}