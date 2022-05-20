package com.example.myapplication

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

import com.example.namespace.R
import com.example.namespace.databinding.*
import java.lang.IllegalArgumentException

class BlockAdapter(private val adapterBlocks: MutableList<DataBlocks>): RecyclerView.Adapter<BlockAdapter.BlockHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockHolder {
        return BlockHolder(
            when (viewType){
                TYPE_INITINT -> BlockInitIntBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TYPE_INITARR -> BlockInitArrayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TYPE_INPUT -> BlockInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TYPE_OUTPUT -> BlockOutputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TYPE_IF -> BlockIfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TYPE_CYCLE -> BlockCycleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TYPE_FUNCTION -> BlockFunctionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TYPE_ASSIGMENT -> BlockAssigmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TYPE_BEGIN -> SubblockBeginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TYPE_END -> SubblockEndBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TYPE_ELSE -> BlockElseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TYPE_RETURN -> BlockReturnBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                else -> throw IllegalArgumentException("Invalid view type")
            }
        )

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


    class BlockHolder(private var binding: ViewBinding) : RecyclerView.ViewHolder(binding.root){
        private fun bindInitInt(item: DataBlocks.InitInt, list: MutableList<DataBlocks>, binding: BlockInitIntBinding){
            binding.initint = item
        }
        private fun bindInitArr(item: DataBlocks.InitArray, list: MutableList<DataBlocks>, binding: BlockInitArrayBinding){
            binding.array = item
        }
        private fun bindInput(item: DataBlocks.InputEl, list: MutableList<DataBlocks>, binding: BlockInputBinding){
            binding.inputB = item
        }
        private fun bindOutput(item: DataBlocks.OutputEl, list: MutableList<DataBlocks>, binding: BlockOutputBinding){
            binding.outputB = item
        }
        private fun bindAssigment(item: DataBlocks.AssigmentEl, list: MutableList<DataBlocks>, binding: BlockAssigmentBinding) {
            binding.assigments = item
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
                is DataBlocks.InitInt -> bindInitInt(dataModel, list,
                    binding as BlockInitIntBinding
                )
                is DataBlocks.InitArray -> bindInitArr(dataModel, list,
                    binding as BlockInitArrayBinding
                )
                is DataBlocks.InputEl -> bindInput(dataModel, list, binding as BlockInputBinding)
                is DataBlocks.OutputEl -> bindOutput(dataModel, list, binding as BlockOutputBinding)
                is DataBlocks.If -> bindIf(dataModel, list)
                is DataBlocks.Cycle -> bindCycle(dataModel, list)
                is DataBlocks.Function -> bindFunction(dataModel, list)
                is DataBlocks.AssigmentEl -> bindAssigment(dataModel, list, binding as BlockAssigmentBinding)
                is DataBlocks.Else -> bindElse(dataModel, list)
                is DataBlocks.Return -> bindReturn(dataModel, list)
                is DataBlocks.Begin -> bindBegin(dataModel, list)
                is DataBlocks.End -> bindEnd(dataModel, list)
            }
        }
    }


}