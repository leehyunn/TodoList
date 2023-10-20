package com.cookandroid.todolist.dday.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cookandroid.todolist.R
import com.cookandroid.todolist.todo.db.TodoEntity

class DdayAdapter(private val dataSet : List<DdayEntity>) {

    fun getDday(position : Int) : DdayEntity {
        var dday = dataSet[position]
        return dday
    }

}