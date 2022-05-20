package com.example.myapplication

import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

import com.example.namespace.R
import java.lang.IllegalArgumentException

class BlockAdapter(val c: Context, private val adapterBlocks: MutableList<DataBlocks>): RecyclerView.Adapter<BlockAdapter.BlockHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockHolder {
        val layout = when (viewType){
            TYPE_INITINT -> R.layout.block_init_int
            TYPE_INITARR -> R.layout.block_init_array
            TYPE_INPUT -> R.layout.block_input
            TYPE_OUTPUT -> R.layout.block_output
            TYPE_IF -> R.layout.block_if
            TYPE_CYCLE -> R.layout.block_cycle
            TYPE_FUNCTION -> R.layout.block_function
            TYPE_ASSIGMENT -> R.layout.block_assigment
            TYPE_BEGIN -> R.layout.subblock_begin
            TYPE_END -> R.layout.subblock_end
            TYPE_ELSE -> R.layout.block_else
            TYPE_RETURN -> R.layout.block_return
            else -> throw IllegalArgumentException("Invalid view type")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return BlockHolder(view)
    }

    override fun onBindViewHolder(holder: BlockHolder, position: Int) {
        val itemView = holder.bind(adapterBlocks[position], adapterBlocks)

    }
    override fun getItemCount(): Int = adapterBlocks.size

    fun setData(data: List<DataBlocks>){
        adapterBlocks.apply {
            clear()
            addAll(data)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterBlocks[position]){
            is DataBlocks.InitInt -> TYPE_INITINT
            is DataBlocks.InitArray -> TYPE_INITARR
            is DataBlocks.InputEl -> TYPE_INPUT
            is DataBlocks.OutputEl -> TYPE_OUTPUT
            is DataBlocks.If -> TYPE_IF
            is DataBlocks.Cycle -> TYPE_CYCLE
            is DataBlocks.Function -> TYPE_FUNCTION
            is DataBlocks.AssigmentEl -> TYPE_ASSIGMENT
            is DataBlocks.Else -> TYPE_ELSE
            is DataBlocks.Return -> TYPE_RETURN
            is DataBlocks.Begin -> TYPE_BEGIN
            is DataBlocks.End -> TYPE_END
        }
    }

    companion object{
        private const val TYPE_INITINT = 0
        private const val TYPE_INITARR = 1
        private const val TYPE_INPUT = 2
        private const val TYPE_OUTPUT = 3
        private const val TYPE_IF = 4
        private const val TYPE_CYCLE = 5
        private const val TYPE_FUNCTION = 6
        private const val TYPE_ASSIGMENT = 7
        private const val TYPE_ELSE = 8
        private const val TYPE_RETURN = 9
        private const val TYPE_BEGIN = 10
        private const val TYPE_END = 11
    }


    class BlockHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private fun bindInitInt(item: DataBlocks.InitInt, list: MutableList<DataBlocks>){
            val nameEdit = itemView.findViewById<EditText>(R.id.init_int)

            nameEdit.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    p0: TextView?,
                    keyCode: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (keyCode == EditorInfo.IME_ACTION_DONE) {
                        item.name = nameEdit.getText().toString()
                        Log.d("Int", item.name)

                        return true
                    }
                    return false
                }
            })

        }
        private fun bindInitArr(item: DataBlocks.InitArray, list: MutableList<DataBlocks>){
            val nameArr = itemView.findViewById(R.id.name_array) as EditText
            val lenArr = itemView.findViewById(R.id.len_array) as EditText

            nameArr.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    p0: TextView?,
                    keyCode: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (keyCode == EditorInfo.IME_ACTION_DONE) {
                        item.name = nameArr.getText().toString()

                        return true
                    }
                    return false
                }
            })

            lenArr.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    p0: TextView?,
                    keyCode: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (keyCode == EditorInfo.IME_ACTION_DONE) {
                        item.len = lenArr.getText().toString()

                        return true
                    }
                    return false
                }
            })
        }
        private fun bindInput(item: DataBlocks.InputEl, list: MutableList<DataBlocks>){
            val textInput = itemView.findViewById(R.id.name_input) as EditText

            textInput.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    p0: TextView?,
                    keyCode: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (keyCode == EditorInfo.IME_ACTION_DONE) {
                        item.name = textInput.getText().toString()

                        return true
                    }
                    return false
                }
            })
        }
        private fun bindOutput(item: DataBlocks.OutputEl, list: MutableList<DataBlocks>){
            val textOutput = itemView.findViewById(R.id.edit_output) as EditText

            textOutput.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    p0: TextView?,
                    keyCode: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (keyCode == EditorInfo.IME_ACTION_DONE) {
                        item.name = textOutput.getText().toString()

                        return true
                    }
                    return false
                }
            })
        }
        private fun bindIf(item: DataBlocks.If, list: MutableList<DataBlocks>){
            val leftIf = itemView.findViewById(R.id.name1_if) as EditText
            val rightIf = itemView.findViewById(R.id.name2_if) as EditText
            val operateI = itemView.findViewById(R.id.operator_choice_if) as Spinner
            val addButton = itemView.findViewById(R.id.add_else) as ImageButton


            list.add(DataBlocks.Begin())
            item.begin = list.indexOf(item)
            list.add(DataBlocks.End())
            item.end = list.indexOf(item) + 2

            leftIf.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    p0: TextView?,
                    keyCode: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (keyCode == EditorInfo.IME_ACTION_DONE) {
                        item.el1 = leftIf.getText().toString()

                        return true
                    }
                    return false
                }
            })
            rightIf.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(
                    p0: TextView?,
                    keyCode: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (keyCode == EditorInfo.IME_ACTION_DONE) {
                        item.el2 = rightIf.getText().toString()

                        return true
                    }
                    return false
                }
            })
            operateI.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    item.choose = p0?.getItemAtPosition(p2).toString()
                    Log.d("Spin", item.choose)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    item.choose = p0?.getItemAtPosition(0).toString()
                    Log.d("Spin", item.choose)
                }

            }

            addButton.setOnClickListener{

            }

        }
        private fun bindCycle(item: DataBlocks.Cycle, list: MutableList<DataBlocks>){
            val leftCycle = itemView.findViewById(R.id.name1_cycle) as EditText
            val rightCycle = itemView.findViewById(R.id.name2_cycle) as EditText
            val operateC = itemView.findViewById(R.id.operator_choice) as Spinner
        }
        private fun bindFunction(item: DataBlocks.Function, list: MutableList<DataBlocks>){
            val leftIf = itemView.findViewById(R.id.name_function) as EditText
            val rightIf = itemView.findViewById(R.id.len_function) as EditText
            val addButton = itemView.findViewById(R.id.add_return) as ImageButton
        }
        private fun bindAssigment(item: DataBlocks.AssigmentEl, list: MutableList<DataBlocks>) {
            val variable = itemView.findViewById(R.id.nameVar) as EditText
            val value = itemView.findViewById(R.id.value) as EditText
        }
        private fun bindReturn(item: DataBlocks.Return, list: MutableList<DataBlocks>){
            val returnVar = itemView.findViewById(R.id.val_return) as EditText
        }
        private fun bindElse(item: DataBlocks.Else, list: MutableList<DataBlocks>){
        }
        private fun bindBegin(item: DataBlocks.Begin, list: MutableList<DataBlocks>){
            //val textVBegin = itemView.findViewById(R.id.begin_text) as TextView?
        }
        private fun bindEnd(item: DataBlocks.End, list: MutableList<DataBlocks>){
            //val textVEnd = itemView.findViewById(R.id.end_text) as TextView?
        }

        fun bind(dataModel: DataBlocks, list: MutableList<DataBlocks>){
            when (dataModel){
                is DataBlocks.InitInt -> bindInitInt(dataModel, list)
                is DataBlocks.InitArray -> bindInitArr(dataModel, list)
                is DataBlocks.InputEl -> bindInput(dataModel, list)
                is DataBlocks.OutputEl -> bindOutput(dataModel, list)
                is DataBlocks.If -> bindIf(dataModel, list)
                is DataBlocks.Cycle -> bindCycle(dataModel, list)
                is DataBlocks.Function -> bindFunction(dataModel, list)
                is DataBlocks.AssigmentEl -> bindAssigment(dataModel, list)
                is DataBlocks.Else -> bindElse(dataModel, list)
                is DataBlocks.Return -> bindReturn(dataModel, list)
                is DataBlocks.Begin -> bindBegin(dataModel, list)
                is DataBlocks.End -> bindEnd(dataModel, list)
            }
        }
    }


}