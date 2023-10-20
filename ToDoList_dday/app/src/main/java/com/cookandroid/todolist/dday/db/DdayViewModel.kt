package com.cookandroid.todolist.dday.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cookandroid.todolist.category.db.CategoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DdayViewModel(application: Application) : AndroidViewModel(application) {

    val context = getApplication<Application>().applicationContext
    val ddayDB = CategoryDatabase.getDatabase(context)

    val ddayRepository = DdayRepository(context)

    private var _ddayList = MutableLiveData<List<DdayEntity>>()
    val ddayList : LiveData<List<DdayEntity>>
        get() = _ddayList

//    private var _ddayTitleList = MutableLiveData<String>()
//    val ddayTitleList : LiveData<String>
//        get() = _ddayTitleList

//    private var _ddayDateList = MutableLiveData<String>()
//    val ddayDateList : LiveData<String>
//        get() = _ddayDateList

    fun getData() = viewModelScope.launch(Dispatchers.IO) {
        _ddayList.postValue(ddayRepository.getData())
    }

//    fun getTitleData() = viewModelScope.launch(Dispatchers.IO) {
//        _ddayTitleList.postValue(ddayRepository.getTitleData())
//    }
//
//    fun getDateData() = viewModelScope.launch(Dispatchers.IO) {
//        _ddayDateList.postValue(ddayRepository.getDateData())
//    }

    fun initialData(title : String, date : String) = viewModelScope.launch(Dispatchers.IO) {
        if(ddayRepository.getTitleData() == null) {
            ddayRepository.insertData(title, date)
        }
    }

    fun insertData(title : String, date : String) = viewModelScope.launch(Dispatchers.IO) {
        ddayRepository.insertData(title, date)
    }

    fun updateData(title : String, date : String) = viewModelScope.launch(Dispatchers.IO) {
        ddayRepository.updateData(title, date)
    }

}