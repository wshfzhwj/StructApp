package com.saint.struct.bean

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
    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.INTEGER)
    var age: Int = 0
) {

    @Ignore
    constructor(name: String, age: Int) : this(null, name, age)

}
