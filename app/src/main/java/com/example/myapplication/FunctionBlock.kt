package com.example.myapplication

class FunctionBlock(name: String, arguments: String): MainActivity() {

    var name = ""
    var listOfArguments = listOf<String>()
    var localVariables = mutableMapOf<String, Int>()
    var localArrays = mutableMapOf<String, Array<Int> >()
    var returnValue = -1
    var success = true
    var errors = mutableListOf<String>()

    init {
        if(functions.containsKey(name)) {
            var openBracketsCounter = arguments.count { i -> i == '(' }
            var closeBracketsCounter = arguments.count { i -> i == ')' }
            if (openBracketsCounter != closeBracketsCounter) {
                this.success = false
                this.errors.add("Number of open brackets in arguments $arguments doesn't equal to number of close brackets")
            }
            else {
                this.name = name
                this.listOfArguments = StringWithCommas(arguments).splittedElements
                if (this.listOfArguments.size != functions[this.name]!!.size) {
                    this.success = false
                    this.errors.add("Number of provided arguments doesn't match to number of function ${this.name} arguments")
                }
                else {
                    makeLocalVariables()
                    if (this.success) {
                        val mainVariables = intVariables
                        val mainArrays = intArrays
                        intVariables = this.localVariables
                        intArrays = this.localArrays
                        processCommands(functionCommands[functionNumberOfCommands[this.name]!!.toInt()])
                        returnFlag = "default"
                        this.returnValue = functionReturnValues[functionNumberOfCommands[this.name]!!.toInt()]!!
                        intVariables = mainVariables
                        intArrays = mainArrays
                    }
                }
            }
        }
        else {
            this.success = false
            this.errors.add("There is no function called $name")
        }
    }

    fun makeLocalVariables() {
        for (i in functions[this.name]!!.indices) {
            if(intArrays.containsKey(listOfArguments[i])) {
                localArrays[listOfArguments[i]] = intArrays[listOfArguments[i]]!!
            }
            else {
                val argument = Expression(listOfArguments[i])
                if(argument.success) {
                    val valueOfArgument = argument.valueOfExpression.toInt()
                    localVariables[functions[this.name]!![i]] = valueOfArgument
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