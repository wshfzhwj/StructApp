package com.saint.struct.task;

import android.os.AsyncTask;
import android.util.Log;

import androidx.room.RoomDatabase;

import com.saint.struct.bean.entity.Student;
import com.saint.struct.database.SaintRoomDB;

import java.util.List;

public class QueryStudentTask extends AsyncTask<Void, Void, Void> {
    public static final String TAG = QueryStudentTask.class.getSimpleName();
    SaintRoomDB mRoomDatabase;
    List<Student> list;
    public QueryStudentTask(SaintRoomDB room , List<Student> list) {
            this.mRoomDatabase = room;
            this.list = list;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        mRoomDatabase.studentDao().insertStudent(new Student("zhangsan", "11"));
        mRoomDatabase.studentDao().insertStudent(new Student("lisi", "12"));
        mRoomDatabase.studentDao().updateStudent(new Student("lisi", "14"));
        list.clear();
        list.addAll(mRoomDatabase.studentDao().getAll());
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        Log.e(TAG,"db>>>>>>>>>>>" + list.get(0).getName());
        Log.e(TAG,"db>>>>>>>>>>>" + list.get(1).getName());
        Log.e(TAG,"db>>>>>>>>>>>" + list.size());
    }

}