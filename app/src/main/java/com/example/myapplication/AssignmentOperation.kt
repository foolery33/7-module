package com.example.myapplication

class AssignmentOperation(nameOfVariable: String, expression: String): MainActivity() {

    var nameOfVariable: String = ""
    var expression: String = ""
    var success = true
    var errors = mutableListOf<String>()

    var expressionAfterOperator = Expression(expression)

    init {
        if(!expressionAfterOperator.success) {
            this.success = false
            for (i in expressionAfterOperator.errors) {
                this.errors.add(i)
            }
        }
        else {
            if(expressionAfterOperator.correctExpression != null) {
                this.expression = expressionAfterOperator.correctExpression as String

                // Переменная - не элемент массива
                if(intVariables.containsKey(nameOfVariable)) {
                    this.nameOfVariable = nameOfVariable
                    addToMap(this.nameOfVariable, expressionAfterOperator.valueOfExpression.toInt())
                }
                // Переменная - элемент массива
                else if(isArrayVariable(nameOfVariable)) {
                    var nameOfArray = ""
                    for (i in nameOfVariable) {
                        if(i != '[') {
                            nameOfArray += i
                        }
                        else {
                            break
                        }
                    }
                    if(intArrays.containsKey(nameOfArray)) {
                        // Ищем индекс элемента в массиве
                        var indexExpression = ""
                        var flag = false
                        var counter = 0
                        for (i in nameOfVariable) {
                            if(i == ']') {
                                counter--
                                if(counter == 0) {
                                    break
                                }
                            }
                            if(flag) {
                                indexExpression += i
                            }
                            if(i == '[') {
                                counter++
                                flag = true
                            }
                        }
                        var index = Expression(indexExpression)
                        intArrays[nameOfArray]!![index.valueOfExpression.toInt()] = expressionAfterOperator.valueOfExpression.toInt()
                    }
                    else {
                        this.success = false
                        this.errors.add("Array $nameOfArray has not been declared")
                    }
                }
                else {
                    this.errors.add("Variable $nameOfVariable has not been declared")
                    this.success = false
                }
            }
            else {
                this.success = false
                this.errors.add("Cannot read expression $expression")
            }
        }
    }

    fun isArrayVariable(nameOfVariable: String): Boolean {
        return Regex("[a-zA-Z]\\w*\\[.+]").matchEntire(nameOfVariable) != null
    }

    fun addToMap(name: String, value: Int) {
        if(intVariables.containsKey(name)) {
            intVariables[name] = value
        }
        else {
            intVariables[name] = value
        }
    }

}