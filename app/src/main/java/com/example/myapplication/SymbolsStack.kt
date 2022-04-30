package com.example.myapplication

class SymbolsStack {

    private var array = arrayOfNulls<Char>(255)
    private var position = 0

    fun push(item: Char?) {
        array[position++] = item
    }

    fun pop(): Char? {
        return array[--position]
    }

    fun comparePriority(op: Char): Boolean {
        return (opPriority(op) > opPriority(array[position-1]))
    }

    private fun opPriority(op: Char?): Int {
        return when (op) {
            '(' -> 0
            ')' -> 1
            '+' -> 2
            '-' -> 2
            '*' -> 3
            '/' -> 3
            '%' -> 3
            else -> -1
        }
    }

    fun isEmpty() = (position == 0)

}