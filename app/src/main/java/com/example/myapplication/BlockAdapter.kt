package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.namespace.R
import java.lang.IllegalArgumentException

class BlockAdapter(val c: Context, private val adapterBlocks: MutableList<DataBlocks>): RecyclerView.Adapter<BlockAdapter.BlockHolder>() {

    /*class BlockHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = BlockInitIntBinding.bind(item)
        fun bind(init_block: InitIntBlock) = with(binding){
            i
        }
    }*/



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
        val itemView = holder.bind(adapterBlocks[position])

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
        private fun bindInitInt(item: DataBlocks.InitInt){
            val textInt = itemView.findViewById(R.id.init_int) as EditText?
        }
        private fun bindInitArr(item: DataBlocks.InitArray){
            val nameArr = itemView.findViewById(R.id.name_array) as EditText?
            val lenArr = itemView.findViewById(R.id.len_array) as EditText?
        }
        private fun bindInput(item: DataBlocks.InputEl){
            val textInput = itemView.findViewById(R.id.name_input) as EditText?
        }
        private fun bindOutput(item: DataBlocks.OutputEl){
            val textOutput = itemView.findViewById(R.id.edit_output) as EditText?
        }
        private fun bindIf(item: DataBlocks.If){
            val leftIf = itemView.findViewById(R.id.name1_if) as EditText?
            val rightIf = itemView.findViewById(R.id.name2_if) as EditText?
            val operateI = itemView.findViewById(R.id.operator_choice_if) as Spinner?
            val addButton = itemView.findViewById(R.id.add_else) as ImageButton?
        }
        private fun bindCycle(item: DataBlocks.Cycle){
            val leftCycle = itemView.findViewById(R.id.name1_cycle) as EditText?
            val rightCycle = itemView.findViewById(R.id.name2_cycle) as EditText?
            val operateC = itemView.findViewById(R.id.operator_choice) as Spinner?
        }
        private fun bindFunction(item: DataBlocks.Function){
            val leftIf = itemView.findViewById(R.id.name_function) as EditText?
            val rightIf = itemView.findViewById(R.id.len_function) as EditText?
            val addButton = itemView.findViewById(R.id.add_return) as ImageButton?
        }
        private fun bindAssigment(item: DataBlocks.AssigmentEl) {
            val variable = itemView.findViewById(R.id.nameVar) as EditText?
            val value = itemView.findViewById(R.id.value) as EditText?
        }
        private fun bindReturn(item: DataBlocks.Return){
            val returnVar = itemView.findViewById(R.id.val_return) as EditText?
        }
        private fun bindElse(item: DataBlocks.Else){
        }
        private fun bindBegin(item: DataBlocks.Begin){
            //val textVBegin = itemView.findViewById(R.id.begin_text) as TextView?
        }
        private fun bindEnd(item: DataBlocks.End){
            //val textVEnd = itemView.findViewById(R.id.end_text) as TextView?
        }

        fun bind(dataModel: DataBlocks){
            when (dataModel){
                is DataBlocks.InitInt -> bindInitInt(dataModel)
                is DataBlocks.InitArray -> bindInitArr(dataModel)
                is DataBlocks.InputEl -> bindInput(dataModel)
                is DataBlocks.OutputEl -> bindOutput(dataModel)
                is DataBlocks.If -> bindIf(dataModel)
                is DataBlocks.Cycle -> bindCycle(dataModel)
                is DataBlocks.Function -> bindFunction(dataModel)
                is DataBlocks.AssigmentEl -> bindAssigment(dataModel)
                is DataBlocks.Else -> bindElse(dataModel)
                is DataBlocks.Return -> bindReturn(dataModel)
                is DataBlocks.Begin -> bindBegin(dataModel)
                is DataBlocks.End -> bindEnd(dataModel)
            }
        }
    }


}