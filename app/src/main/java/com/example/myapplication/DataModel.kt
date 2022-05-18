package com.example.myapplication

import android.provider.SyncStateContract

data class ParentData(
    val parentTitle: String? = null,
    var type: Int = Constants.PARENT,
    var subList: MutableList<ChildData> = ArrayList(),
    var isExpanded: Boolean = false,
    val nameBlock: String = ""
)

object Constants{
    const val PARENT = 0
    const val CHILD = 1
}

data class ChildData(val childTitle: String, val nameBlock: String)