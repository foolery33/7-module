package com.example.myapplication

import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.namespace.R

sealed class DataBlocks: MainActivity(){
    var flag: Boolean = true

    data class InitInt(var name: String = ""): DataBlocks()
    data class InitArray(var name: String = "", var len: String = ""): DataBlocks()
    data class InputEl(var name: String = ""): DataBlocks() {}
    data class OutputEl(var name: String = ""): DataBlocks(){}
    data class AssigmentEl(var el1: String = "", var el2: String = ""): DataBlocks()
    data class If(var el1: String = "", var el2: String = "", var choose: String = "",
                  var listIf: MutableList<DataBlocks> = mutableListOf()): DataBlocks(){
                      fun addElse(){
                          val begin = DataBlocks.Begin()
                          val end = DataBlocks.End()

                          MainActivity.programList.add(DataBlocks.Else())
                          MainActivity.programList.add(begin)
                          MainActivity.programList.add(end)

                          MainActivity.blockAdapter.notifyDataSetChanged()
                      }
                  }
    data class Else(var listElse: MutableList<DataBlocks> = mutableListOf(Begin(), End())): DataBlocks()
    data class Cycle(var el1: String = "", var el2: String = "", var choose: String = "",
                     var listCycle: MutableList<DataBlocks> = mutableListOf()): DataBlocks()
    data class Function(var name: String = "", var elem: String = "",
                        var listFun: MutableList<DataBlocks> = mutableListOf()): DataBlocks(){
        fun addReturn(){
            MainActivity.programList.add(DataBlocks.Return())

            MainActivity.blockAdapter.notifyDataSetChanged()
        }
                        }
    data class Return(var return_name: String = ""): DataBlocks()
    data class Begin(var text: String = ""): DataBlocks()
    data class End(var text: String = ""): DataBlocks()

    fun getCommands(blocks: MutableList<DataBlocks>, index: Int): MutableList<DataBlocks> {
        var commands = mutableListOf<DataBlocks>()
        var i = index
        if(blocks[i] is DataBlocks.Begin) {
            var counter = 1
            var j = i + 1
            while (counter != 0 && j < blocks.size) {
                if(blocks[j] is DataBlocks.Begin) {
                    counter++
                }
                else if(blocks[j] is DataBlocks.End) {
                    counter--;
                }
                commands.add(blocks[j])
                j++
            }
            j -= 1
        }
        else {
            // выдать ошибку и прекратить выполнение программы
        }
        if(commands[commands.size - 1] is DataBlocks.End) {
            commands.removeAt(commands.size - 1)
        }
        return commands
    }

    fun doProgram(block: DataBlocks, text: TextView, index: Int, blocks: MutableList<DataBlocks>) : String{
        var str: String = text.text.toString()
        when(block){
            is DataBlocks.InitInt -> {
                val a = IntVariable(block.name)

                if (!a.success){
                    for (j in a.errors){
                        str += (j + "\n")
                        outputString += (j + "\n")
                        flag = false
                    }
                }
            }
            is DataBlocks.InitArray -> {
                val a = StaticArray(block.name, block.len)

                if (!a.success){
                    for (j in a.errors){
                        str += (j + "\n")
                        outputString += (j + "\n")
                        flag = false
                    }
                }
            }
            is DataBlocks.OutputEl -> {
                val a = OutputBlock(block.name)

                if (!a.success){
                    for (j in a.errors){
                        str += (j + "\n")
                        outputString += (j + "\n")
                        flag = false
                    }
                }
                else {
                    str += (a.outputValue.toString() + "\n")
                    outputString += (a.outputValue.toString() + "\n")
                }
            }
            is DataBlocks.AssigmentEl -> {
                val a = AssignmentOperation(block.el1, block.el2)

                if (!a.success){
                    for (j in a.errors){
                        str += (j  + "\n")
                        outputString += (j + "\n")
                        flag = false
                    }
                }
            }
            is DataBlocks.If -> {
                val a = IfOperator(block.el1, block.choose, block.el2, getCommands(blocks, index + 1), text)
                previousIfResult = a.result

                if (!a.success){
                    for (j in a.errors){
                        str += (j + "\n")
                        outputString += (j + "\n")
                        flag = false
                    }
                }
            }
            is DataBlocks.InputEl -> {
                val a = InputBlock(block.name)

                if (!a.success){
                    for (j in a.errors){
                        str += (j + "\n")
                        outputString += (j + "\n")
                        flag = false
                    }
                }
            }
            is DataBlocks.Else -> {
                val a = ElseOperator(getCommands(blocks, index + 1), text)

                if (!a.success){
                    for (j in a.errors){
                        str += (j + "\n")
                        outputString += (j + "\n")
                        flag = false
                    }
                }
            }
            is DataBlocks.Cycle -> {
                val a = Cycle(block.el1, block.choose, block.el2, getCommands(blocks, index + 1), text)
            }
            //is DataBlocks.Function ->
            //is DataBlocks.Return ->
        }
        return str
    }

    fun processBlocks(blocks: MutableList<DataBlocks>) {

    }

}