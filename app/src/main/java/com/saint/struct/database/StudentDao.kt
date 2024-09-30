package com.saint.struct.database

import androidx.room.*
import com.saint.struct.bean.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Insert
    fun insertStudent(student: Student)

    @Delete
    fun deleteStudent(student: Student)

    @Update
    fun updateStudent(student: Student)

    @Query("SELECT * FROM student")
    fun  getAll(): List<Student>

    @Query("SELECT * FROM student WHERE id = :id")
    fun getStudentById(id: Int): Student?

    @Query("DELETE FROM student")
    fun deleteAll()

    @Query("SELECT * FROM student")
    fun  getFlowAll(): Flow<List<Student>>
}