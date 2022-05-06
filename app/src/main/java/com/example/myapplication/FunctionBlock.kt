package com.example.myapplication

class FunctionBlock(name: String, arguments: String, returnValue: String, numberOfFunctionCommands: String): MainActivity() {

    var name = ""
    var listOfArguments = listOf<String>()
    var localVariables = mutableMapOf<String, Int>()
    var localArrays = mutableMapOf<String, Array<Int> >()
    var numberOfFunctionCommands = -1
    var success = true
    var errors = mutableListOf<String>()

    init {
        if(functions.containsKey(name)) {
            this.numberOfFunctionCommands = numberOfFunctionCommands.toInt()
            this.name = name
            this.listOfArguments = arguments.split(",")
            if(this.listOfArguments.size != functions[this.name]!!.size) {
                this.success = false
                this.errors.add("Number of provided arguments doesn't match to number of function ${this.name} arguments")
            }
            else {
                /*if(flag == "function") {

                }*/
                makeLocalVariables()
                if(this.success) {
                    processCommands(functionCommands[this.numberOfFunctionCommands])
                    var returnVal = Expression(returnValue).valueOfExpression.toInt()
                }
            }
        }
    }

    fun makeLocalVariables() {
        for (i in functions[this.name]!!.indices) {
            val argument = Expression(listOfArguments[i])
            if(this.success) {
                val valueOfArgument = argument.valueOfExpression.toInt()
                localVariables[listOfArguments[i]] = valueOfArgument
            }
            else {
                break
            }
        }
    }

}