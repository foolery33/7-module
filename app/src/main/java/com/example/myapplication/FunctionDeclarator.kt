package com.example.myapplication

class FunctionDeclarator(name: String, arguments: String, numberOfFunction: Int): MainActivity() {

    var success = true
    var errors: MutableList<String> = mutableListOf()

    init {
        if (isCorrectName(name)) {
            if(intVariables.containsKey(name)) {
                this.success = false
                this.errors.add("Function can't be called like int variable $name")
            }
            else {
                if(intArrays.containsKey(name)) {
                    this.success = false
                    this.errors.add("Function can't be called like static array $name")
                }
                else {
                    var argumentList = StringWithCommas(arguments).splittedElements
                    if(areCorrectArguments(argumentList, name)) {
                        functions[name] = argumentList
                        functionReturnValues[numberOfFunction] = numberOfFunction
                        functionNumberOfCommands[name] = numberOfFunction
                    }
                }
            }
        }
    }

    fun isCorrectName(name: String): Boolean {
        return (Regex("[a-zA-Z]\\w*").matchEntire(name) != null)
    }

    fun areCorrectArguments(argumentList: MutableList<String>, name: String): Boolean {
        for (i in argumentList) {
            if(Regex("[a-zA-Z]\\w*").matchEntire(i) == null) {
                this.success = false
                this.errors.add("Incorrect name of argument $i in function $name")
                return false
            }
        }
        return true
    }

}