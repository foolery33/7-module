package com.example.myapplication

interface HasStringId {
    val id: String
    override fun equals(other: Any?): Boolean
}