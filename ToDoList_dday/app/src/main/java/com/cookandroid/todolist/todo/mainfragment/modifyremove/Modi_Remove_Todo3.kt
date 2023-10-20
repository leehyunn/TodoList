package com.cookandroid.todolist.todo.mainfragment.modifyremove

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cookandroid.todolist.R
import com.cookandroid.todolist.category.activity.MainActivity
import com.cookandroid.todolist.category.db.CategoryDatabase
import com.cookandroid.todolist.category.db.CategoryViewModel
import com.cookandroid.todolist.databinding.ActivityModiRemoveTodo2Binding
import com.cookandroid.todolist.databinding.ActivityModiRemoveTodo3Binding
import com.cookandroid.todolist.todo.db.TodoAdapter
import com.cookandroid.todolist.todo.db.TodoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Modi_Remove_Todo3 : AppCompatActivity() {

    private lateinit var binding : ActivityModiRemoveTodo3Binding

    lateinit var todoViewModel : TodoViewModel
    private lateinit var cateViewModel : CategoryViewModel

    var todoDB = CategoryDatabase.getDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_modi_remove_todo3)

        cateViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        todoViewModel.getCate3Data()

        val position = intent.getIntExtra("todoPosition3", 0)

        todoViewModel.todoList.observe(this, Observer {
            val todoAdapter = TodoAdapter(it)

            val todoItemTitle = todoAdapter.getTodoTitle(position)
            val todoItemDate = todoAdapter.getTodoDate(position)
            val todoItemCheck = todoAdapter.getTodoCheck(position)

            binding.todoTitle.setText(todoItemTitle)
            binding.todoDate.setText(todoItemDate)
            binding.todoCheck.isChecked = todoItemCheck
            binding.spinner.post(Runnable {
                binding.spinner.setSelection(2)
            })

            val todoItemPriority = todoAdapter.getTodoPriorty(position)

            when(todoItemPriority) {
                "상" -> binding.radio1.isChecked = true
                "중" -> binding.radio2.isChecked = true
                "하" -> binding.radio3.isChecked = true
            }

            val todoItemCategory = todoAdapter.getTodoCategory(position)

        })

        var priority : String = ""
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radio1 -> {
                    priority = "상"
                }

                R.id.radio2 -> {
                    priority = "중"
                }

                R.id.radio3 -> {
                    priority = "하"
                }
            }
        }

        lateinit var category : String
        var categoryPosition : Int = 0

        cateViewModel.getTitleData()
        cateViewModel.titleList.observe(this, Observer {
            var title1 = cateViewModel.getItemData(0)
            var title2 = cateViewModel.getItemData(1)
            var title3 = cateViewModel.getItemData(2)
            var title4 = cateViewModel.getItemData(3)
            var title5 = cateViewModel.getItemData(4)
            Log.d("abc", title1)
            var categorySpinnerList = arrayListOf(title1, title2, title3, title4, title5)

            var adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorySpinnerList)

        val spinner = findViewById<Spinner>(R.id.spinner)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                category = categorySpinnerList.get(position)
                categoryPosition = position+1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(baseContext, "카테고리를 선택해주세요.", Toast.LENGTH_LONG).show()
            }
        }
    })

        binding.update.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                val result = todoDB.todoDao().getCate3Data()
                val todoEntity = result[position]

                todoEntity.todoTitle = binding.todoTitle.text.toString()
                todoEntity.todoDate = binding.todoDate.text.toString()
                todoEntity.todoCheck = binding.todoCheck.isChecked
                todoEntity.todoPriority = priority
                todoEntity.todoCategory = categoryPosition

                todoDB.todoDao().update(todoEntity)
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.delete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val result = todoDB.todoDao().getCate3Data()
                val todoEntity = result[position]

                todoDB.todoDao().delete(todoEntity)
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}