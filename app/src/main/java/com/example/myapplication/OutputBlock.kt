package com.example.myapplication

class OutputBlock(name: String): MainActivity() {

    var name = ""
    var errors = mutableListOf<String>()
    var success = true
    var outputValue = ""

    init {
        this.name = name
        var nameExpression = Expression(name)
        if(nameExpression.success) {
            this.outputValue = nameExpression.valueOfExpression
            println(this.outputValue)
        }
        else {
            this.errors.add("Variable $name has not been declared")
            this.success = false
        }
    }
}