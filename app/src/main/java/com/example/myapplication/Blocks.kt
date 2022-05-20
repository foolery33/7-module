package com.example.myapplication

import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.namespace.R

sealed class DataBlocks{
    var flag: Boolean = true
    /*data class OneEdit(val title: String, val name: EditText) : DataBlocks()
    data class TwoEdits(val title: String,
                        val elem1: EditText,
                        val text1: String,
                        val elem2: EditText,
                        val text2: String) : DataBlocks()
    data class NoEdits(val title: String) : DataBlocks()*/

    data class InitInt(var name: String = ""): DataBlocks()
    data class InitArray(var name: String = "", var len: String = ""): DataBlocks()
    data class InputEl(var name: String = ""): DataBlocks() {}
    data class OutputEl(var name: String = ""): DataBlocks(){}
    data class AssigmentEl(var el1: String = "", var el2: String = ""): DataBlocks(){}
    data class If(var el1: String = "", var el2: String = "", var choose: String = "", var begin: Int = -1, var end: Int = -1, var listIf: MutableList<DataBlocks> = mutableListOf(Begin(), End())): DataBlocks()
    data class Else(var begin: Int, var end: Int,var listElse: MutableList<DataBlocks> = mutableListOf(Begin(), End())): DataBlocks()
    data class Cycle(var el1: String = "", var el2: String = "", var choose: String = "", var begin: Int = -1, var end: Int = -1, var listCycle: MutableList<DataBlocks> = mutableListOf(Begin(), End())): DataBlocks()
    data class Function(var name: String = "", var elem: String = "", var begin: Int = -1, var end: Int = -1, var listFun: MutableList<DataBlocks> = mutableListOf(Begin(), End())): DataBlocks()
    data class Return(var begin: Int = -1, var end: Int = -1): DataBlocks()
    data class Begin(var text: String = ""): DataBlocks()
    data class End(var text: String = ""): DataBlocks()

    fun doProgram(block: DataBlocks, text: TextView) : String{
        var str: String = text.text.toString()
        when(block){
            is DataBlocks.InitInt -> {
                val a = IntVariable(block.name)

                if (!a.success){
                    for (j in a.errors){
                        str += (j + "\n")
                        flag = false
                    }
                }
            }
            is DataBlocks.InitArray -> {
                val a = StaticArray(block.name, block.len)

                if (!a.success){
                    for (j in a.errors){
                        str += (j + "\n")
                        flag = false
                    }
                }
            }
            is DataBlocks.OutputEl -> {
                val a = OutputBlock(block.name)

                if (!a.success){
                    for (j in a.errors){
                        str += (j + "\n")
                        flag = false
                    }
                }
                else {
                    str += (a.outputValue + "\n")
                }
            }
            is DataBlocks.AssigmentEl -> {
                val a = AssignmentOperation(block.el1, block.el2)

                if (!a.success){
                    for (j in a.errors){
                        str += (j  + "\n")
                        flag = false
                    }
                }
            }
            is DataBlocks.If -> {
                val a = IfOperator(block.el1, block.choose, block.el2, (block.listIf.size - 2).toString())

                if (!a.success){
                    for (j in a.errors){
                        str += (j + "\n")
                        flag = false
                    }
                }
            }
            is DataBlocks.InputEl -> {
                val a = InputBlock(block.name)

                if (!a.success){
                    for (j in a.errors){
                        str += (j + "\n")
                        flag = false
                    }
                }
            }
            //is DataBlocks.Else -> ElseOperator()
            //is DataBlocks.Cycle -> Cyc
            //is DataBlocks.Function ->
            //is DataBlocks.Return ->
        }
        return str
    }
}