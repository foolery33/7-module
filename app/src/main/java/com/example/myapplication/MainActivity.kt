package com.example.myapplication

import android.R.attr.data
import android.util.Log
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class MainActivity : AppCompatActivity() {

    companion object {

        @JvmField
        var intVariables = mutableMapOf<String, Int>()
        //var stringVariables = mutableMapOf<String, String>()
        var intArrays = mutableMapOf<String, Array<Int>>()
        var functions = mutableMapOf<String, MutableList<String>>()

        var commands: MutableList<String> = mutableListOf()
        var ifConditions: MutableList<MutableList<String> > = mutableListOf()
        var elseConditions: MutableList<MutableList<String> > = mutableListOf()
        var cycleCommands: MutableList<MutableList<String> > = mutableListOf()
        var functionCommands: MutableList<MutableList<String> > = mutableListOf()

        var ifConditionsCounter = -1
        var elseConditionsCounter = -1
        var cycleCommandsCounter = -1

        fun processCommands(commands: MutableList<String>) {
            for (i in commands.indices) {
                var splitted = commands[i].split(" ")
                when (splitted[0]) {
                    "int" -> {
                        val a = IntVariable(splitted[1])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            break
                        }
                    }
                    "assignment" -> {
                        val a = AssignmentOperation(splitted[1], splitted[2])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            break
                        }
                    }
                    "input" -> {
                        val a = InputBlock(splitted[1], splitted[2])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            break
                        }
                    }
                    "output" -> {
                        val a = OutputBlock(splitted[1])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            break
                        }
                    }
                    "if" -> {
                        val a = IfOperator(splitted[1], splitted[2], splitted[3], splitted[4])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            break
                        }
                    }
                    "else" -> {
                        val a = ElseOperator(splitted[1], splitted[2], splitted[3], splitted[4])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            break
                        }
                    }
                    "array" -> {
                        val a = StaticArray(splitted[1], splitted[2])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            break
                        }
                    }
                    "cycle" -> {
                        val a = Cycle(splitted[1], splitted[2], splitted[3], splitted[4])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            break
                        }
                    }
                    "function" -> {
                        //val a = FunctionBlock()
                    }
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*commands.add("int i,j,k")
    commands.add("assignment i 0")
    commands.add("assignment j 0")
    commands.add("assignment k 0")
    commands.add("array a 10")
    commands.add("input a[0],a[1],a[2],a[a[2]+1],a[4],a[5],a[6],a[7],a[8],a[9] 10,5,2,3,6,8,4,1,7,9")
    commands.add("cycle i < 10 0")

    cycleCommands.add(mutableListOf("assignment j i+1", "cycle j < 10 1", "assignment i i+1"))
    commands.add("cycle j < 10 1")

    cycleCommands.add(mutableListOf("if a[i] > a[j] 0", "assignment j j+1"))
    ifConditions.add(mutableListOf("int t", "assignment t a[i]", "assignment a[i] a[j]", "assignment a[j] t"))

    commands.add("cycle k < 10 2")
    cycleCommands.add(mutableListOf("output a[k]", "assignment k k+1"))

    processCommands(commands)*/
        /*functions["sum"] = mutableListOf("a", "b")
        functionCommands.add(mutableListOf("int c", "assignment c a+b"))
        FunctionBlock("sum", "0,1", "c","0")*/

        commands.add("int n,i")
        commands.add("array a 4")
        commands.add("input n 8")
        commands.add("if n == 0 0")
        commands.add("else n == 0 0")
        ifConditions.add(mutableListOf("output 0"))
        elseConditions.add(mutableListOf("cycle n != 0 0", "assignment i 3", "cycle i >= 0 1"))
        cycleCommands.add(mutableListOf("assignment a[i] n%2", "assignment n n/2", "assignment i i+1"))
        cycleCommands.add(mutableListOf("output a[i]", "assignment i i-1"))
        processCommands(commands)
    }

}