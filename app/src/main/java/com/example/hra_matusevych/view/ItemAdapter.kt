package com.example.hra_matusevych.view

import com.example.hra_matusevych.model.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hra_matusevych.R

class ItemAdapter(val items: ArrayList<Item>) : RecyclerView.Adapter<ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row,parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setImageItem(items[position].name)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}