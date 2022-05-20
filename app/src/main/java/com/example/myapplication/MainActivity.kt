package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.namespace.R
import com.example.namespace.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*
import kotlin.collections.ArrayList


open class MainActivity : AppCompatActivity() {

    private var launcher: ActivityResultLauncher<Intent>? = null
    private lateinit var binding: ActivityMainBinding



    companion object{

        @JvmField
        var programList: MutableList<DataBlocks> = mutableListOf<DataBlocks>()
        var variables = mutableMapOf<String, Int>()
        var arrays = mutableMapOf<String, Array<Int>>()
        lateinit var blockAdapter: BlockAdapter

        var ifConditionsCounter = -1
        var ifConditions = mutableListOf<IfConditions>()
        var elseConditionsCounter = -1
        var elseConditions = mutableListOf<ElseConditions>()

        fun addToMap(name: String, value: Int) {
            if(variables.containsKey(name)) {
                variables[name] = value
            }
            else {
                variables[name] = value
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        IntVariable("variable")
        AssignmentOperation("variable", "123")
        OutputBlock("variable")
        StaticArray("array", "10")
        InputBlock("array[0],array[1],array[2],array[3]", "1,2,3,4")
        AssignmentOperation("array[4]","array[0]+array[1]+array[2]+array[3]")
        OutputBlock("array[array[0]+array[2]]")
        OutputBlock("array[4]")


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setAdapter()

        val HelpButton: ImageButton = findViewById(R.id.help)
        val MenuButton: ImageButton = findViewById(R.id.menu)
        val RunButton: ImageButton = findViewById(R.id.run)

        HelpButton.setOnClickListener{
            val intent = Intent(this@MainActivity, HelpActivity::class.java)
            startActivity(intent)
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
            if (result.resultCode == RESULT_OK){
                val choice = result.data?.getStringExtra("user")
                if (choice != null) {
                    addList(choice, programList)
                }
                blockAdapter.notifyDataSetChanged()
            }
        }

        MenuButton.setOnClickListener{
            launcher?.launch(Intent(this@MainActivity, BlockMenuActivity::class.java))
        }

        RunButton.setOnClickListener {
            val result = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.run_console, null)
            val closeButton = view.findViewById<ImageButton>(R.id.delete_console)
            val text = view.findViewById<TextView>(R.id.output_result)

            for (block in programList){
                text.text = block.doProgram(block, text)

                if (!block.flag){
                    break
                }

            }

            closeButton.setOnClickListener {
                result.dismiss()
                text.text = ""
            }
            result.setCancelable(false)
            result.setContentView(view)
            result.show()
        }

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN
                or ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder,
                target: ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.bindingAdapterPosition
                val toPosition = target.bindingAdapterPosition
                Collections.swap(programList, fromPosition, toPosition)

                blockAdapter.notifyItemMoved(fromPosition, toPosition)
                return false
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                programList.remove(programList[position])
                blockAdapter.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMain)

    }

    fun addList(choice: String, programList: MutableList<DataBlocks>){
        when (choice) {
            in "int" ->
                programList.add(DataBlocks.InitInt())
            in "array" ->
                programList.add(DataBlocks.InitArray())
            in "input" ->
                programList.add(DataBlocks.InputEl())
            in "output" ->
                programList.add(DataBlocks.OutputEl())
            in "if" -> {
                val end = DataBlocks.End()
                val begin = DataBlocks.Begin()
                programList.add(DataBlocks.If())
                programList.add(begin)
                programList.add(end)
            }
            in "cycle" -> {
                val end = DataBlocks.End()
                val begin = DataBlocks.Begin()
                programList.add(DataBlocks.Cycle())
                programList.add(begin)
                programList.add(end)
            }
            in "function" -> {
                val end = DataBlocks.End()
                val begin = DataBlocks.Begin()
                programList.add(DataBlocks.Function())
                programList.add(begin)
                programList.add(end)
            }
            else -> programList.add(DataBlocks.AssigmentEl())
        }
    }

    fun setAdapter(){
        blockAdapter = BlockAdapter(programList)
        binding.rvMain.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = blockAdapter
        }
    }
}

