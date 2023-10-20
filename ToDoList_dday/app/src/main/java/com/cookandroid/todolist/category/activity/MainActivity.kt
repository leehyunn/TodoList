package com.cookandroid.todolist.category.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.DateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cookandroid.todolist.R
import com.cookandroid.todolist.category.db.CategoryAdapter
import com.cookandroid.todolist.category.db.CategoryDatabase
import com.cookandroid.todolist.category.db.CategoryViewModel
import com.cookandroid.todolist.databinding.ActivityMainBinding
import com.cookandroid.todolist.dday.db.DdayAdapter
import com.cookandroid.todolist.dday.db.DdayDao
import com.cookandroid.todolist.dday.db.DdayEntity
import com.cookandroid.todolist.dday.db.DdayRepository
import com.cookandroid.todolist.dday.db.DdayViewModel
import com.cookandroid.todolist.todo.Todo_Input
import com.cookandroid.todolist.todo.db.TodoAdapter
import com.cookandroid.todolist.todo.mainfragment.Fragment1
import com.cookandroid.todolist.todo.mainfragment.Fragment2
import com.cookandroid.todolist.todo.mainfragment.Fragment3
import com.cookandroid.todolist.todo.mainfragment.Fragment4
import com.cookandroid.todolist.todo.mainfragment.Fragment5
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var cateViewModel : CategoryViewModel

    private lateinit var ddayEntity: DdayEntity
    private lateinit var ddayRepository: DdayRepository
    private lateinit var ddayViewModel : DdayViewModel
    private lateinit var ddayDao: DdayDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        cateViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        ddayViewModel = ViewModelProvider(this).get(DdayViewModel::class.java)

        val cateDB = CategoryDatabase.getDatabase(this)

        val view = binding.root
        setContentView(view)

        // 카테고리 관리 클릭 이벤트
        binding.detail1.setOnClickListener {
            val intent = Intent(this, Content_Category::class.java)
            startActivity(intent)
        }
        // 달력 버튼 클릭 이벤트
        binding.calendar.setOnClickListener {
            val intent = Intent(this, Calender::class.java)
            startActivity(intent)
        }
        // 투두 추가 버튼 클릭 이벤트
        binding.todoInsert.setOnClickListener {
            val intent = Intent(this, Todo_Input::class.java)
            startActivity(intent)
        }

        // 카테고리 분류 제목
        var cate1 = binding.textView1
        var cate2 = binding.textView2
        var cate3 = binding.textView3
        var cate4 = binding.textView4
        var cate5 = binding.textView5

        cateViewModel.getTitleData()
        cateViewModel.titleList.observe(this, Observer {
            var title1 = cateViewModel.getItemData(0)
            Log.d("category", title1)
            cate1.setText(title1)

            var title2 = cateViewModel.getItemData(1)
            Log.d("category", title2)
            cate2.setText(title2)

            var title3 = cateViewModel.getItemData(2)
            Log.d("category", title3)
            cate3.setText(title3)

            var title4 = cateViewModel.getItemData(3)
            Log.d("category", title4)
            cate4.setText(title4)

            var title5 = cateViewModel.getItemData(4)
            Log.d("category", title5)
            cate5.setText(title5)

        })

        // 날짜 저장 포맷
        val dateFormat = "yyyyMMdd"
        val simpleDateFormat = SimpleDateFormat(dateFormat)

        // 디데이 초기화
        val initialTitle = "D-Day"
        val initialDate = Date(System.currentTimeMillis())
        val initialDateSimple = simpleDateFormat.format(initialDate)
        ddayViewModel.initialData(initialTitle, initialDateSimple)

        // db에서 디데이 정보 가져오기 + 화면에 보여주기
        ddayViewModel.getData()
        ddayViewModel.ddayList.observe(this, Observer {
            val ddayAdapter = DdayAdapter(it)
            val ddayData = ddayAdapter.getDday(0)

            val ddayCount = ddayData.ddayDate.toInt() - initialDateSimple.toInt()

            if(ddayCount >= 0) {
                Log.d("dday", ddayCount.toString())
                binding.ddayTitle.setText(ddayData.ddayName)
                binding.ddayCount.setText("D-" + ddayCount.toString())
            }
            else {
                binding.ddayTitle.setText(ddayData.ddayName)
                binding.ddayCount.setText("D-" + 0)
            }
        })

        // 디데이 설정
        binding.ddayTitle.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dday_dialog, null)
            val builder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("디데이 설정")

            val alertDialog = builder.show()

            // 디데이 제목 설정, 디데이 제목, 날짜 변수 선언
            var ddayTitle = alertDialog.findViewById<EditText>(R.id.ddayTitle)
            var ddayDate = ""

            // 디데이 날짜 설정
            alertDialog.findViewById<Button>(R.id.ddayDateSelectBtn)?.setOnClickListener {

                val today = GregorianCalendar()
                val year : Int = today.get(Calendar.YEAR)
                val month : Int = today.get(Calendar.MONTH)
                val day : Int = today.get(Calendar.DATE)

                DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(
                        view: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {
                        val selectDate = Date(year, month, dayOfMonth)
                        val selectDateSimple : String = simpleDateFormat.format(selectDate)
                        val saveDate = selectDateSimple.toInt() - 19000000
                        if(saveDate < initialDateSimple.toInt()) {
                            Toast.makeText(baseContext, "미래 날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            alertDialog.findViewById<TextView>(R.id.ddayDate)
                                ?.setText(saveDate.toString())
                            ddayDate = saveDate.toString()
                        }
                    }
                }, year, month, day).show()

            }

            // 디데이 저장 버튼 클릭 시
            alertDialog.findViewById<Button>(R.id.ddaySaveBtn)?.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {

                    val result = cateDB.ddayDao().getData()
                    val ddayEntity = result[0]
                    ddayEntity.ddayName = ddayTitle?.text.toString()
                    ddayEntity.ddayDate = ddayDate

                    cateDB.ddayDao().update(ddayEntity)
                }

                alertDialog.dismiss()
                ddayViewModel.getData()
                ddayViewModel.ddayList.observe(this, Observer {
                    val ddayAdapter = DdayAdapter(it)
                    val ddayData = ddayAdapter.getDday(0)
                    val ddayCount = ddayData.ddayDate.toInt() - initialDateSimple.toInt()
                    Log.d("dday", ddayCount.toString())
                    binding.ddayTitle.setText(ddayData.ddayName)
                    binding.ddayCount.setText("D-" + ddayCount.toString())
                })
            }

        }

        // 카테고리 별로 목록 분류
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame1, Fragment1())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame2, Fragment2())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame3, Fragment3())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame4, Fragment4())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame5, Fragment5())
            .commit()


    }
}