package com.example.hra_matusevych.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hra_matusevych.R

class LogHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var textLog : TextView?

    init {
        textLog = view.findViewById(R.id.row)
    }

    fun setLogData(log: String) {
        textLog?.text = log
    }
}