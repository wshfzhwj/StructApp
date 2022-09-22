package com.saint.struct.bean.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore

@Entity(tableName = "student")
class Student (){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    var id: Int = 0
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    var name: String = ""
    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.TEXT)
    var age: String = ""

    constructor(name: String, age: String) : this() {
        this.name = name
        this.age = age
    }
}
