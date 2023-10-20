package com.cookandroid.todolist.dday.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cookandroid.todolist.todo.db.TodoEntity

@Dao
interface DdayDao {

    @Query("SELECT * FROM dday_table")
    fun getData(): List<DdayEntity>

    @Query("SELECT title FROM dday_table WHERE id = 1")
    fun getTitleData() : String

    @Query("SELECT date FROM dday_table WHERE id = 1")
    fun getDateData() : String

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(text : DdayEntity)

    @Update
    fun update(text : DdayEntity)

    @Delete
    fun delete(text : DdayEntity)

}