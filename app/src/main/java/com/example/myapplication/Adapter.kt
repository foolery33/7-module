package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.namespace.R

class RecycleAdapter(var mContext: Context, val list: MutableList<ParentData>, private var choice: String, private val onClickListener: ChoiceOnClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    fun getChoice() : String = choice

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == Constants.PARENT){
            val rowView: View = LayoutInflater.from(parent.context).inflate(R.layout.parent_row, parent, false)
            GroupViewHolder(rowView)
        }
        else{
            val rowView: View = LayoutInflater.from(parent.context).inflate(R.layout.child_row, parent, false)
            ChildViewHolder(rowView)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataList = list[position]

        if(dataList.type == Constants.PARENT){
            holder as GroupViewHolder
            holder.apply {
                parentTV?.text = dataList.parentTitle
                parentV.setOnClickListener{
                    expandOrCollapseParentItem(dataList, position, downIV)
                }
            }
        }
        else{
            holder as ChildViewHolder

            holder.apply {
                val singleService = dataList.subList.first()
                childTV?.text = singleService.childTitle
                childV.setOnClickListener {
                    onClickListener.onClicked(singleService.nameBlock)
                }
            }
        }
    }

    private fun expandOrCollapseParentItem(singleBoarding: ParentData, position: Int, icon: ImageView) {
        if (singleBoarding.isExpanded){
            icon.setBackgroundResource(R.drawable.icon_hide)
            collapseParentRow(position)
        }
        else {
            icon.setBackgroundResource(R.drawable.icon_show)
            expandParentRow(position)
        }
    }

    private fun expandParentRow(position: Int) {
        val currentBoardingRow = list[position]
        val services = currentBoardingRow.subList
        currentBoardingRow.isExpanded = true
        var nextPosition = position

        if (currentBoardingRow.type == Constants.PARENT){

            services.forEach{
                service ->
                val parentModel = ParentData()
                parentModel.type = Constants.CHILD
                val subList: ArrayList<ChildData> = ArrayList()
                subList.add(service)
                parentModel.subList = subList
                list.add(++nextPosition, parentModel)
            }
            notifyDataSetChanged()
        }
    }

    private fun collapseParentRow(position: Int) {
        val currentBoardingRow = list[position]
        val services = currentBoardingRow.subList
        list[position].isExpanded = false

        if (list[position].type == Constants.PARENT){
            services.forEach{ _ ->
                list.removeAt(position + 1)
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = list.size

    class GroupViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val parentTV = row.findViewById(R.id.parent_Title) as TextView?
        val downIV = row.findViewById(R.id.down_iv) as ImageView
        val parentV = row.findViewById(R.id.view_parent) as RelativeLayout
    }

    class ChildViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val childTV = row.findViewById(R.id.child_Title) as TextView?
        val childV = row.findViewById(R.id.view_child) as RelativeLayout
    }

    override fun getItemViewType(position: Int): Int = list[position].type

}