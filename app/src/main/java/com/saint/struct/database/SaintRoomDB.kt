package com.saint.struct.database

import android.content.Context
import androidx.room.Database
import com.saint.struct.bean.Student
import androidx.room.RoomDatabase
import androidx.room.Room

//@Database标签用于告诉系统这是Room数据库对象。entities属性用于指定该数据库有哪些表，若需建立多张表，以逗号相隔开。
//version属性用于指定数据库版本号，后续数据库的升级正是依据版本号来判断的。
// 该类需要继承自RoomDatabase，在类中，通过Room.databaseBuilder()结合单例设计模式，完成数据库的创建工作。
// 另外，我们创建的Dao对象，在这里以抽象方法的形式返回，只需一行代码即可。
@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class SaintRoomDB : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        private const val DATABASE_NAME = "my_db"

        @Volatile
        private var databaseInstance: SaintRoomDB? = null

        fun getInstance(context: Context): SaintRoomDB {
            databaseInstance ?: synchronized(this) {
                databaseInstance ?: Room
                    .databaseBuilder(
                        context.applicationContext,
                        SaintRoomDB::class.java,
                        DATABASE_NAME
                    ).build().also { databaseInstance = it }
            }
            return databaseInstance!!
        }
    }
}