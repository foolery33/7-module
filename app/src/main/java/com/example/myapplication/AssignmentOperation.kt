package com.example.myapplication

import android.widget.TextView

class AssignmentOperation(nameOfVariable: String, expression: String, text: TextView) :
    MainActivity() {

    var nameOfVariable = ""
    var expression = ""
    var success = true
    var errors = mutableListOf<String>()

    var expressionAfterOperator = Expression(expression, text)

    init {
        this.nameOfVariable = nameOfVariable.replace(" ", "")
        this.expression = expression.replace(" ", "")
        if (!expressionAfterOperator.success) {
            this.success = false
            for (i in expressionAfterOperator.errors) {
                this.errors.add(i)
            }
        } else {
            if (expressionAfterOperator.correctExpression != null) {

                // Переменная - не элемент массива
                if (intVariables.containsKey(this.nameOfVariable)) {
                    addToMap(this.nameOfVariable, expressionAfterOperator.valueOfExpression.toInt())
                }
                // Переменная - элемент массива
                else if (isArrayVariable(this.nameOfVariable)) {
                    var nameOfArray = ""
                    for (i in this.nameOfVariable) {
                        if (i != '[') {
                            nameOfArray += i
                        } else {
                            break
                        }
                    }
                    if (intArrays.containsKey(nameOfArray)) {
                        // Ищем индекс элемента в массиве
                        var indexExpression = ""
                        var flag = false
                        var counter = 0
                        for (i in this.nameOfVariable) {
                            if (i == ']') {
                                counter--
                                if (counter == 0) {
                                    break
                                }
                            }
                            if (flag) {
                                indexExpression += i
                            }
                            if (i == '[') {
                                counter++
                                flag = true
                            }
                        }
                        var index = Expression(indexExpression, text)
                        if (index.valueOfExpression.toInt() >= intArrays[nameOfArray]!!.size) {
                            this.success = false
                            this.errors.add("Index $indexExpression out of range of array $nameOfArray")
                        } else {
                            intArrays[nameOfArray]!![index.valueOfExpression.toInt()] =
                                expressionAfterOperator.valueOfExpression.toInt()
                        }
                    } else {
                        this.success = false
                        this.errors.add("Array $nameOfArray has not been declared")
                    }
                } else {
                    this.errors.add("Variable ${this.nameOfVariable} has not been declared")
                    this.success = false
                }
            } else {
                this.success = false
                this.errors.add("Cannot read expression ${this.expression}")
            }
        }
    }

    fun isArrayVariable(nameOfVariable: String): Boolean {
        return Regex("[a-zA-Z]\\w*\\[.+]").matchEntire(this.nameOfVariable) != null
    }

    fun addToMap(name: String, value: Int) {
        if (intVariables.containsKey(name)) {
            intVariables[name] = value
        } else {
            intVariables[name] = value
        }
    }

}