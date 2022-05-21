package com.example.myapplication

import android.widget.TextView

class FunctionBlock(name: String, arguments: String, text: TextView): MainActivity() {

    var name = ""
    var arguments = ""
    var textView = text
    var listOfArguments = listOf<String>()
    var localVariables = mutableMapOf<String, Int>()
    var localArrays = mutableMapOf<String, Array<Int> >()
    var returnValue = -1
    var success = true
    var errors = mutableListOf<String>()

    init {
        currentFunctionNumber++
        if(functionArguments.containsKey(name)) {
            var openBracketsCounter = arguments.count { i -> i == '(' }
            var closeBracketsCounter = arguments.count { i -> i == ')' }
            if (openBracketsCounter != closeBracketsCounter) {
                this.success = false
                this.errors.add("Number of open brackets in arguments $arguments doesn't equal to number of close brackets")
            }
            else {
                this.name = name.replace(" ", "")
                this.arguments = arguments.replace(" ", "")
                this.listOfArguments = StringWithCommas(this.arguments).splittedElements
                if (this.listOfArguments.size != functionArguments[this.name]!!.size) {
                    this.success = false
                    this.errors.add("Number of provided arguments doesn't equal to number of function ${this.name} arguments")
                }
                else {
                    makeLocalVariables()
                    if (this.success) {
                        val mainVariables = intVariables
                        val mainArrays = intArrays
                        intVariables = this.localVariables
                        intArrays = this.localArrays
                        processFunctionBlock(functionCommands[this.name]!!, text, this.name)
                        returnFlag = "default"
                        this.returnValue = functionReturnValues[this.name]!!
                        intVariables = mainVariables
                        intArrays = mainArrays
                    }
                }
            }
        }
        else {
            this.success = false
            this.errors.add("There is no function called $this.name")
        }
    }

    fun makeLocalVariables() {
        for (i in functionArguments[this.name]!!.indices) {
            if(intArrays.containsKey(listOfArguments[i])) {
                localArrays[listOfArguments[i]] = intArrays[listOfArguments[i]]!!
            }
            else {
                val argument = Expression(listOfArguments[i], textView)
                if(argument.success) {
                    val valueOfArgument = argument.valueOfExpression.toInt()
                    localVariables[functionArguments[this.name]!![i]] = valueOfArgument
                }
                else {
                    this.success = false
                    for (i in argument.errors) {
                        this.errors.add(i)
                    }
                }
            }
        }
    }

}