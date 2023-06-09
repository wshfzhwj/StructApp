package com.saint.struct.ipc.aidl

import android.os.Parcel
import android.os.Parcelable

class Person(var name: String? = "") : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    fun readFromParcel(parcel:Parcel){
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Person(name=$name) hashcode = ${hashCode()}"
    }
}