package com.example.myapplication

class Cycle(beforeOperator: String, operator: String, afterOperator: String, cycleCommands: MutableList<DataBlocks>): MainActivity() {

    var success = true
    var errors = mutableListOf<String>()
    var beforeOperatorExpression = Expression(beforeOperator)
    var afterOperatorExpression = Expression(afterOperator)

    init {

        if(!beforeOperatorExpression.success) {
            this.success = false
            for (i in beforeOperatorExpression.errors) {
                this.errors.add(i)
            }
        }
        if(!afterOperatorExpression.success) {
            this.success = false
            for (i in beforeOperatorExpression.errors) {
                this.errors.add(i)
            }
        }

        else {
            while (true) {
                val currentBeforeOperatorExpression = Expression(beforeOperator)
                val currentAfterOperatorExpression = Expression(afterOperator)
                if(currentBeforeOperatorExpression.success && currentAfterOperatorExpression.success) {
                    if (isTrueComparison(currentBeforeOperatorExpression.valueOfExpression.toInt(), operator, currentAfterOperatorExpression.valueOfExpression.toInt())) {
                        //processBlocks(cycleCommands)
                    }
                    else {
                        break
                    }
                }
                else {
                    this.success = false
                    for (i in currentBeforeOperatorExpression.errors) {
                        this.errors.add(i)
                    }
                    for (i in currentAfterOperatorExpression.errors) {
                        this.errors.add(i)
                    }
                    break
                }
            }
        }
    }

    fun isTrueComparison(beforeOperatorValue: Int, operator: String, afterOperatorValue: Int): Boolean {
        when (operator) {
            ">" -> (return beforeOperatorValue > afterOperatorValue)
            "<" -> (return beforeOperatorValue < afterOperatorValue)
            "==" -> (return beforeOperatorValue == afterOperatorValue)
            "!=" -> (return beforeOperatorValue != afterOperatorValue)
            ">=" -> (return beforeOperatorValue >= afterOperatorValue)
            "<=" -> (return beforeOperatorValue <= afterOperatorValue)
        }
        this.success = false
        this.errors.add("Incorrect operator $operator")
        return false
    }

}