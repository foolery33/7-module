package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.namespace.R
import com.example.namespace.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess


open class MainActivity : AppCompatActivity() {

    private var launcher: ActivityResultLauncher<Intent>? = null
    private lateinit var binding: ActivityMainBinding



    companion object{

        @JvmField
        var programList: MutableList<DataBlocks> = mutableListOf<DataBlocks>()
        var variables = mutableMapOf<String, Int>()
        var arrays = mutableMapOf<String, Array<Int>>()
        lateinit var blockAdapter: BlockAdapter

        var commands: MutableList<String> = mutableListOf()
        var ifConditions: MutableList<MutableList<String> > = mutableListOf()
        var elseConditions: MutableList<MutableList<String> > = mutableListOf()
        var cycleCommands: MutableList<MutableList<String> > = mutableListOf()
        var functionCommands: MutableList<MutableList<String> > = mutableListOf()
        var functionReturnValues: MutableMap<Int, Int> = mutableMapOf()
        var functionNumberOfCommands: MutableMap<String, Int> = mutableMapOf()

        var ifConditionsCounter = -1
        var elseConditionsCounter = -1
        var cycleCommandsCounter = -1
        var functionCommandsCounter = 0

        var returnFlag = "default"

        var inputCounter = 0
        var input = 0
        var inputNames = mutableListOf<String>()
        var inputValues = mutableMapOf<String, String>()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setAdapter()

        val HelpButton: ImageButton = findViewById(R.id.help)
        val MenuButton: ImageButton = findViewById(R.id.menu)
        val RunButton: ImageButton = findViewById(R.id.run)

        HelpButton.setOnClickListener {
            val intent = Intent(this@MainActivity, HelpActivity::class.java)
            startActivity(intent)
        }

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val choice = result.data?.getStringExtra("user")
                    if (choice != null) {
                        addList(choice, programList)
                    }
                    blockAdapter.notifyDataSetChanged()
                }
            }

        MenuButton.setOnClickListener {
            launcher?.launch(Intent(this@MainActivity, BlockMenuActivity::class.java))
        }

        RunButton.setOnClickListener {
            for (i in programList) {
                when (i) {
                    is DataBlocks.InputEl -> {
                        val matchResult = Regex("[a-zA-Z]\\w*").findAll(i.name)
                        matchResult.forEach { f ->
                            if (!inputNames.contains(f.value)) {
                                //Вывести в консоль ошибку, что переменная не была объявлена и прекратить выполнение программы
                                return@forEach
                            } else {
                                var alertDialog = createAlert(i.name, programList)
                                alertDialog.show()
                            }
                        }
                    }
                    is DataBlocks.InitInt -> {
                        inputNames.add(i.name)
                    }
                    is DataBlocks.InitArray -> {
                        inputNames.add(i.name)
                    }
                }

            }
            if(inputCounter == 0) {
                continueProgram(programList)
            }
        }

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN
                    or ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.RIGHT
        ) {
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
    fun createAlert(name: String, programList: MutableList<DataBlocks>): AlertDialog.Builder {

        var inputString = ""

        var alertName: AlertDialog.Builder = AlertDialog.Builder(this)
        var editTextName1 = EditText(this)

        alertName.setTitle("Введите значение(-я) $name")
        alertName.setView(editTextName1)

        var layoutName = LinearLayout(this)
        layoutName.orientation = LinearLayout.VERTICAL
        layoutName.addView(editTextName1)
        alertName.setView(layoutName)

        alertName.setPositiveButton("OK") { _, _ ->
            run {
                input++
                inputString =  editTextName1.text.toString()
                inputValues[name] = inputString
                if(input == inputCounter) {
                    continueProgram(programList)
                }
            }
        }
        alertName.setNegativeButton("Cancel") { _, _ ->
            input++
            inputString =  editTextName1.text.toString()
            inputValues[name] = inputString
            if(input == inputCounter) {
                continueProgram(programList)
            }
        }

        return alertName
    }

    fun continueProgram(programList: MutableList<DataBlocks>) {
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

    fun setAdapter() {
        blockAdapter = BlockAdapter(programList)
        binding.rvMain.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = blockAdapter
        }
    }

}

