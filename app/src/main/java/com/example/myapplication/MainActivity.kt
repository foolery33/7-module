package com.example.myapplication

import android.R.attr.data
import android.util.Log
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

open class MainActivity : AppCompatActivity() {

    companion object {

        @JvmField
        var intVariables: MutableMap<String, Int> = mutableMapOf()
        var intArrays: MutableMap<String, Array<Int> > = mutableMapOf()
        var functions: MutableMap<String, MutableList<String> > = mutableMapOf()

        var commands: MutableList<String> = mutableListOf()
        var ifConditions: MutableList<MutableList<String> > = mutableListOf()
        var elseConditions: MutableList<MutableList<String> > = mutableListOf()
        var cycleCommands: MutableList<MutableList<String> > = mutableListOf()
        var functionCommands: MutableList<MutableList<String> > = mutableListOf()
        var functionReturnValues: MutableMap<Int, Int> = mutableMapOf()
        var functionNumberOfCommands: MutableMap<String, Int> = mutableMapOf()

        var ifConditionsCounter = -1
        var elseConditionsCounter = -1
        var cycleCommandsCounter = -1
        var functionCommandsCounter = 0

        var returnFlag = "default"

        fun processCommands(commands: MutableList<String>) {
            for (i in commands.indices) {
                if(returnFlag == "return") {
                    break
                }
                var splitted = commands[i].split(" ")
                when (splitted[0]) {
                    "int" -> {
                        val a = IntVariable(splitted[1])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            exitProcess(0)
                        }
                    }
                    "assignment" -> {
                        val a = AssignmentOperation(splitted[1], splitted[2])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            exitProcess(0)
                        }
                    }
                    "input" -> {
                        val a = InputBlock(splitted[1], splitted[2])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            exitProcess(0)
                        }
                    }
                    "output" -> {
                        val a = OutputBlock(splitted[1])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            exitProcess(0)
                        }
                    }
                    "if" -> {
                        val a = IfOperator(splitted[1], splitted[2], splitted[3], splitted[4])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            exitProcess(0)
                        }
                    }
                    "else" -> {
                        val a = ElseOperator(splitted[1], splitted[2], splitted[3], splitted[4])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            exitProcess(0)
                        }
                    }
                    "array" -> {
                        val a = StaticArray(splitted[1], splitted[2])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            exitProcess(0)
                        }
                    }
                    "cycle" -> {
                        val a = Cycle(splitted[1], splitted[2], splitted[3], splitted[4])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            exitProcess(0)
                        }
                    }
                    "function" -> {
                        val a = FunctionBlock(splitted[1], splitted[2])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            exitProcess(0)
                        }
                    }
                    "return" -> {
                        val a = Expression(splitted[1])
                        if(!a.success) {
                            for (j in a.errors) {
                                println(j)
                            }
                            exitProcess(0)
                        }
                        else {
                            functionReturnValues[functionCommandsCounter] = a.valueOfExpression.toInt()
                            returnFlag = "return"
                            break
                        }
                    }
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        commands.add("int n")
        commands.add("array recurs 100")
        commands.add("int i")
        commands.add("cycle i < 100 0")
        cycleCommands.add(mutableListOf("assignment recurs[i] 0", "assignment i i+1"))
        commands.add("input n 65")
        functions["chislo"] = mutableListOf("n", "recurs")
        functionCommands.add(mutableListOf("if n == 1 0", "if n == 2 1", "if recurs[n-2] != 0 2", "if recurs[n-2] != 0 3", "if recurs[n-3] != 0 4", "if recurs[n-2] == 0 5"))
        functionNumberOfCommands["chislo"] = 0
        ifConditions.add(mutableListOf("assignment recurs[n-1] 0", "return 0"))
        ifConditions.add(mutableListOf("assignment recurs[n-1] 1", "return 1"))
        ifConditions.add(mutableListOf("if recurs[n-3] == 0 6"))
        ifConditions.add(mutableListOf("if recurs[n-3] != 0 7"))
        ifConditions.add(mutableListOf("if recurs[n-2] == 0 8"))
        ifConditions.add(mutableListOf("if recurs[n-3] == 0 9"))
        ifConditions.add(mutableListOf("assignment recurs[n-3] chislo(n-2,recurs)", "return recurs[n-2]+chislo(n-2,recurs)"))
        ifConditions.add(mutableListOf("return recurs[n-2]+recurs[n-3]"))
        ifConditions.add(mutableListOf("assignment recurs[n-2] chislo(n-1,recurs)", "return recurs[n-3]+chislo(n-1,recurs)"))
        ifConditions.add(mutableListOf("assignment recurs[n-2] chislo(n-1,recurs)", "assignment recurs[n-3] chislo(n-2,recurs)", "return chislo(n-1,recurs)+chislo(n-2,recurs)"))
        commands.add("output chislo(n,recurs)")
        processCommands(commands)

    }

}