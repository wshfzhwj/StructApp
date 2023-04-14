package com.saint.struct.fragment

import android.content.*
import android.graphics.BitmapFactory
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.saint.struct.R
import com.saint.struct.activity.AidlActivity
import com.saint.struct.activity.WebActivity
import com.saint.struct.bean.InnerClass
import com.saint.struct.bean.entity.Student
import com.saint.struct.databinding.FragmentMainBinding
import com.saint.struct.service.JobTestService
import com.saint.struct.service.MessengerService
import com.saint.struct.viewmodel.MainFragmentViewModel
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of requireActivity() fragment.
 */

class MainFragment : BaseFragment() {
    private var mService: Messenger? = null
    private val mRetrofit: Retrofit? = null
    var studentList = mutableListOf<Student>()
    private lateinit var mFragmentMainBinding: FragmentMainBinding
    private lateinit var viewModel: MainFragmentViewModel

    companion object {
        const val EXTRA_KEY_SERVICE = "extra_key_service"
        private val TAG = MainFragment::class.java.name
    }

    override fun getFragmentLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun doInit() {
        viewModel = MainFragmentViewModel(studentList)
        mFragmentMainBinding = fragmentBinding as FragmentMainBinding
        init()
        requestPermission()
    }

    fun testGif() {
        viewModel.testGif(this, mFragmentMainBinding.gifImage)
    }

    fun testGlide() {
        viewModel.testGlide(this, mFragmentMainBinding.roundImage)
    }

    fun testDB() {
        viewModel.testDB(requireActivity())
    }

    fun testFinger() {
        viewModel.testFinger(requireActivity(), mFragmentMainBinding.tvDesc)
    }

    private fun testExtension() {
        requireActivity().getPreferences(Context.MODE_PRIVATE).modify {
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
        val hello = InnerClass.Hello()
        InnerClass.Hello.world()
        hello.hello()
    }

    private fun testService() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        }else{
//            startService(intent);
//        }
        JobTestService.enqueueWork(requireActivity(), requireActivity().intent)
        //        startService(intent);
//                bindService(new Intent(MainActivity.requireActivity(), MessengerService.class), connection, BIND_AUTO_CREATE);
    }


    private fun testBitmapMemory() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.a)
        Log.d(TAG, "memory getAllocationByteCount = " + bitmap.allocationByteCount)
        Log.d(TAG, "memory getByteCount = " + bitmap.byteCount)
    }

    private fun testConflict() {
        Log.i(TAG, "requireActivity() method is for testing conflict from Q")
        //        Log.d(TAG,"requireActivity() method is for testing git conflict");
    }

    var conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d(TAG, "onServiceConnected")
            mService = Messenger(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "onServiceDisconnected")
        }
    }

    override fun onDestroy() {
        requireActivity().unbindService(conn)
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e(TAG, "onSaveInstanceState")
    }

    private fun startAidl() {
        startActivity(Intent().setClass(requireActivity(), AidlActivity::class.java))
    }

    val typeClass: Unit
        get() {
            val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
            type.toString()
        }

    private fun init() {
        setToolbar()
        bindService()
        mFragmentMainBinding.handle = this
    }

    private fun setToolbar() {
        (fragmentBinding as FragmentMainBinding).layoutAppBar.titleBar.text = "首页"
    }

    private fun bindService() {
        val intent = Intent(requireActivity(), MessengerService::class.java)
        requireActivity().bindService(intent, conn, AppCompatActivity.BIND_AUTO_CREATE)
    }

    fun startWebActivity() {
//        String url = "http://webapp.1/a/b/c";
        val url = "qax://webapp.1/a/b/c"
        //        String url = "file:///android_asset/dist/index.html";
        //  String url = "file:///android_asset/test.html";
        WebActivity.startActivity(requireActivity(), "title", url)
    }

    private fun requestPermission() {
        val permissions = Permission.Group.STORAGE
        AndPermission.with(requireActivity())
            .runtime()
            .permission(permissions)
            .onGranted {
                Toast.makeText(requireActivity(), "get it", Toast.LENGTH_SHORT).show()
            }
            .onDenied {
                Toast.makeText(requireActivity(), "请打开权限，否则无法获取本地文件", Toast.LENGTH_SHORT).show()
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
//                Toast.makeText(requireActivity(), "get it", Toast.LENGTH_LONG).show()
//            } else {
//                //用户不同意，向用户展示该权限作用
//                if (ActivityCompat.shouldShowRequestPermissionRationale(
//                        requireActivity(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    )
//                ) {
//                    AlertDialog.Builder(requireActivity())
//                        .setMessage("申请权限")
//                        .setPositiveButton("OK") { dialog1: DialogInterface?, which: Int ->
//                            ActivityCompat.requestPermissions(
//                                requireActivity(), arrayOf(
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


    fun transBitmap() {
        val bundle = Bundle()
        bundle.putBinder("", null)
    }

    private val messengerHandler: Handler = object : Handler(Looper.myLooper()!!) {
    }
    private val mGetMessenger = Messenger(messengerHandler)


    internal inner class ThreadHandler : Thread() {
        override fun run() {
            super.run()
            Looper.prepare()
            val handler = Handler(Looper.getMainLooper()!!)
            Log.e(TAG, "handler id = " + handler.looper.thread.id)
            Log.e(TAG, "handler id = $id")
            Looper.loop()
        }

    }

    internal inner class ScheduleCallable : Callable<Any> {
        @Throws(Exception::class)
        override fun call(): Any {
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