package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.RelativeLayout
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.navigation.NavigationView
import kotlin.contracts.InvocationKind

interface ViewHolderManager{
    fun registerViewHolder(itemType: Int, viewHolder: ViewHolderVisitor)
    fun getItemType(item: Any): Int
    fun getViewHolder(itemType: Int): ViewHolderVisitor
}

object ItemTypes{
    const val Init_int = -1
    const val Init_array = 0
    const val Input_ = 1
    const val Output_ = 2
    const val If_ = 3
    const val Cycle_ = 4
    const val Function_ = 5
    const val Assigment_ = 6
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

interface ViewHolderVisitor{
    val layout: Int
    fun acceptBinding(item: Any): Boolean
    fun bind(binding: ViewDataBinding, item: Any, clickListener: AdapterClickListenerById)
}

class ViewHoldersManagerImpl: ViewHolderManager{
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

class CustomRecyclerAdapter(private val names: List <RelativeLayout>){


}
class InterfaceActivity: Activity() {

    companion object {
        const val REQUEST_CHOICE_BLOCK = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_interface)

        val recyclerView: RecyclerView = findViewById(R.id.rv_main)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val HelpButton: ImageButton = findViewById(R.id.help)
        val MenuButton: ImageButton = findViewById(R.id.choose)
        val RunButton: ImageButton = findViewById(R.id.run)

        HelpButton.setOnClickListener{
            val intent = Intent(this@InterfaceActivity, HelpActivity::class.java)
            startActivity(intent)
        }


        MenuButton.setOnClickListener{
            val intent = Intent(this@InterfaceActivity, BlockMenuActivity::class.java)
            val choice_block = 0
            startActivityForResult(intent, REQUEST_CHOICE_BLOCK)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Activity.RESULT_OK){
            when (requestCode){
                REQUEST_CHOICE_BLOCK -> {
                    val blockname = data?.getStringExtra("user") //выбранный блок

                }

            }
        }
    }

}