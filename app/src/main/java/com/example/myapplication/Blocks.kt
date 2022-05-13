package com.example.myapplication

import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

sealed class DataBlocks{
    /*data class OneEdit(val title: String, val name: EditText) : DataBlocks()
    data class TwoEdits(val title: String,
                        val elem1: EditText,
                        val text1: String,
                        val elem2: EditText,
                        val text2: String) : DataBlocks()
    data class NoEdits(val title: String) : DataBlocks()*/

    data class InitInt(val name: String): DataBlocks()
    data class InitArray(val name: String, val len: String): DataBlocks()
    data class InputEl(val name: String): DataBlocks()
    data class OutputEl(val name: String): DataBlocks()
    data class IfElse(val el1: String, val el2: String, val choose: Spinner): DataBlocks()
    data class Cycle(val el1: String, val el2: String, val choose: Spinner): DataBlocks()
    data class Function(val name: String, val elem: String): DataBlocks()
    data class Assigment(val el1: String, val el2: String): DataBlocks()
}