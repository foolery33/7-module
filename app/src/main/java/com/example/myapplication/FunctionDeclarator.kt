package com.example.myapplication

import android.widget.TextView

class FunctionDeclarator(name: String, arguments: String, blocks: MutableList<DataBlocks>) :
    MainActivity() {

    var name = ""
    var arguments = ""
    var success = true
    var errors: MutableList<String> = mutableListOf()

    init {
        if (isCorrectName(name)) {
            if (intVariables.containsKey(name)) {
                this.success = false
                this.errors.add("Function can't be called like int variable $name")
            } else {
                if (intArrays.containsKey(name)) {
                    this.success = false
                    this.errors.add("Function can't be called like static array $name")
                } else {
                    this.name = name.replace(" ", "")
                    this.arguments = arguments.replace(" ", "")
                    var argumentList = StringWithCommas(this.arguments).splittedElements
                    if (areCorrectArguments(argumentList, this.name)) {
                        functionArguments[this.name] = argumentList
                        functionCommands[this.name] = blocks
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
            if (Regex("[a-zA-Z]\\w*").matchEntire(i) == null) {
                this.success = false
                this.errors.add("Incorrect name of argument $i in function $name")
                return false
            }
        }
        return true
    }

}