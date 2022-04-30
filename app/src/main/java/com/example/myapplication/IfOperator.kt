package com.example.myapplication

class IfOperator(beforeOperator: String, operator: String, afterOperator: String, numberOfIfCommands: Int): MainActivity() {

    var operator = ""
    var beforeOperator = ""
    var afterOperator = ""
    var result = false
    var success = true
    var numberOfIfCommands = 0
    var expressionBeforeOperator = Expression(beforeOperator)
    var expressionAfterOperator = Expression(afterOperator)
    var errors = mutableListOf<String>()

    init {
        this.numberOfIfCommands = numberOfIfCommands
        if(expressionBeforeOperator.errors.size != 0) {
            for (i in expressionBeforeOperator.errors.indices) {
                this.errors.add(expressionBeforeOperator.errors[i])
            }
        }
        if(expressionAfterOperator.errors.size != 0) {
            for (i in expressionAfterOperator.errors.indices) {
                this.errors.add(expressionAfterOperator.errors[i])
            }
        }
        else {
            this.beforeOperator = beforeOperator
            this.operator = operator
            this.afterOperator = afterOperator

            this.result = isTrueComparison()
            if(result) {
                /* processOperations(ifConditions[numberOfIfCommands]) */
            }
        }
    }


    fun isTrueComparison(): Boolean {
        when(this.operator) {
            ">" -> return expressionBeforeOperator.valueOfExpression > expressionAfterOperator.valueOfExpression
            "<" -> return expressionBeforeOperator.valueOfExpression < expressionAfterOperator.valueOfExpression
            "==" -> return expressionBeforeOperator.valueOfExpression == expressionAfterOperator.valueOfExpression
            "!=" -> return expressionBeforeOperator.valueOfExpression != expressionAfterOperator.valueOfExpression
            ">=" -> return expressionBeforeOperator.valueOfExpression >= expressionAfterOperator.valueOfExpression
            "<=" -> return expressionBeforeOperator.valueOfExpression <= expressionAfterOperator.valueOfExpression
        }
        this.errors.add("Incorrect operator $operator")
        this.success = false
        return false
    }
}