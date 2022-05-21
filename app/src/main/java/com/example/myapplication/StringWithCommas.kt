package com.example.myapplication

class StringWithCommas(string: String): MainActivity() {

    var string = ""
    var splittedElements: MutableList<String> = mutableListOf()

    init {
        this.string = string.replace(" ", "")
        this.splittedElements = splitElements(this.string)
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
                    currentArgument += editedString[i]
                    i++
                    var counter = 1
                    var j = i
                    while (counter != 0 && j < editedString.length) {
                        if(editedString[j] == '(') {
                            counter++
                        }
                        if(editedString[j] == ')') {
                            counter--
                        }
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