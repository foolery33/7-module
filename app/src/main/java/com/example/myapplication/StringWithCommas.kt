package com.example.myapplication

class StringWithCommas(string: String) {

    var splittedElements: MutableList<String> = mutableListOf()

    init {
        this.splittedElements = splitElements(string)
    }

    fun splitElements(string: String): MutableList<String> {

        var splittedArguments: MutableList<String> = mutableListOf()
        var editedString = string

        var i = 0
        var currentArgument = ""
        while (i < editedString.length) {
            if(editedString[i] != ',') {
                if(editedString[i] != '(') {
                    currentArgument += editedString[i]
                }
                else {
                    var j = i
                    while (editedString[j] != ')' && j < editedString.length) {
                        currentArgument += editedString[j]
                        j++
                    }
                    if(j > i) {
                        i = j - 1
                    }
                    else {
                        i = j
                    }
                }
            }
            else {
                splittedArguments.add(currentArgument)
                currentArgument = ""
            }
            i++
        }
        if(currentArgument.isNotEmpty()) {
            splittedArguments.add(currentArgument)
        }

        return splittedArguments
    }

}