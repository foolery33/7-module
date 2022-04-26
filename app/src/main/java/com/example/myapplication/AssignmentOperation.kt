package com.example.myapplication

class AssignmentOperation(nameOfFirstVariable: String, expression: String): MainActivity() {

    var nameOfFirstVariable: String = ""
    var expression: String = ""

    var expressionAfterOperator = Expression(expression)

    init {
        if(expressionAfterOperator.errors.size != 0) {
            for (i in expressionAfterOperator.errors) {
                println(i)
            }
        }
        else {
            if(expressionAfterOperator.correctExpression != null) {
                this.expression = expressionAfterOperator.correctExpression as String
                println(this.expression)
                println(this.expressionAfterOperator.valueOfExpression)
            }
            else {
                success = false
            }
            if(variables.containsKey(nameOfFirstVariable)) {
                this.nameOfFirstVariable = nameOfFirstVariable
                addToMap(this.nameOfFirstVariable, expressionAfterOperator.valueOfExpression)
            }
            else {
                println("Variable $nameOfFirstVariable has not been declared")
                success = false
            }
        }
    }

}