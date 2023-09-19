package com.saint.struct.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.DeadObjectException
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkQuery
import com.saint.struct.databinding.LayoutAidlBinding
import com.saint.struct.ipc.aidl.IPersonManager
import com.saint.struct.ipc.aidl.Person
import com.saint.struct.service.MyWorker
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import java.util.concurrent.TimeUnit


class AidlActivity : BaseActivity() {

    private lateinit var binding: LayoutAidlBinding
    private lateinit var constraints: Constraints
    private val mainScope = MainScope()
    private var isConnect = false;


    companion object {
        const val TAG = "AidlActivity"
    }

    private var remoteServer: IPersonManager? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isConnect = false;
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            remoteServer = IPersonManager.Stub.asInterface(service)
            isConnect = true;
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "testManagerWorker Checking system3。。。。。。。。")
        binding = LayoutAidlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAddPerson.post(Runnable {
            Log.e(TAG, "binding.btnAddPerson")
        })
//下面这个写法无法触发点击事件
//        setContentView(R.layout.layout_aidl)
//        binding = DataBindingUtil.setContentView(this, R.layout.layout_aidl)
        initListener()
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
//            startCheckSystemWorker()
            testMainScope()
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
        getWorkInfoById(checkSystem.id)
//        getWorkInfoListFromTags("Test")
//      getWorkInfoListFromQuery(WorkQuery.Builder.fromIds(listOf(checkSystem.id)).build())
    }

    private fun getWorkInfoListFromTags(tag: String) {
        mainScope.launch(Dispatchers.IO) {
            println("Dispatchers.IO---------$coroutineContext[CoroutineName]")
            val result = async {
                WorkManager.getInstance(applicationContext)
                    .getWorkInfosLiveData(
                        androidx.work.WorkQuery.Builder.fromTags(kotlin.collections.listOf<kotlin.String?>(tag)).build()
                    )
            }
            withContext(Dispatchers.Main) {
                println("Dispatchers.Main---------$coroutineContext[CoroutineName]")
                result.await().observe(this@AidlActivity) { list ->
                    list.forEach {
                        when (it.state) {
                            WorkInfo.State.ENQUEUED -> Log.e(TAG, "ENQUEUED")
                            WorkInfo.State.RUNNING -> Log.e(TAG, "RUNNING")
                            WorkInfo.State.SUCCEEDED -> Log.e(TAG, "SUCCEEDED")
                            WorkInfo.State.FAILED -> Log.e(TAG, "FAILED")
                            WorkInfo.State.BLOCKED -> Log.e(TAG, "BLOCKED")
                            WorkInfo.State.CANCELLED -> Log.e(TAG, "CANCELLED")
                        }
                    }
                }
            }
        }

    }


    private fun getWorkInfoById(id: UUID) {
        val liveData = WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(id)
        liveData.observe(this@AidlActivity) {
            when (it.state) {
                WorkInfo.State.ENQUEUED -> Log.e(TAG, "ENQUEUED")
                WorkInfo.State.RUNNING -> Log.e(TAG, "RUNNING")
                WorkInfo.State.SUCCEEDED -> Log.e(TAG, "SUCCEEDED")
                WorkInfo.State.FAILED -> Log.e(TAG, "FAILED")
                WorkInfo.State.BLOCKED -> Log.e(TAG, "BLOCKED")
                WorkInfo.State.CANCELLED -> Log.e(TAG, "CANCELLED")
            }
        }
    }

    private fun getWorkInfoListFromQuery(query: WorkQuery) {
        WorkManager.getInstance(applicationContext)
            .getWorkInfosLiveData(query)
            .observe(this@AidlActivity) { list ->
                list.forEach {
                    when (it.state) {
                        WorkInfo.State.ENQUEUED -> Log.e(TAG, "ENQUEUED")
                        WorkInfo.State.RUNNING -> Log.e(TAG, "RUNNING")
                        WorkInfo.State.SUCCEEDED -> Log.e(TAG, "SUCCEEDED")
                        WorkInfo.State.FAILED -> Log.e(TAG, "FAILED")
                        WorkInfo.State.BLOCKED -> Log.e(TAG, "BLOCKED")
                        WorkInfo.State.CANCELLED -> Log.e(TAG, "CANCELLED")
                    }

                }
            }
    }

    private fun executePeriodicWork() {
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

    private fun testMainScope() {

//         创建一个默认参数的协程，其默认的调度模式为Main 也就是说该协程的线程环境是Main线程
        val job1 = mainScope.launch {
            // 这里就是协程体

            // 延迟1000毫秒  delay是一个挂起函数
            // 在这1000毫秒内该协程所处的线程不会阻塞
            // 协程将线程的执行权交出去，该线程该干嘛干嘛，到时间后会恢复至此继续向下执行
            delay(1000)
        }

        // 创建一个指定了调度模式的协程，该协程的运行线程为IO线程
        val job2 = mainScope.launch(Dispatchers.IO) {

            // 此处是IO线程模式

            // 切线程 将协程所处的线程环境切至指定的调度模式Main
            withContext(Dispatchers.Main) {
                // 现在这里就是Main线程了  可以在此进行UI操作了
            }
        }

        // 下面直接看一个例子： 从网络中获取数据  并更新UI
        // 该例子不会阻塞主线程
        mainScope.launch(Dispatchers.IO) {
            // 执行getUserInfo方法时会将线程切至IO去执行
            val userInfo = getUserInfo()
            // 获取完数据后 切至Main线程进行更新UI
            withContext(Dispatchers.Main) {
                // 更新UI
                println(userInfo)
            }
        }
    }

    /**
     * 获取用户信息 该函数模拟IO获取数据
     * @return String
     */
    private suspend fun getUserInfo(): String {
        return withContext(Dispatchers.IO) {
            delay(2000)
            "Kotlin"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //最后记得unbindService
        if (isConnect) {
            unbindService(serviceConnection)
            isConnect = false
        }
        mainScope.cancel()
    }
}