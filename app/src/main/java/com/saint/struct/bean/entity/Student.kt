package com.saint.struct.bean.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore

@Entity(tableName = "student")
data class Student(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    var id: Int?,
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    var name: String = "",
    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.TEXT)
    var age: String = ""
) {

    @Ignore
    constructor(name: String, age: String) : this(null, name, age)

}
