package com.cookandroid.todolist.todo.db

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cookandroid.todolist.R
import com.cookandroid.todolist.category.db.CategoryDatabase
import com.cookandroid.todolist.category.db.CategoryEntity
import com.cookandroid.todolist.databinding.TodoRvItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class TodoAdapter(private val dataSet : List<TodoEntity>) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    interface TodoItemClick {
        fun onClick(view : View, position : Int)
    }

    fun getTodoTitle(position : Int) : String{
        var todoItemName = dataSet[position].todoTitle
        return todoItemName
    }
    fun getTodoDate(position : Int) : String{
        var todoItemDate = dataSet[position].todoDate
        return todoItemDate
    }
    fun getTodoPriorty(position : Int) : String {
        var todoItemPriority = dataSet[position].todoPriority
        return todoItemPriority
    }
    fun getTodoCategory(position : Int) : Int {
        var todoItemCategory = dataSet[position].todoCategory
        return todoItemCategory
    }
    fun getTodoCheck(position : Int) : Boolean {
        var todoItemCheck = dataSet[position].todoCheck
        return todoItemCheck
    }

    var itemClick : TodoItemClick? = null

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val checkBox : CheckBox = view.findViewById(R.id.rv_todo_check)
        val textView : TextView = view.findViewById(R.id.rv_todo_title)
        val date : TextView = view.findViewById(R.id.rv_todo_date)
        val priority : TextView = view.findViewById(R.id.rv_todo_priority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_rv_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(itemClick != null) {
            holder?.itemView?.setOnClickListener { v ->
                itemClick?.onClick(v,position)
            }
        }

        holder.checkBox.isChecked = dataSet[position].todoCheck
        holder.textView.text = dataSet[position].todoTitle
        holder.date.text = dataSet[position].todoDate
        holder.priority.text = dataSet[position].todoPriority
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}