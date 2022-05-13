package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*
import com.example.myapplication.HasStringId
import kotlin.collections.ArrayList


open class MainActivity : AppCompatActivity() {

    private var launcher: ActivityResultLauncher<Intent>? = null

    companion object {
        const val REQUEST_CHOICE_BLOCK = 0
        @JvmField
        var variables = mutableMapOf<String, Int>()
        var arrays = mutableMapOf<String, Array<Int>>()

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

        val recyclerView: RecyclerView = findViewById(R.id.rv_main)
        val programList: MutableList<DataBlocks> = mutableListOf<DataBlocks>()
        val blockAdapter: BlockAdapter = BlockAdapter(this, programList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = blockAdapter

        val HelpButton: ImageButton = findViewById(R.id.help)
        val MenuButton: ImageButton = findViewById(R.id.choose)
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

    }

    fun addList(choice: String, programList: MutableList<DataBlocks>){
        programList.add(when (choice) {
            in "int" -> DataBlocks.InitInt("")
            in "array" -> DataBlocks.InitArray("", "")
            in "input" -> DataBlocks.InputEl("")
            in "output" -> DataBlocks.InputEl("")
            in "if" -> DataBlocks.InputEl("")
            in "cycle" -> DataBlocks.InputEl("")
            in "function" -> DataBlocks.InputEl("")
            else -> DataBlocks.InputEl("")
        })
    }
}

