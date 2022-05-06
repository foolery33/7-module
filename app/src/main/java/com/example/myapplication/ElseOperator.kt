package com.example.myapplication

import com.example.myapplication.MainActivity.Companion.elseConditions
import com.example.myapplication.MainActivity.Companion.processCommands

class ElseOperator(beforeOperator: String, operator: String, afterOperator: String, numberOfElseCommands: String) {

    var operator = ""
    var beforeOperator = ""
    var afterOperator = ""
    var result = false
    var success = true
    var numberOfElseCommands = -1
    var expressionBeforeOperator = Expression(beforeOperator)
    var expressionAfterOperator = Expression(afterOperator)
    var errors = mutableListOf<String>()

    init {
        this.numberOfElseCommands = numberOfElseCommands.toInt()
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

            this.result = isFalseComparison(expressionBeforeOperator.valueOfExpression.toInt(), expressionAfterOperator.valueOfExpression.toInt())
            if(result && this.success) {
                processCommands(elseConditions[this.numberOfElseCommands])
            }
        }
    }


    fun isFalseComparison(first: Int, second: Int): Boolean {
        when(this.operator) {
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