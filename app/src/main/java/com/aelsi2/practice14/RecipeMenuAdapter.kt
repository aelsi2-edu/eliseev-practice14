package com.aelsi2.practice14

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeMenuAdapter(private val menuEntries : ArrayList<RecipeMenuEntry>,
                        private val onItemClickListener: OnItemClickListener? = null)
    : RecyclerView.Adapter<RecipeMenuAdapter.ViewHolder>() {
    inner class ViewHolder constructor(itemView: View, private val onItemClickListener: OnItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        private var index : Int = 0
        private val icon : ImageView
        private val name : TextView
        init {
            icon = itemView.findViewById(R.id.menu_entry_icon)
            name = itemView.findViewById(R.id.menu_entry_name)
            itemView.isClickable = true
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(index)
            }
        }
        fun setIndex(int : Int) {
            index = int
        }
        fun setIcon(drawable : Drawable) {
            icon.setImageDrawable(drawable)
        }
        fun setName(string : String) {
            name.text = string
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_entry_item, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIndex(position)
        holder.setIcon(menuEntries[position].Icon)
        holder.setName(menuEntries[position].Name)
    }

    override fun getItemCount(): Int {
        return menuEntries.size
    }
}