package com.saint.struct.database

import androidx.room.*
import com.saint.struct.bean.entity.Student

@Dao
interface StudentDao {
    @Insert
    fun insertStudent(student: Student?)

    @Delete
    fun deleteStudent(student: Student?)

    @Update
    fun updateStudent(student: Student?)

    @Query("SELECT * FROM student")
    fun  getAll(): List<Student?>?

    @Query("SELECT * FROM student WHERE id = :id")
    fun getStudentById(id: Int): Student?
}