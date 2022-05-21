package com.example.myapplication

import android.widget.TextView

class Cycle(
    beforeOperator: String,
    operator: String,
    afterOperator: String,
    cycleCommands: MutableList<DataBlocks>,
    text: TextView
) : MainActivity() {

    var beforeOperator = ""
    var operator = ""
    var afterOperator = ""
    var success = true
    var errors = mutableListOf<String>()
    var beforeOperatorExpression = Expression(beforeOperator, text)
    var afterOperatorExpression = Expression(afterOperator, text)

    init {
        this.beforeOperator = beforeOperator.replace(" ", "")
        this.operator = operator.replace(" ", "")
        this.afterOperator = afterOperator.replace(" ", "")
        if (!beforeOperatorExpression.success) {
            this.success = false
            for (i in beforeOperatorExpression.errors) {
                this.errors.add(i)
            }
        }
        if (!afterOperatorExpression.success) {
            this.success = false
            for (i in beforeOperatorExpression.errors) {
                this.errors.add(i)
            }
        } else {
            while (true) {
                val currentBeforeOperatorExpression = Expression(this.beforeOperator, text)
                val currentAfterOperatorExpression = Expression(this.afterOperator, text)
                if (currentBeforeOperatorExpression.success && currentAfterOperatorExpression.success) {
                    if (isTrueComparison(
                            currentBeforeOperatorExpression.valueOfExpression.toInt(),
                            this.operator,
                            currentAfterOperatorExpression.valueOfExpression.toInt()
                        )
                    ) {
                        processBlocks(cycleCommands, text)
                    } else {
                        break
                    }
                } else {
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

    fun isTrueComparison(
        beforeOperatorValue: Int,
        operator: String,
        afterOperatorValue: Int
    ): Boolean {
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