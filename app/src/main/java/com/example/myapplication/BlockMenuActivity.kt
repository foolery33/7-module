package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.namespace.R

import com.google.android.material.snackbar.Snackbar
import java.sql.ResultSet
import java.time.temporal.ValueRange

class Data(){

    private var valueText: String = ""
    private var valueId: Int = 0
    private var parentItem: Boolean = false
    private var parentId: Int = -1
    private var childVisibility: Boolean = false

    fun isItemParent(): Boolean {
        return parentItem
    }

    fun setItemParent(newItemParent: Boolean){
        parentItem = newItemParent
    }

    fun isChildVisibility(): Boolean {
        return childVisibility
    }

    fun setChildVisibility(newChildVisibility: Boolean){
        childVisibility = newChildVisibility
    }

    fun getParentId(): Int {
        return parentId
    }

    fun setParentId(newParentId: Int){
        parentId = newParentId
    }

    fun getValueText(): String{
        return valueText
    }

    fun setValueText(newValueText: String){
        valueText = newValueText
    }

    fun getValueId(): Int{
        return valueId
    }

    fun setValueId(newValueId: Int){
        valueId = newValueId
    }
}

class CustomRecyclerAdapterMenu(private var allRecords: MutableList <Data>, var choice: String):
    RecyclerView.Adapter<CustomRecyclerAdapterMenu.MenuViewHolder>(){

    public fun customRecyclerAdapterMenu(records: MutableList<Data>) {
        allRecords = records
    }

    public fun getItem(): String{
        return choice
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val item: LinearLayout = itemView.findViewById(R.id.block_text)
        val valueText: TextView = itemView.findViewById(R.id.value_name)
        val iconTree: AppCompatImageView = itemView.findViewById(R.id.icon_tree)
        val vv: View = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycleview_item_formenu,parent,false)
        return MenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val record: Data = allRecords[position]
        val value: String = record.getValueText()
        var id: Int = record.getValueId()
        val parentId: Int = record.getParentId()

        if (parentId >= 0){
            setVisibility(holder.item, allRecords[parentId].isChildVisibility(), parentId)
        }
        else{
            setVisibility(holder.iconTree, true, parentId)
        }

        if (record.isItemParent()){
            holder.iconTree.visibility = View.VISIBLE

            if (record.isChildVisibility())
                holder.iconTree.setBackgroundResource(R.drawable.icon_show)
            else
                holder.iconTree.setBackgroundResource(R.drawable.icon_hide)
        }
        else
            holder.iconTree.visibility = View.GONE

        if (!TextUtils.isEmpty(value)){
            holder.valueText.text = value
        }

        holder.valueText.setOnClickListener(View.OnClickListener() {
            fun onClick(view: View){
                val dataItem: Data = allRecords[position]
                val answerIntent = Intent()

                if (dataItem.isItemParent()){
                    dataItem.setChildVisibility(!dataItem.isChildVisibility())
                    notifyDataSetChanged()
                }
                else{
                    val valueId = dataItem.getValueId()

                    if (valueId == 1)
                        choice = "int"
                    else if (valueId == 2)
                        choice = "array"
                    else if (valueId == 3)
                        choice = "input"
                    else if (valueId == 4)
                        choice = "output"
                    else if (valueId == 5)
                        choice = "if"
                    else if (valueId == 6)
                        choice = "cycle"
                    else if (valueId == 7)
                        choice = "function"
                    else if (valueId == 8)
                        choice = "assignment"
                }
            }
        })
    }

    private fun setVisibility(curV: View, visible: Boolean, parentId: Int){
        val vPadding: LinearLayout = curV.findViewById(R.id.block_text)

        val params: LinearLayout.LayoutParams

        if (visible){
            params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            if (parentId >= 0){
                vPadding.setPadding(80, 0, 0, 0)
            }
            else{
                vPadding.setPadding(0,0,0,0)
            }
        }
        else
            params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        curV.layoutParams = params
    }

    override fun getItemCount() = allRecords.size


}



open class BlockMenuActivity: Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_blocksmenu)

        val BackButton: ImageButton = findViewById(R.id.return_block)
        val HelpButton: ImageButton = findViewById(R.id.help_blocks)
        val records: MutableList<Data> = ArrayList<Data>()
        var record: Data = Data()
        var parentId: Int = 0
        val recyclerView: RecyclerView = findViewById(R.id.recycle_list)

        /*fun setImageView(valueId:Int){
            val imageView: ImageView = ImageView(this)
            imageView.setImageResource(R.drawable.icon)

            if (valueId == 1)
                imageView.setBackgroundColor(Color.parseColor("#"))
        }*/

        fun fillList(){
            record.setValueId(1)
            record.setValueText("Объявления")
            record.setItemParent(true)
            records.add(record)
            parentId = records.size - 1

            record = Data()
            record.setValueId(1)
            record.setValueText("Целочисленной переменной")
            record.setParentId(parentId)
            records.add(record)


            record = Data()
            record.setValueId(2)
            record.setValueText("Массива")
            record.setParentId(parentId)
            records.add(record)

            record = Data()
            record.setValueId(1)
            record.setValueText("Ввод/Вывод данных")
            record.setItemParent(true)
            records.add(record)
            parentId = records.size - 1

            record = Data()
            record.setValueId(3)
            record.setValueText("Ввод")
            record.setParentId(parentId)
            records.add(record)

            record = Data()
            record.setValueId(4)
            record.setValueText("Вывод")
            record.setParentId(parentId)
            records.add(record)

            record = Data()
            record.setValueId(1)
            record.setValueText("Логика")
            record.setItemParent(true)
            records.add(record)
            parentId = records.size - 1

            record = Data()
            record.setValueId(5)
            record.setValueText("If")
            record.setParentId(parentId)
            records.add(record)

            record = Data()
            record.setValueId(6)
            record.setValueText("Цикл")
            records.add(record)

            record = Data()
            record.setValueId(7)
            record.setValueText("Функция")
            records.add(record)

            record = Data()
            record.setValueId(8)
            record.setValueText("Присваивание")
            records.add(record)
        }

        fillList()
        val adapter: CustomRecyclerAdapterMenu = CustomRecyclerAdapterMenu(records, "")
        val itemAnimator: RecyclerView.ItemAnimator = DefaultItemAnimator()
        val layoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = itemAnimator

        BackButton.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }

        val answerIntent = Intent()
        val choice: String = adapter.getItem()
        answerIntent.putExtra("user", choice)
        setResult(RESULT_OK, answerIntent)
        finish()
    }
}


