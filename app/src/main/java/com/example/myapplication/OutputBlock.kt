package com.example.myapplication

import android.widget.TextView

class OutputBlock(name: String, text: TextView): MainActivity() {

    var name = ""
    var errors = mutableListOf<String>()
    var success = true
    var outputValue = mutableListOf<String>()

    init {
        this.name = name.replace(" ", "")
        var elements = StringWithCommas(this.name).splittedElements
        for (i in elements) {
            var elementExpression = Expression(i, text)
            if(elementExpression.success) {
                this.outputValue.add(elementExpression.valueOfExpression)
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