package com.example.myapplication

import android.widget.TextView

class ElseOperator(elseCommands: MutableList<DataBlocks>, text: TextView) : MainActivity() {

    var success = true
    var errors = mutableListOf<String>()

    init {
        processBlocks(elseCommands, text)
    }

}