package com.example.myapplication

class InputBlock(name: String): MainActivity() {

    var value = ""
    var success = true
    var errors = mutableListOf<String>()

    init {
        if(inputValues[name] != null) {
            this.value = inputValues[name]!!
            processData(name, this.value)
        }
        else {
            this.success = false
            this.errors.add("There are no such variable(-s) as $name")
        }
    }
    fun processData(name: String, value: String) {
        var names = name.split(",")
        var values = value.split(",")
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