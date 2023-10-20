package com.cookandroid.todolist.dday.db

import android.content.Context
import com.cookandroid.todolist.category.db.CategoryDatabase

class DdayRepository(context : Context) {

    private val ddayDB = CategoryDatabase.getDatabase(context)

    fun getData() = ddayDB.ddayDao().getData()

    fun getTitleData() = ddayDB.ddayDao().getTitleData()

    fun getDateData() = ddayDB.ddayDao().getDateData()

    fun insertData(title : String, date : String) = ddayDB.ddayDao().insert(DdayEntity(0, title, date))

    fun updateData(title : String, date : String) = ddayDB.ddayDao().update(DdayEntity(0, title, date))

    // fun deleteData() = ddayDB.ddayDao().delete()

}