package com.example.myapplication

import android.provider.ContactsContract
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.example.namespace.R
import java.text.FieldPosition

sealed class DataBlocks{
    var flag: Boolean = true

    data class InitInt(var name: String = ""): DataBlocks()
    data class InitArray(var name: String = "", var len: String = ""): DataBlocks()
    data class InputEl(var name: String = ""): DataBlocks() {}
    data class OutputEl(var name: String = ""): DataBlocks(){}
    data class AssigmentEl(var el1: String = "", var el2: String = ""): DataBlocks(){}
    data class If(var el1: String = "", var el2: String = "", var choose: String = "",
                  var listIf: MutableList<DataBlocks> = mutableListOf(), ): DataBlocks() {
        fun addElse() {
            val endblock = End()
            val begin = Begin()
            MainActivity.programList.add(Else())
            MainActivity.programList.add(begin)
            MainActivity.programList.add(endblock)
            MainActivity.blockAdapter.notifyDataSetChanged()
        }
    }
    data class Else(var listElse: MutableList<DataBlocks> = mutableListOf()): DataBlocks()
    data class Cycle(var el1: String = "", var el2: String = "", var choose: String = "",
                     var listCycle: MutableList<DataBlocks> = mutableListOf()): DataBlocks()
    data class Function(var name: String = "", var elem: String = "",
                        var listFun: MutableList<DataBlocks> = mutableListOf()): DataBlocks() {
        fun addReturn() {
            MainActivity.programList.add(Return())
            MainActivity.blockAdapter.notifyDataSetChanged()
        }

    }
    data class Return(var return_name: String = ""): DataBlocks()
    data class Begin(var position: Int = -1): DataBlocks()
    data class End(var position: Int = -1): DataBlocks()

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
                val a = IfOperator(block.el1, block.choose, block.el2, block.listIf.size - 2)

                if (!a.success){
                    for (j in a.errors){
                        str += (j + "\n")
                        flag = false
                    }
                }
            }
            /*is DataBlocks.InputEl -> {
                val a = InputBlock(block.name,)

                if (!a.success){
                    for (j in a.errors){
                        str += (j + "\n")
                        flag = false
                    }
                }
            }*/
            //is DataBlocks.Else -> ElseOperator()
            //is DataBlocks.Cycle -> Cyc
            //is DataBlocks.Function ->
            //is DataBlocks.Return ->
        }
        return str
    }
}