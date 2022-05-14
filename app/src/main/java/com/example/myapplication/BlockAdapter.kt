package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
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
            TYPE_IFELSE -> R.layout.block_if_else
            TYPE_CYCLE -> R.layout.block_cycle
            TYPE_FUNCTION -> R.layout.block_function
            else -> throw IllegalArgumentException("Invalid view type")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return BlockHolder(view)
    }

    override fun onBindViewHolder(holder: BlockHolder, position: Int) {
        holder.bind(adapterBlocks[position])
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
            is DataBlocks.IfElse -> TYPE_IFELSE
            is DataBlocks.Cycle -> TYPE_CYCLE
            is DataBlocks.Function -> TYPE_FUNCTION
            else -> TYPE_ASSIGMENT
        }
    }

    companion object{
        private const val TYPE_INITINT = 0
        private const val TYPE_INITARR = 1
        private const val TYPE_INPUT = 2
        private const val TYPE_OUTPUT = 3
        private const val TYPE_IFELSE = 4
        private const val TYPE_CYCLE = 5
        private const val TYPE_FUNCTION = 6
        private const val TYPE_ASSIGMENT = 7
    }

    class BlockHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private fun bindInitInt(item: DataBlocks.InitInt){
        }
        private fun bindInitArr(item: DataBlocks.InitArray){
        }
        private fun bindInput(item: DataBlocks.InputEl){
        }
        private fun bindOutput(item: DataBlocks.OutputEl){
        }
        private fun bindIfElse(item: DataBlocks.IfElse){
        }
        private fun bindCycle(item: DataBlocks.Cycle){
        }
        private fun bindFunction(item: DataBlocks.Function){
        }
        private fun bindAssigment(item: DataBlocks.Assigment){

        }

        fun bind(dataModel: DataBlocks){
            when (dataModel){
                is DataBlocks.InitInt -> bindInitInt(dataModel)
                is DataBlocks.InitArray -> bindInitArr(dataModel)
                is DataBlocks.InputEl -> bindInput(dataModel)
                is DataBlocks.OutputEl -> bindOutput(dataModel)
                is DataBlocks.IfElse -> bindIfElse(dataModel)
                is DataBlocks.Cycle -> bindCycle(dataModel)
                is DataBlocks.Function -> bindFunction(dataModel)
                is DataBlocks.Assigment -> bindAssigment(dataModel)
            }
        }
    }


}