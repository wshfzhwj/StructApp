package com.saint.struct.repository

import com.saint.struct.bean.Student
import com.saint.struct.database.StudentDao
import com.saint.struct.tool.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class StudentRepository(private val dao: StudentDao) {

    suspend fun insertUser(student: Student) {
        withContext(Dispatchers.IO) {
            dao.insertStudent(student)
        }
    }

    suspend fun updateStudent(student: Student) {
        withContext(Dispatchers.IO) {
            dao.updateStudent(student)
        }
    }

    suspend fun deleteUser(student: Student) {
        withContext(Dispatchers.IO) {
            dao.deleteStudent(student)
        }
    }

    suspend fun getAllUsers(): List<Student> = withContext(Dispatchers.IO) {
        dao.getAll()
    }

    suspend fun getAllUsersFlow(): Flow<List<Student>> = withContext(Dispatchers.IO) {
        log("getAllUsersFlow")
        dao.getFlowAll()
    }

    suspend fun deleteAllUser() = withContext(Dispatchers.IO) {
        dao.deleteAll()
    }
}