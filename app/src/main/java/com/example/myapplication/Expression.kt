package com.example.myapplication

open class Expression(expression: String) {
    open var expression = ""
    var correctExpression: String? = null
    var valueOfExpression = 0
    var errors: MutableList<String> = mutableListOf("1")

    init {
        errors.remove("1")
        this.expression = expression
        var newExpression = getCorrectExpression(this.expression)
        if(newExpression != null) {
            this.correctExpression = newExpression
            //this.valueOfExpression = getValueOfExpression(this.correctExpression);
        }
    }

    /*private fun getValueOfExpression(): Int {

    }*/

    private fun isCorrectExpression(expression: String): Boolean {
        val nonZeroVariable = "(?:-?(?:[1-9][0-9]*|[a-zA-Z_]\\w*))"
        val zeroVariable = "(?:-?(?:0|[1-9][0-9]*|[a-zA-Z_]\\w*))"
        val correctExpression = "$zeroVariable(?:[+*/%-]$nonZeroVariable|[+*-]$zeroVariable)*"

        val matchResult = Regex(correctExpression).matchEntire(expression)

        return matchResult != null
    }

    private fun processBrackets(expression: String): String {

        var newExpression = expression

        while (Regex("\\([^\\(\\)]+\\)").containsMatchIn(newExpression)) {
            var matchResult = Regex("\\([^\\(\\)]+\\)").findAll(newExpression)
            matchResult.forEach { f ->
                var value = f.value
                value = value.replace("(", "")
                value = value.replace(")", "")
                if(isCorrectExpression(value)) {
                    newExpression = newExpression.replace(f.value, "a")
                }
                else {
                    return newExpression
                }
            }
        }

        return newExpression
    }

    private fun getCorrectExpression(expression: String): String? {

        val editedExpression = expression.replace(" ", "");

        val expressionWithoutBrackets = processBrackets(editedExpression)

        if(!isCorrectExpression(expressionWithoutBrackets)) {
            errors.add("Cannot read expression '$expression'")
            return null
        }

        val variablesInExpression = Regex("([a-zA-Z]\\w*)").findAll(editedExpression)

        var finalExpression = editedExpression

        variablesInExpression.forEach { f ->
            val currentVariable: String = f.value
            if(variables.containsKey(currentVariable)) {
                finalExpression = finalExpression.replace(currentVariable, variables[currentVariable].toString())
            }
            else {
                errors.add("Variable $currentVariable has not been declared")
                return null
            }
        }
        return finalExpression
    }
}