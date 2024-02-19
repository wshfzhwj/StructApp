package com.saint.struct.ipc.binder

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.saint.struct.ipc.aidl.Person

class ServerService : Service() {
    private val mPeople: MutableList<Person> = ArrayList()
    override fun onCreate() {
        super.onCreate()
        mPeople.add(Person())
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private val mStub: BinderObj = object : BinderObj() {
        override fun addPerson(p: Person) {
            var p: Person? = p
            if (p == null) {
                p = Person()
            }
            mPeople.add(p)
        }

        override fun getPersonList(): List<Person> {
            return mPeople
        }
    }

    companion object {
        private const val TAG = "ServerService"
    }
}
