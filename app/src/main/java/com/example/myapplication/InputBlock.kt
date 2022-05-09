package com.example.myapplication

class InputBlock(name: String, value: String): MainActivity() {

    var success = true
    var errors = mutableListOf<String>()

    init {
        processData(name, value)
    }
    fun processData(name: String, value: String) {
        var names = StringWithCommas(name).splittedElements
        var values = StringWithCommas(value).splittedElements
        if(names.size != values.size) {
            this.errors.add("Number of variables doesn't match to number of values")
            this.success = false
        }
        else {
            for (i in names.indices) {
                var a = AssignmentOperation(names[i], values[i])
                if (!a.success) {
                    this.success = false
                    for (i in a.errors) {
                        this.errors.add(i)
                    }
                    break
                }
            }
        }
    }

}