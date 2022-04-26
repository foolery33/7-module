package com.example.myapplication

class IntVariable(): MainActivity() {

    var name: String = ""
    var value: Int = 0

    constructor(string: String) : this() {
        if (isCorrectVariableName(string)) {
            this.name = string
            this.value = 0
            addToMap(string, 0)
        }
        else if(isSequenceOfVariables(string)) {
            processSequenceOfVariables(string)
        }
        else {
            println("Cannot process string `$string`")
            success = false
        }
    }

    private fun isSequenceOfVariables(sequenceOfVariables: String): Boolean {
        val matchResult = Regex("(([A-Za-z_]\\w*) *,* *)+").matchEntire(sequenceOfVariables)
        return if (matchResult != null) {
            true
        }
        else {
            success = false
            false
        }
    }

    private fun processSequenceOfVariables(sequenceOfVariables: String) {
        val variablesInSequence = Regex("[A-Za-z_]\\w*").findAll(sequenceOfVariables)
        variablesInSequence.forEach { f ->
            val currentVariable: String = f.value
            addToMap(currentVariable, 0)
        }
    }

    private fun isCorrectVariableName(name: String): Boolean {
        val matchResult = Regex("[A-Za-z_]\\w*").matchEntire(name)
        if(matchResult == null) {
            return false
        }
        return true
    }

}