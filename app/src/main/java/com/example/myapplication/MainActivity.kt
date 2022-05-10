package com.example.myapplication

import android.R.attr.data
import android.util.Log
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

interface HasStringId{
    val id: String
}

class BaseDiffCallback: DiffUtil.ItemCallback<HasStringId>(){
    override fun areItemsTheSame(oldItem: HasStringId, newItem: HasStringId): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: HasStringId, newItem: HasStringId): Boolean = oldItem == newItem
}

interface ViewHoldersManager{
    fun registerViewHolder(itemType: Int, viewHolder: ViewHolderVisitor)
    fun getItemType(item: Any): Int
    fun getViewHolder(itemType: Int): ViewHolderVisitor
}

object ItemTypes{
    const val UNKNOWN = -1
    const val Init_int = 0
    const val Init_array = 1
    const val Input_ = 2
    const val Output_ = 3
    const val If_ = 4
    const val Cycle_ = 5
    const val Function_ = 6
    const val Assigment_ = 7
}

object DiModule{
    fun provideAdaptersManager(): ViewHoldersManager = ViewHoldersManagerImpl().apply{
        registerViewHolder(ItemTypes.Init_int, InitIntViewHolder())
        registerViewHolder(ItemTypes.Init_array, InitArrayViewHolder())
        registerViewHolder(ItemTypes.Input_, InputViewHolder())
        registerViewHolder(ItemTypes.Output_, OutputViewHolder())
        registerViewHolder(ItemTypes.If_, IfViewHolder())
        registerViewHolder(ItemTypes.Cycle_, CycleViewHolder())
        registerViewHolder(ItemTypes.Function_, FunctionViewHolder())
        registerViewHolder(ItemTypes.Assigment_, AssigmentViewHolder())
    }
}

class AssigmentViewHolder : ViewHolderVisitor {

}

class FunctionViewHolder : ViewHolderVisitor {

}

class CycleViewHolder : ViewHolderVisitor {

}

class IfViewHolder : ViewHolderVisitor {

}

class OutputViewHolder : ViewHolderVisitor {

}

class InputViewHolder : ViewHolderVisitor {

}

class InitArrayViewHolder : ViewHolderVisitor {

}

class InitIntViewHolder : ViewHolderVisitor {

}

interface ViewHolderVisitor{
    val layout: Int
    fun acceptBinding(item: Any): Boolean
    fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById)
}

class ViewHoldersManagerImpl: ViewHoldersManager{
    private val holdersMap = emptyMap<Int, ViewHolderVisitor>().toMutableMap()

    override fun registerViewHolder(itemType: Int, viewHolder: ViewHolderVisitor) {
        holdersMap += itemType to viewHolder
    }

    override fun getItemType(item: Any): Int {
        holdersMap.forEach{ (itemType, holder) ->
            if (holder.acceptBinding(item)) return itemType
        }
        return ItemTypes.UNKNOWN
    }

    override fun getViewHolder(itemType: Int) = holdersMap[itemType] ?: throw TypeCastException("Unknown recycler item type!")
}

class BaseListAdapter(private val clickListener: AdapterClickListenerById,
                      private val viewHoldersManager: ViewHoldersManager
): ListAdapter<HasStringId, BaseListAdapter.DataViewHolder>(BaseDiffCallback()){
    inner class DataViewHolder(
        private val binding: ViewDataBinding,
        private val holder: ViewHolderVisitor
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: HasStringId, clickListener: AdapterClickListenerById) =
            holder.bind(binding, item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        LayoutInflater.from(parent.context).run {
            val holder = viewHoldersManager.getViewHolder(viewType)
            DataViewHolder(DataBindingUtil.inflate(this, holder.layout, parent, false), holder)
        }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(getItem(position), clickListener)

    override fun getItemViewType(position: Int): Int = viewHoldersManager.getItemType(getItem(position))

}

open class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CHOICE_BLOCK = 0
    }

    companion object {
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
        recyclerView.layoutManager = LinearLayoutManager(this)

        val HelpButton: ImageButton = findViewById(R.id.help)
        val MenuButton: ImageButton = findViewById(R.id.choose)
        val RunButton: ImageButton = findViewById(R.id.run)

        HelpButton.setOnClickListener{
            val intent = Intent(this@MainActivity, HelpActivity::class.java)
            startActivity(intent)
        }


        MenuButton.setOnClickListener{
            val intent = Intent(this@MainActivity, BlockMenuActivity::class.java)
            val choice_block = 0
            startActivityForResult(intent, InterfaceActivity.REQUEST_CHOICE_BLOCK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Activity.RESULT_OK){
            when (requestCode){
                InterfaceActivity.REQUEST_CHOICE_BLOCK -> {
                    val blockname = data?.getStringExtra("user") //выбранный блок

                }

            }
        }
    }

}
