package com.example.myapplication

class ElseOperator(beforeOperator: String, operator: String, afterOperator: String, numberOfElseCommands: Int) {
    var operator = ""
    var beforeOperator = ""
    var afterOperator = ""
    var result = false
    var success = true
    var numberOfElseCommands = 0
    var expressionBeforeOperator = Expression(beforeOperator)
    var expressionAfterOperator = Expression(afterOperator)
    var errors = mutableListOf<String>()

    init {
        this.numberOfElseCommands = numberOfElseCommands
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

            this.result = isFalseComparison()
            if(result) {
                /* processOperations(ifConditions[numberOfElseCommands]) */
            }
        }
    }


    fun isFalseComparison(): Boolean {
        when(this.operator) {
            ">" -> return expressionBeforeOperator.valueOfExpression <= expressionAfterOperator.valueOfExpression
            "<" -> return expressionBeforeOperator.valueOfExpression >= expressionAfterOperator.valueOfExpression
            "==" -> return expressionBeforeOperator.valueOfExpression != expressionAfterOperator.valueOfExpression
            "!=" -> return expressionBeforeOperator.valueOfExpression == expressionAfterOperator.valueOfExpression
            ">=" -> return expressionBeforeOperator.valueOfExpression < expressionAfterOperator.valueOfExpression
            "<=" -> return expressionBeforeOperator.valueOfExpression > expressionAfterOperator.valueOfExpression
        }
        this.errors.add("Incorrect operator $operator")
        this.success = false
        return false
    }
}