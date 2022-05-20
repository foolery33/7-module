package com.example.myapplication

class OutputBlock(name: String): MainActivity() {

    var errors = mutableListOf<String>()
    var success = true
    var outputValue = mutableListOf<String>()

    init {
        var elements = StringWithCommas(name).splittedElements
        for (i in elements) {
            var elementExpression = Expression(i)
            if(elementExpression.success) {
                this.outputValue.add(elementExpression.valueOfExpression)
                print("${elementExpression.valueOfExpression} ")
            }
            else {
                this.success = false
                for (j in elementExpression.errors) {
                    this.errors.add(j)
                }
            }
        }
    }

}