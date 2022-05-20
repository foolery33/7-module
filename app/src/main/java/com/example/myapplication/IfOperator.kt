package com.example.myapplication

import android.widget.TextView

class IfOperator(beforeOperator: String, operator: String, afterOperator: String, ifCommands: MutableList<DataBlocks>, text: TextView): MainActivity() {

    var success = true
    var result = false
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
            val result = isTrueComparison(expressionBeforeOperator.valueOfExpression.toInt(), operator, expressionAfterOperator.valueOfExpression.toInt())
            this.result = result
            if(this.result && this.success) {
                processBlocks(ifCommands, text)
            }
        }
    }


    fun isTrueComparison(first: Int, operator: String, second: Int): Boolean {
        when(operator) {
            ">" -> return (first > second)
            "<" -> return (first < second)
            "==" -> return (first == second)
            "!=" -> return (first != second)
            ">=" -> return (first >= second)
            "<=" -> return (first <= second)
        }
        this.errors.add("Incorrect operator $operator")
        this.success = false
        return false
    }

}