package com.example.myapplication

class ElseOperator(beforeOperator: String, operator: String, afterOperator: String, numberOfElseCommands: String) {

    var success = true
    var expressionBeforeOperator = Expression(beforeOperator)
    var expressionAfterOperator = Expression(afterOperator)
    var errors = mutableListOf<String>()

    init {
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
            val result = isFalseComparison(expressionBeforeOperator.valueOfExpression.toInt(), operator, expressionAfterOperator.valueOfExpression.toInt())
            if(result && this.success) {
                //processCommands(elseConditions[numberOfElseCommands.toInt()])
            }
        }
    }


    fun isFalseComparison(first: Int, operator: String, second: Int): Boolean {
        when(operator) {
            ">" -> return (first <= second)
            "<" -> return (first >= second)
            "==" -> return (first != second)
            "!=" -> return (first == second)
            ">=" -> return (first < second)
            "<=" -> return (first > second)
        }
        this.errors.add("Incorrect operator $operator")
        this.success = false
        return false
    }

}