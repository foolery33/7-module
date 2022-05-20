package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.namespace.R


open class BlockMenuActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_blocksmenu)

        val BackButton: ImageButton = findViewById(R.id.return_block)
        val HelpButton: ImageButton = findViewById(R.id.help_blocks)

        val listData: MutableList<ParentData> = ArrayList()

        val parentData: Array<String> = arrayOf("Объявления", "Ввод/Вывод", "Другие блоки")
        val childNotification: MutableList<ChildData> = mutableListOf(ChildData("Целочисленной переменной", "int"),
            ChildData("Массива", "array"))
        val childInOutput: MutableList<ChildData> = mutableListOf(ChildData("Ввод", "input"),
            ChildData("Вывод", "output"))
        val childOtherBlocks: MutableList<ChildData> = mutableListOf(ChildData("Циклы", "cycle"),
            ChildData("Функция", "function"), ChildData("Логика", "if"),
            ChildData("Присваивание", "assigment"))
        /*val childWithoutParent: MutableList<ChildData> = mutableListOf(ChildData("Циклы"),
            ChildData("Функция"), ChildData("Логика"), ChildData("Присваивание"))*/

        val parentObjNotification = ParentData(parentTitle = parentData[0], subList = childNotification)
        val parentObjInOutput = ParentData(parentTitle = parentData[1], subList = childInOutput)
        val parentOtherBlocks = ParentData(parentTitle = parentData[2], subList = childOtherBlocks)

        listData.add(parentObjNotification)
        listData.add(parentObjInOutput)
        listData.add(parentOtherBlocks)

        val exRecyclerView = findViewById<RecyclerView>(R.id.expandable_menu)
        val adapter =  RecycleAdapter(this@BlockMenuActivity, listData, "", object : ChoiceOnClick {
            override fun onClicked(tag: String) {
                val answerIntent = Intent()
                answerIntent.putExtra("user", tag)
                setResult(Activity.RESULT_OK, answerIntent)
                finish()
            }
        })
        exRecyclerView.layoutManager = LinearLayoutManager(this)
        exRecyclerView.adapter = adapter

        BackButton.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }

    }
}


