package com.example.myapplication

class Cycle(beforeOperator: String, operator: String, afterOperator: String, numberOfCycleCommands: String): MainActivity() {

    var beforeOperator = ""
    var operator = ""
    var afterOperator = ""
    var numberOfCycleCommands = -1
    var success = true
    var errors = mutableListOf<String>()
    var beforeOperatorExpression = Expression(beforeOperator)
    var afterOperatorExpression = Expression(afterOperator)

    init {
        this.numberOfCycleCommands = numberOfCycleCommands.toInt()

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
            this.beforeOperator = beforeOperator
            this.operator = operator
            this.afterOperator = afterOperator

            while (true) {
                var currentBeforeOperatorExpression = Expression(beforeOperator)
                var currentAfterOperatorExpression = Expression(afterOperator)
                if(currentBeforeOperatorExpression.success && currentAfterOperatorExpression.success) {
                    if (isTrueComparison(currentBeforeOperatorExpression.valueOfExpression.toInt(), this.operator, currentAfterOperatorExpression.valueOfExpression.toInt())) {
                        processCommands(cycleCommands[this.numberOfCycleCommands])
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