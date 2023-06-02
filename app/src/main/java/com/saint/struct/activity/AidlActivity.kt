package com.saint.struct.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.DeadObjectException
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkQuery
import androidx.work.WorkQuery.Builder.fromTags
import com.saint.struct.databinding.LayoutAidlBinding
import com.saint.struct.practice.aidl.IPersonManager
import com.saint.struct.practice.aidl.Person
import com.saint.struct.service.MyWorker
import java.util.concurrent.TimeUnit


class AidlActivity : BaseActivity() {

    private lateinit var binding: LayoutAidlBinding
    lateinit var constraints: Constraints

    companion object {
        const val TAG = "AidlActivity"
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
        Log.e(TAG, "testManagerWorker Checking system3。。。。。。。。")
        binding = LayoutAidlBinding.inflate(layoutInflater)
        setContentView(binding.root)
//下面这个写法无法触发点击事件
//        setContentView(R.layout.layout_aidl)
//        binding = DataBindingUtil.setContentView(this, R.layout.layout_aidl)
        initListener();
    }

    private fun initListener() {
        binding.btnConnect.setOnClickListener {
            Log.e(TAG, "btnConnect 。。。。。。。。")
            connectService()
        }
        binding.btnGetPerson.setOnClickListener {
            getPerson()
        }
        binding.btnAddPerson.setOnClickListener {
            addPerson()
        }
        binding.btnTestWorker.setOnClickListener {
            Log.e(TAG, "startCheckSystemWorker click")
            startCheckSystemWorker()
        }
    }

    private fun startCheckSystemWorker() {
        Log.e(TAG, "startCheckSystemWorker 。。。。。。。。")
        constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED) // 需要联网
            .build()

        val checkSystem = OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constraints)
            .addTag("Test")
            .build()

        WorkManager.getInstance(applicationContext).enqueue(checkSystem)

//        val liveData = WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(checkSystem.id)
//        liveData.observe(this) {
//            when (it.state) {
//                WorkInfo.State.ENQUEUED -> Log.e(TAG, "ENQUEUED");
//                WorkInfo.State.RUNNING -> Log.e(TAG, "RUNNING");
//                WorkInfo.State.SUCCEEDED -> Log.e(TAG, "SUCCEEDED");
//                WorkInfo.State.FAILED -> Log.e(TAG, "FAILED");
//                WorkInfo.State.BLOCKED -> Log.e(TAG, "BLOCKED");
//                WorkInfo.State.CANCELLED -> Log.e(TAG, "CANCELLED");
//            }
//        }


        WorkManager.getInstance(applicationContext).getWorkInfosLiveData(WorkQuery.Builder.fromTags(listOf<String?>("Test")).build()).observe(this) { list ->
//        WorkManager.getInstance(applicationContext).getWorkInfosByTagLiveData("Test").observe(this) { list ->
            list.forEach {
                    when (it.state) {
                        WorkInfo.State.ENQUEUED -> Log.e(TAG, "ENQUEUED");
                        WorkInfo.State.RUNNING -> Log.e(TAG, "RUNNING");
                        WorkInfo.State.SUCCEEDED -> Log.e(TAG, "SUCCEEDED");
                        WorkInfo.State.FAILED -> Log.e(TAG, "FAILED");
                        WorkInfo.State.BLOCKED -> Log.e(TAG, "BLOCKED");
                        WorkInfo.State.CANCELLED -> Log.e(TAG, "CANCELLED");
                }

            }
        }
    }

    public fun executePeriodicWork() {
        constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED) // 需要联网
            .build()
        val workRequest = PeriodicWorkRequestBuilder<MyWorker>(1, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }

    private fun connectService() {
        Log.e(TAG, "connectService...")
        val intent = Intent()
        //action 和 package(app的包名)
        intent.action = "com.saint.struct.aidl.Server.Action"
        intent.setPackage("com.saint.struct")
        val bindServiceResult = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        Log.e(TAG, "connectService $bindServiceResult")
    }

    private fun addPerson() {
        Log.e(TAG, "addPerson...")
        //客户端调服务端方法时,需要捕获以下几个异常:
        //RemoteException 异常：
        //DeadObjectException 异常：连接中断时会抛出异常；
        //SecurityException 异常：客户端和服务端中定义的 AIDL 发生冲突时会抛出异常；
        try {
            val addPersonResult = remoteServer?.addPerson(Person("盖伦"))
            Log.e(TAG, "addPerson result = $addPersonResult")
        } catch (e: RemoteException) {
            e.printStackTrace()
        } catch (e: DeadObjectException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun getPerson() {
        Log.e(TAG, "getPerson...")
        val personList = remoteServer?.personList
        Log.e(TAG, "person 列表 $personList")
    }

    override fun onDestroy() {
        super.onDestroy()
        //最后记得unbindService
        unbindService(serviceConnection)
    }
}