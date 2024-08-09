package com.example.hra_matusevych.view

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.hra_matusevych.Icons
import com.example.hra_matusevych.R

class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var imageView : ImageView?

    init {
        imageView = view.findViewById(R.id.imageItem)
    }

    fun setImageItem(item:String){
        imageView!!.setImageResource(Icons.valueOf(item).imgResource)
    }
}