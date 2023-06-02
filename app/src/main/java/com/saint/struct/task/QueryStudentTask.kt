package com.saint.struct.task

import com.saint.struct.database.SaintRoomDB
import com.saint.struct.bean.entity.Student
import android.os.AsyncTask
import android.util.Log
import com.saint.struct.task.QueryStudentTask

abstract class QueryStudentTask(var mRoomDatabase: SaintRoomDB, var list: MutableList<Student?>) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg arg0: Void): Void? {
        mRoomDatabase.studentDao().insertStudent(Student("zhangsan", "11"))
        mRoomDatabase.studentDao().insertStudent(Student("lisi", "12"))
        mRoomDatabase.studentDao().updateStudent(Student("lisi", "14"))
        list.clear()
        list.addAll(mRoomDatabase.studentDao()!!.getAll()!!)
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        Log.e(TAG, "db>>>>>>>>>>>" + list[0]!!.name)
        Log.e(TAG, "db>>>>>>>>>>>" + list[1]!!.name)
        Log.e(TAG, "db>>>>>>>>>>>" + list.size)
    }

    companion object {
        val TAG : String = QueryStudentTask::class.java.simpleName
    }
}