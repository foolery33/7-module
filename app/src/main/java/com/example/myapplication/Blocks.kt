package com.example.myapplication

import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.namespace.R

sealed class DataBlocks{
    /*data class OneEdit(val title: String, val name: EditText) : DataBlocks()
    data class TwoEdits(val title: String,
                        val elem1: EditText,
                        val text1: String,
                        val elem2: EditText,
                        val text2: String) : DataBlocks()
    data class NoEdits(val title: String) : DataBlocks()*/

    data class InitInt(var name: String = ""): DataBlocks()
    data class InitArray(var name: String = "", var len: String = ""): DataBlocks()
    data class InputEl(var name: String = ""): DataBlocks()
    data class OutputEl(var name: String = ""): DataBlocks()
    data class If(var el1: String = "", var el2: String = "", var choose: String = "", var begin: Int = -1, var end: Int = -1): DataBlocks()
    data class Else(var begin: Int, var end: Int): DataBlocks()
    data class Cycle(var el1: String = "", var el2: String = "", var choose: String = "", var begin: Int = -1, var end: Int = -1): DataBlocks()
    data class Function(var name: String = "", var elem: String = "", var begin: Int = -1, var end: Int = -1): DataBlocks()
    data class Return(var begin: Int = -1, var end: Int = -1): DataBlocks()
    data class AssigmentEl(var el1: String = "", var el2: String = ""): DataBlocks()
    data class Begin(var text: String = ""): DataBlocks()
    data class End(var text: String = ""): DataBlocks()
}