package com.example.myapplication

class Stack {

    private var array = arrayOfNulls<String>(255)
    private var position = 0

    fun push(item: String) {
        array[position++] = item
    }

    fun pop(): String {
        return if(!isEmpty()) {
            array[--position].toString()
        } else {
            ""
        }
    }

    fun isEmpty() = (position == 0)
}