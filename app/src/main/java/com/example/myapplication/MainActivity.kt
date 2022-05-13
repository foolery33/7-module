package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*


public fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) : Int {
    val dragFlags: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN
    val swipeFlags: Int = ItemTouchHelper.START or ItemTouchHelper.END
    return makeMovementFlags(dragFlags, swipeFlags)
}

public fun isLongPressDragEnabled() : Boolean {
    return true
}

public fun isItemViewSwipeEnabled() : Boolean {
    return true
}

public interface ItemTouchHelperAdapter{
    fun onItemMove(fromPosition: Int, toPosition: Int) {}
    fun onItemDismiss(position: Int) {}
}

interface HasStringId {
    val id: String
    override fun equals(other: Any?): Boolean
}

class AdapterClickListenerById(val clickListener: (id: String) -> Unit) {
    fun onClick(id: String) = clickListener(id)
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
    override val layout: Int = R.layout.block_assigment

    override fun acceptBinding(item: Any): Boolean {

    }

    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        clickListener: AdapterClickListenerById
    ) {

    }
}

class FunctionViewHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.block_function

    override fun acceptBinding(item: Any): Boolean {

    }

    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        clickListener: AdapterClickListenerById
    ) {

    }
}

class CycleViewHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.block_cycle

    override fun acceptBinding(item: Any): Boolean {

    }

    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        clickListener: AdapterClickListenerById
    ) {

    }
}

class IfViewHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.block_if_else

    override fun acceptBinding(item: Any): Boolean {

    }

    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        clickListener: AdapterClickListenerById
    ) {

    }
}

class OutputViewHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.block_output

    override fun acceptBinding(item: Any): Boolean {

    }

    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        clickListener: AdapterClickListenerById
    ) {

    }
}

class InputViewHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.block_input

    override fun acceptBinding(item: Any): Boolean {

    }

    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        clickListener: AdapterClickListenerById
    ) {

    }
}

class InitArrayViewHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.block_init_array

    override fun acceptBinding(item: Any): Boolean {

    }

    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        clickListener: AdapterClickListenerById
    ) {

    }

}

class InitIntViewHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.block_init_int
    override fun acceptBinding(item: Any): Boolean {}

    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        clickListener: AdapterClickListenerById
    ) {

    }
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

abstract class BaseListAdapter(private val clickListener: AdapterClickListenerById,
                               private val viewHoldersManager: ViewHoldersManager
): ListAdapter<HasStringId, BaseListAdapter.DataViewHolder>(BaseDiffCallback()){
    inner class DataViewHolder(
        private val binding: ViewDataBinding,
        private val holder: ViewHolderVisitor
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: HasStringId, clickListener: AdapterClickListenerById) =
            holder.bind(binding, item, clickListener)
    }

    val mItems = mutableListOf<RelativeLayout>()
    private var mAdapter: ItemTouchHelperAdapter? = null

    public fun SimpleItemTouchHelperCallback(adapter: ItemTouchHelperAdapter){
        mAdapter = adapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        LayoutInflater.from(parent.context).run {
            val holder = viewHoldersManager.getViewHolder(viewType)
            DataViewHolder(DataBindingUtil.inflate(this, holder.layout, parent, false), holder)
        }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(getItem(position), clickListener)

    public fun onItemDismiss(position: Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
    }

    public fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition..toPosition)
                Collections.swap(mItems, i, i + 1)
        }
        else {
            for (i in fromPosition downTo toPosition)
                Collections.swap(mItems, i, i - 1)
        }

        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun getItemCount(): Int = mItems.size

    open fun onMove(
        recyclerView: RecyclerView?,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        mAdapter?.onItemMove(
            viewHolder.adapterPosition,
            target.adapterPosition
        )
        return true
    }

    open fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int
    ) {
        mAdapter?.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun getItemViewType(position: Int): Int = viewHoldersManager.getItemType(getItem(position))

}

class SimpleItemTouchHelperCallback(private val mAdapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView, viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        mAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        mAdapter.onItemDismiss(viewHolder.adapterPosition)
    }
}

open class MainActivity : AppCompatActivity() {

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
            startActivityForResult(intent, MainActivity.REQUEST_CHOICE_BLOCK)
        }

        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Activity.RESULT_OK){
            when (requestCode){
                MainActivity.REQUEST_CHOICE_BLOCK -> {
                    val blockname = data?.getStringExtra("user") //выбранный блок
                }
            }
        }
    }

}
