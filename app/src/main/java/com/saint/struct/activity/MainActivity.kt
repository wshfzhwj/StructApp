package com.saint.struct.activity

import android.content.*
import android.graphics.BitmapFactory
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.saint.biometiriclib.BiometricPromptManager
import com.saint.biometiriclib.BiometricPromptManager.OnBiometricIdentifyCallback
import com.saint.struct.R
import com.saint.struct.bean.InnerClass
import com.saint.struct.bean.InnerClass.Hello
import com.saint.struct.bean.Node
import com.saint.struct.bean.User
import com.saint.struct.bean.entity.Student
import com.saint.struct.database.SaintRoomDB
import com.saint.struct.databinding.LayoutMainBinding
import com.saint.struct.service.JobTestService
import com.saint.struct.service.MessengerService
import com.saint.struct.task.QueryStudentTask
import com.saint.struct.viewmodel.MainActivityViewModel
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MainActivity : BaseActivity() {
    private var mService: Messenger? = null
    private val mRetrofit: Retrofit? = null
    private lateinit var mLayoutBinding: LayoutMainBinding
    private var mManager: BiometricPromptManager? = null
    private var studentList = mutableListOf<Student>()
    private lateinit var mRoomDatabase: SaintRoomDB

    companion object {
        const val EXTRA_KEY_SERVICE = "extra_key_service"
        private val TAG = MainActivity::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLayoutBinding = DataBindingUtil.setContentView(this, R.layout.layout_main)
        requestPermission()
        setListener()
    }

    private fun setListener() {
        mLayoutBinding.helloBtn.setOnClickListener {
//            val mUrl = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg"
            val mGif = "http://p1.pstatp.com/large/166200019850062839d3"
            Glide.with(this@MainActivity).load(mGif).into(mLayoutBinding.roundImage)
        }
        mLayoutBinding.helloBtn2.setOnClickListener {
            startActivity(Intent().setClass(this@MainActivity, PageKtActivity::class.java))
        }
        mLayoutBinding.helloBtn3.setOnClickListener {
            startActivity(Intent().setClass(this@MainActivity, PageRxActivity::class.java))
        }
        mLayoutBinding.helloBtn4.setOnClickListener {
            startActivity(Intent().setClass(this@MainActivity, PageOldActivity::class.java))
        }
        mLayoutBinding.helloBtn5.setOnClickListener { testDB() }
    }

    private fun testDB() {
            mRoomDatabase = SaintRoomDB.getInstance(this@MainActivity)!!
        thread {
            mRoomDatabase.studentDao()!!.insertStudent(Student("zhangsan", "110"))
            mRoomDatabase.studentDao()!!.insertStudent(Student("lisi", "12"))
            mRoomDatabase.studentDao()!!.updateStudent(Student("lisi", "14"))
            studentList.clear()
            studentList.addAll(mRoomDatabase.studentDao()!!.getAll()!! as Collection<Student>)
            Log.e(TAG, "db>>>>>>>>>>>" + studentList.size);
        }
    }

    private fun testExtension() {
        getPreferences(Context.MODE_PRIVATE).modify {
            putBoolean("aaa", true)
            putFloat("bbb", 10f)
        }
    }

    fun SharedPreferences.modify(block: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        editor.block()
        editor.apply()
    }


    fun testLooper() {
        val looper = Looper.getMainLooper()
        val handlerThread = HandlerThread("test")
        handlerThread.start()
        val threadThread = handlerThread.looper
        val me = looper.queue
        val queue = threadThread.queue
        println("me = $me")
        println("queue = $queue")
        handlerThread.quitSafely()
    }

    private fun testThreadPool() {
        val service = Executors.newScheduledThreadPool(10)
        service.schedule(ScheduleCallable(), 10, TimeUnit.SECONDS)
        service.scheduleAtFixedRate(ScheduleRunnable(), 10, 10, TimeUnit.SECONDS)
        service.scheduleWithFixedDelay(ScheduleRunnable(), 10, 10, TimeUnit.SECONDS)
    }

    private fun testInner() {
        val world = InnerClass().World()
        world.hello()
        val hello = Hello()
        Hello.world()
        hello.hello()
    }

    private fun testService() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        }else{
//            startService(intent);
//        }
        JobTestService.enqueueWork(this, intent)
        //        startService(intent);
//                bindService(new Intent(MainActivity.this, MessengerService.class), connection, BIND_AUTO_CREATE);
    }

    private fun testFinger() {
        mManager = BiometricPromptManager.from(this)
        val stringBuilder = StringBuilder()
        stringBuilder.append("SDK version is " + Build.VERSION.SDK_INT)
        stringBuilder.append("\n")
        stringBuilder.append("isHardwareDetected : " + mManager!!.isHardwareDetected())
        stringBuilder.append("\n")
        stringBuilder.append("hasEnrolledFingerprints : " + mManager!!.hasEnrolledFingerprints())
        stringBuilder.append("\n")
        stringBuilder.append("isKeyguardSecure : " + mManager!!.isKeyguardSecure())
        stringBuilder.append("\n")
        mLayoutBinding.tvDesc.text = stringBuilder.toString()
        if (mManager!!.isBiometricPromptEnable()) {
            mManager!!.authenticate(object : OnBiometricIdentifyCallback {
                override fun onUsePassword() {
                    Toast.makeText(this@MainActivity, "onUsePassword", Toast.LENGTH_SHORT).show()
                }

                override fun onSucceeded() {
                    Toast.makeText(this@MainActivity, "onSucceeded", Toast.LENGTH_SHORT).show()
                }

                override fun onFailed() {
                    Toast.makeText(this@MainActivity, "onFailed", Toast.LENGTH_SHORT).show()
                }

                override fun onError(code: Int, reason: String) {
                    Toast.makeText(this@MainActivity, "onError", Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() {
                    Toast.makeText(this@MainActivity, "onCancel", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun testBitmapMemory() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.a)
        Log.d(TAG, "memory getAllocationByteCount = " + bitmap.allocationByteCount)
        Log.d(TAG, "memory getByteCount = " + bitmap.byteCount)
    }

    private fun testConflict() {
        Log.i(TAG, "This method is for testing conflict from Q")
        //        Log.d(TAG,"This method is for testing git conflict");
    }

    var connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d(TAG, "onServiceConnected")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "onServiceDisconnected")
        }
    }

    private fun testViewModel() {
        val model = ViewModelProviders.of(this).get(
            MainActivityViewModel::class.java
        )
    }

    fun testEquals() {
        val user = User("a", "a")
        val user2 = User("a", "a")
        Log.e(TAG, "value 1= " + (user === user2))
        Log.e(TAG, "value 2= " + (user == user2))
    }

    private fun testGlide() {
        val url = "https://alifei04.cfp.cn/creative/vcg/800/version23/VCG41175510742.jpg"
        Glide.with(this).load(url).into(mLayoutBinding.roundImage)
    }

    private fun testFunc() {
//        new InterviewFunc().lightFunc();
        val Head: Node<*>? = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e(TAG, "onSaveInstanceState")
    }

    private fun startAidl() {
        startActivity(Intent().setClass(this, AidlActivity::class.java))
    }

    val typeClass: Unit
        get() {
            val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
            type.toString()
        }

    private fun init() {
        val intent = Intent(this@MainActivity, MessengerService::class.java)
        bindService(intent, conn, BIND_AUTO_CREATE)
    }

    fun handleVue() {
//        String url = "http://webapp.1/a/b/c";
        val url = "qax://webapp.1/a/b/c"
        //        String url = "file:///android_asset/dist/index.html";
        //  String url = "file:///android_asset/test.html";
        WebActivity.startActivity(this@MainActivity, "title", url)
    }

    private fun requestPermission() {
        val permissions = Permission.Group.STORAGE
        AndPermission.with(this)
            .runtime()
            .permission(permissions)
            .onGranted {
                Toast.makeText(this, "get it", Toast.LENGTH_SHORT).show()
            }
            .onDenied {
                Toast.makeText(this, "请打开权限，否则无法获取本地文件", Toast.LENGTH_SHORT).show()
            }.start()
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 1) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //用户同意，执行操作
//                Toast.makeText(this@MainActivity, "get it", Toast.LENGTH_LONG).show()
//            } else {
//                //用户不同意，向用户展示该权限作用
//                if (ActivityCompat.shouldShowRequestPermissionRationale(
//                        this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    )
//                ) {
//                    AlertDialog.Builder(this@MainActivity)
//                        .setMessage("申请权限")
//                        .setPositiveButton("OK") { dialog1: DialogInterface?, which: Int ->
//                            ActivityCompat.requestPermissions(
//                                this, arrayOf(
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                                ),
//                                1
//                            )
//                        }
//                        .setNegativeButton("Cancel", null)
//                        .create()
//                        .show()
//                }
//            }
//        }
//    }

    private val conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mService = Messenger(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {}
    }

    fun transBitmap() {
        val bundle = Bundle()
        bundle.putBinder("", null)
    }

    private val messengerHandler: Handler = object : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }
    }
    private val mGetMessager = Messenger(messengerHandler)
    override fun onDestroy() {
        unbindService(conn)
        super.onDestroy()
    }

    internal inner class ThreadHandler : Thread() {
        override fun run() {
            super.run()
            Looper.prepare()
            val handler = Handler()
            Log.e(TAG, "handler id = " + handler.looper.thread.id)
            Log.e(TAG, "handler id = $id")
            Looper.loop()
        }
    }

    internal inner class ScheduleCallable : Callable<Any?> {
        @Throws(Exception::class)
        override fun call(): Any? {
            Log.e(TAG, "ScheduleCallable call...")
            return true
        }
    }

    internal inner class ScheduleRunnable : Runnable {
        override fun run() {
            Log.e(TAG, "ScheduleRunnable run...")
        }
    }


}

//private fun <E> MutableList<E>.addAll(elements: List<E?>) {
//    elements.forEach { this.add(it!!) }
//}
