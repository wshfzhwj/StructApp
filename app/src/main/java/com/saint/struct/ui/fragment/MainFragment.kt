package com.saint.struct.ui.fragment

import android.Manifest
import android.content.*
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.saint.struct.R
import com.saint.struct.database.SaintRoomDB
import com.saint.struct.databinding.FragmentMainBinding
import com.saint.struct.repository.StudentRepository
import com.saint.struct.service.MessengerService
import com.saint.struct.tool.TAG
import com.saint.struct.tool.log
import com.saint.struct.ui.activity.AidlActivity
import com.saint.struct.ui.activity.WebActivity
import com.saint.struct.viewmodel.MainFragmentViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.lang.reflect.ParameterizedType
import java.util.concurrent.Callable


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of requireActivity() fragment.
 */

class MainFragment : BaseFragment(), EasyPermissions.PermissionCallbacks {
    private var mService: Messenger? = null

    //    private val mRetrofit: Retrofit? = null
    private lateinit var mFragmentMainBinding: FragmentMainBinding
    private lateinit var mStudentRepository: StudentRepository
    private lateinit var viewModel: MainFragmentViewModel

    companion object {
        const val EXTRA_KEY_SERVICE = "extra_key_service"
        const val PERMISSION_REQUEST_CODE = 10001
        const val TAG: String = "MainFragment"
    }

    override fun initLayoutId() = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        mFragmentMainBinding.handle = this
        observeViewModel()
    }


    override fun initData() {
        mStudentRepository = StudentRepository(SaintRoomDB.getInstance(requireActivity()).studentDao())
        viewModel = ViewModelProvider(this, ViewModelFactory(mStudentRepository))[MainFragmentViewModel::class.java]
        requestPermission()
    }


    fun observeViewModel(){
        //LiveData
        viewModel.studentLiveData.observe(viewLifecycleOwner) { value ->
            log("MainFragment studentLiveData -${value}")
        }

        lifecycleScope.launch {
            viewModel.shareFLow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect { value ->
                log("MainFragment shareFLow -${value}")
            }
        }

        //stateFlow
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { value ->
                    log("MainFragment stateFlow$value")
                }
            }
        }

//        //单个数据流
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.mSharedFlow
//                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
//                .collect {
//
//                }
//        }


        //但是，如果您需要并行对多个数据流执行生命周期感知型收集，则必须在不同的协程中收集每个数据流。在这种情况下，直接使用 repeatOnLifecycle() 会更加高效：
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                // Because collect is a suspend function, if you want to
//                // collect multiple flows in parallel, you need to do so in
//                // different coroutines.
//                launch {
//                    viewModel.mSharedFlow.collect { /* Process the value. */ }
//                }
//
//                launch {
//                     viewModel.mSharedFlow.collect { /* Process the value. */ }
//                }
//            }
//        }
    }

    fun testGif() {
        viewModel.testGif(this, mFragmentMainBinding.gifImage)
    }

    fun testGlide() {
        viewModel.testGlide(this, mFragmentMainBinding.gifImage)
    }

    fun testFinger() {
        viewModel.testFinger(requireActivity())
    }

    fun addData() {
        viewModel.addDataForDao()
    }

    fun clearData() {
        viewModel.clearDataForDao()
    }

    fun queryByLiveData() {
        viewModel.queryByLiveData()
    }

    fun queryByShareFlow() {
        viewModel.queryByShareFlow()
    }

    fun queryByStateFlow() {
        viewModel.queryByStateFlow()
    }

    fun queryByFlow() {
        //flow
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.queryByFlow().collect {
                    log("MainFragment queryByFlow$it")
                }
            }
        }
    }

    fun SharedPreferences.modify(block: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        editor.block()
        editor.apply()
    }

    fun testCoroutineScope3() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("exceptionHandler", "${coroutineContext[CoroutineName]} $throwable")
        }
        lifecycleScope.launch(Dispatchers.Main + CoroutineName("scope1") + exceptionHandler) {
            supervisorScope {
                Log.d("scope", "--------- 1")
                launch(CoroutineName("scope2")) {
                    Log.d("scope", "--------- 2")
                    throw NullPointerException("空指针")
                }
                val scope4 = launch(CoroutineName("scope4")) {
                    Log.d("scope", "--------- 6")
                    delay(2000)
                    Log.d("scope", "--------- 7")
                }
                scope4.join()
                Log.d("scope", "--------- 8")
            }
        }

    }

    private fun testCoroutineScope4() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("exceptionHandler", "${coroutineContext[CoroutineName]} $throwable")
        }
        val coroutineScope = CoroutineScope(SupervisorJob() + CoroutineName("coroutineScope"))
        lifecycleScope.launch(Dispatchers.Main + CoroutineName("scope1") + exceptionHandler) {
            with(coroutineScope) {
                val scope2 = launch(CoroutineName("scope2") + exceptionHandler) {
                    Log.d("scope", "1--------- ${coroutineContext[CoroutineName]}")
                    throw NullPointerException("空指针")
                }
                val scope3 = launch(CoroutineName("scope3") + exceptionHandler) {
                    scope2.join()
                    Log.d("scope", "2--------- ${coroutineContext[CoroutineName]}")
                    delay(2000)
                    Log.d("scope", "3--------- ${coroutineContext[CoroutineName]}")
                }
                scope2.join()
                Log.d("scope", "4--------- ${coroutineContext[CoroutineName]}")
                coroutineScope.cancel()
                scope3.join()
                Log.d("scope", "5--------- ${coroutineContext[CoroutineName]}")
            }
            Log.d("scope", "6--------- ${coroutineContext[CoroutineName]}")
        }

    }

    private fun testException() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("exceptionHandler", "${coroutineContext[CoroutineName].toString()} 处理异常 ：$throwable")
        }
        lifecycleScope.launch(exceptionHandler) {
            supervisorScope {
                launch(CoroutineName("异常子协程")) {
                    Log.d(TAG, "${Thread.currentThread().name}, 我要开始抛异常了")
                    throw NullPointerException("空指针异常")
                }
                for (index in 0..10) {
                    launch(CoroutineName("子协程$index")) {
                        Log.d(TAG, "${Thread.currentThread().name},$index")
                        if (index % 3 == 0) {
                            throw NullPointerException("子协程${index}空指针异常")
                        }
                    }
                }
            }
        }
    }

    private fun testExceptionBySupervisorJob() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("exceptionHandler", "${coroutineContext[CoroutineName].toString()} 处理异常 ：$throwable")
        }
        val supervisorScope = CoroutineScope(SupervisorJob() + exceptionHandler)
        with(supervisorScope) {
            launch(CoroutineName("异常子协程")) {
                Log.d(Thread.currentThread().name, "我要开始抛异常了")
                throw NullPointerException("空指针异常")
            }
            for (index in 0..10) {
                launch(CoroutineName("子协程$index")) {
                    Log.d(TAG, "${Thread.currentThread().name}正常执行,$index")
                    if (index % 3 == 0) {
                        throw NullPointerException("子协程${index}空指针异常")
                    }
                }
            }
        }
    }


    private var conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d(TAG, "onServiceConnected")
            mService = Messenger(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "onServiceDisconnected")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e(TAG, "onSaveInstanceState")
    }

    fun startAidl() {
        startActivity(Intent().setClass(requireActivity(), AidlActivity::class.java))
    }

    val typeClass: Unit
        get() {
            val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
            type.toString()
        }

    private fun initView() {
        mFragmentMainBinding = fragmentBinding as FragmentMainBinding
        setToolbar()
    }

    private fun setToolbar() {
        (fragmentBinding as FragmentMainBinding).layoutAppBar.titleBar.text = "首页"
    }

    private fun bindService() {
        val intent = Intent(requireActivity(), MessengerService::class.java)
        requireActivity().bindService(intent, conn, AppCompatActivity.BIND_AUTO_CREATE)
    }

    private fun unBindService() {
        requireActivity().unbindService(conn)
    }

    fun startWebActivity() {
//        String url = "http://webapp.1/a/b/c";
        val url = "qax://webapp.1/a/b/c"
        //        String url = "file:///android_asset/dist/index.html";
        //  String url = "file:///android_asset/test.html";
        WebActivity.startActivity(requireActivity(), "title", url)
    }

    private val messengerHandler: Handler = object : Handler(Looper.myLooper()!!) {}

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


    inner class ViewModelFactory(private val stu: StudentRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            // 判断 MainViewModel 是不是 modelClass 的父类或接口
            if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
                return MainFragmentViewModel(stu) as T
            }
            throw IllegalArgumentException("UnKnown class")
        }
    }

    @AfterPermissionGranted(PERMISSION_REQUEST_CODE)
    private fun requestPermission() {
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(requireActivity(), *permissions)) {
            // 如果有上述权限, 执行该操作
            Toast.makeText(requireActivity(), "权限申请通过", Toast.LENGTH_LONG).show()
        } else {
            // 方式一:如果没有上述权限 , 那么调用requestPermissions申请权限，用户拒绝权限申请后 , 再次申请会自动弹出该对话框 ;
//            EasyPermissions.requestPermissions(
//                PermissionRequest.Builder(this, PERMISSION_REQUEST_CODE, *permissions)
//                    .setRationale("需要用到手机存储权限")
//                    .setPositiveButtonText("去授权")
//                    .setNegativeButtonText("取消")
//                    .build()
            // 方式二:
            EasyPermissions.requestPermissions(
                this, "需要用到手机存储权限",
                PERMISSION_REQUEST_CODE, *permissions
            )
        }
    }

    // 权限结果回调方法
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 将权限处理结果传递给EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(requireActivity(), "get it", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(requireActivity(), "请打开权限，否则无法获取本地文件", Toast.LENGTH_SHORT).show()
        // 弹框提示用户跳转设置打开权限
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}