package com.sf.struct.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sf.struct.R
import com.sf.struct.databinding.LayoutAidlBinding
import com.sf.struct.practice.aidl.IPersonManager
import com.sf.struct.practice.aidl.Person

class AidlActivity : AppCompatActivity() {

    private lateinit var binding: LayoutAidlBinding

    companion object {
        const val TAG = "sf_aidl"
    }

    private var remoteServer: IPersonManager? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            remoteServer = IPersonManager.Stub.asInterface(service)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_aidl)
        binding = LayoutAidlBinding.inflate(layoutInflater)
        binding.btnConnect.setOnClickListener {
            connectService()
        }
        binding.btnGetPerson.setOnClickListener {
            getPerson()
        }
        binding.btnAddPerson.setOnClickListener {
            addPerson()
        }
    }

    private fun connectService() {
        val intent = Intent()
        //action 和 package(app的包名)
        intent.action = "com.sf.back.aidl.Server.Action"
        intent.setPackage("com.sf.back")
        val bindServiceResult = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        Log.e(TAG,"connectService $bindServiceResult")
    }

    private fun addPerson() {
        //客户端调服务端方法时,需要捕获以下几个异常:
        //RemoteException 异常：
        //DeadObjectException 异常：连接中断时会抛出异常；
        //SecurityException 异常：客户端和服务端中定义的 AIDL 发生冲突时会抛出异常；
        try {
            val addPersonResult = remoteServer?.addPerson(Person("盖伦"))
            Log.e(TAG,"addPerson result = $addPersonResult")
        } catch (e: RemoteException) {
            e.printStackTrace()
        } catch (e: DeadObjectException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun getPerson() {
        val personList = remoteServer?.personList
        Log.e(TAG, "person 列表 $personList")
    }

    override fun onDestroy() {
        super.onDestroy()
        //最后记得unbindService
        unbindService(serviceConnection)
    }
}