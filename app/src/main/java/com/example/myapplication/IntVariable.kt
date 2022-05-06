package com.example.myapplication

class IntVariable(): MainActivity() {

    var name: String = ""
    var value: Int = 0
    var success = true
    var errors = mutableListOf<String>()

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
            this.errors.add("Cannot process string $string")
            this.success = false
        }
    }

    private fun isSequenceOfVariables(sequenceOfVariables: String): Boolean {
        val matchResult = Regex("(([A-Za-z]\\w*) *,* *)+").matchEntire(sequenceOfVariables)
        return if (matchResult != null) {
            true
        }
        else {
            this.success = false
            false
        }
    }

    private fun processSequenceOfVariables(sequenceOfVariables: String) {
        val variablesInSequence = Regex("[A-Za-z]\\w*").findAll(sequenceOfVariables)
        variablesInSequence.forEach { f ->
            val currentVariable: String = f.value
            addToMap(currentVariable, 0)
        }
    }

    private fun isCorrectVariableName(name: String): Boolean {
        return Regex("[A-Za-z]\\w*").matchEntire(name) != null
    }

    fun addToMap(name: String, value: Int) {
        if(intVariables.containsKey(name)) {
            intVariables[name] = value
        }
        else {
            intVariables[name] = value
        }
    }

}