package com.example.myapplication

class IntVariable(name: String): MainActivity() {

    var success = true
    var errors = mutableListOf<String>()

    init {
        var newName = name.replace(" ", "")
        if (isCorrectVariableName(newName)) {
            addToMap(newName, 0)
        }
        else if(isSequenceOfVariables(newName)) {
            processSequenceOfVariables(newName)
        }
        else {
            this.errors.add("Cannot process string $name")
            this.success = false
        }
    }

    private fun isSequenceOfVariables(sequenceOfVariables: String): Boolean {
        val matchResult = Regex("(([A-Za-z]\\w*),*)+").matchEntire(sequenceOfVariables)
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
        return (Regex("[A-Za-z]\\w*").matchEntire(name) != null)
    }

    fun addToMap(name: String, value: Int) {
        if(intVariables.containsKey(name)) {
            this.success = false
            this.errors.add("Variable $name has already been declared")
        }
        else {
            intVariables[name] = value
        }
    }

}