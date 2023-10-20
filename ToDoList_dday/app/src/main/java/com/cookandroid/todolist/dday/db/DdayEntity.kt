package com.cookandroid.todolist.dday.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dday_table")
class DdayEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var ddayID : Int,
    @ColumnInfo(name = "title")
    var ddayName : String,
    @ColumnInfo(name = "date")
    var ddayDate : String

)

