package com.saint.struct.viewmodel

import android.app.Activity
import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.saint.biometiriclib.BiometricPromptManager
import com.saint.struct.bean.Student
import com.saint.struct.repository.StudentRepository
import com.saint.struct.tool.TAG
import com.saint.struct.tool.log
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainFragmentViewModel(val repository: StudentRepository, application: Application) : BaseViewModel(application) {
    private val _stuMutableLiveData = MutableLiveData<List<Student>>()
    val studentLiveData: LiveData<List<Student>> get() = _stuMutableLiveData

    private var _sharedFlow = MutableSharedFlow<List<Student>>(
        replay = 1,//重播给新订阅者的数量
        extraBufferCapacity = 1,//当有剩余缓冲空间时，emit不会挂起
        onBufferOverflow = BufferOverflow.DROP_OLDEST //配置缓冲区的溢出操作
    )
    val shareFLow: SharedFlow<List<Student>> get() = _sharedFlow

    private var _stateFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val stateFlow = _stateFlow

    private lateinit var mManager: BiometricPromptManager



    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    companion object {
        val NAMES = listOf("JACK", "MIKE", "TOM", "JIM", "JERRY", "ROSE")
        val AGES = listOf(18, 19, 20, 21, 22, 23)
    }

    fun queryByFlow(): Flow<List<Student>> = flow {
        log("viewModel queryByFlow")
        emit(repository.getAllUsers())
    }

    fun queryByLiveData() {
        viewModelScope.launch {
            _stuMutableLiveData.value = repository.getAllUsers()
        }
    }

    fun queryByShareFlow() {
        viewModelScope.launch {
            _sharedFlow.tryEmit(repository.getAllUsers())
        }
    }

    fun queryByStateFlow() {
        viewModelScope.launch {
            repository.getAllUsersFlow().map {
                UiState.Success(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = UiState.Loading
            ).collect {
                log("collect $it")
                _stateFlow.value = it
            }
        }
    }


    fun addDataForDao() {
        viewModelScope.launch {
            val random = Random.nextInt(0, 6)
            repository.insertUser(Student(NAMES[random], AGES[random]))
        }
    }

    fun clearDataForDao() {
        viewModelScope.launch {
            repository.deleteAllUser()
        }
    }

    fun testFinger(activity: Activity) {
        try {
            mManager = BiometricPromptManager.from(activity)
        } catch (e: RuntimeException) {
            Log.e(TAG,"指纹模块出问题了")
            _toastMessage.postValue("指纹模块出问题了")
            return
        }
        val stringBuilder = StringBuilder()
        stringBuilder.append("SDK version is " + Build.VERSION.SDK_INT)
        stringBuilder.append("\n")
        stringBuilder.append("isHardwareDetected : " + mManager.isHardwareDetected)
        stringBuilder.append("\n")
        stringBuilder.append("hasEnrolledFingerprints : " + mManager.hasEnrolledFingerprints())
        stringBuilder.append("\n")
        stringBuilder.append("isKeyguardSecure : " + mManager!!.isKeyguardSecure)
        stringBuilder.append("\n")
        log(stringBuilder.toString())
        if (mManager.isBiometricPromptEnable) {
            mManager.authenticate(object : BiometricPromptManager.OnBiometricIdentifyCallback {
                override fun onUsePassword() {
                    Toast.makeText(activity, "onUsePassword", Toast.LENGTH_SHORT).show()
                }

                override fun onSucceeded() {
                    Toast.makeText(activity, "onSucceeded", Toast.LENGTH_SHORT).show()
                }

                override fun onFailed() {
                    Toast.makeText(activity, "onFailed", Toast.LENGTH_SHORT).show()
                }

                override fun onError(code: Int, reason: String) {
                    Toast.makeText(activity, "onError", Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() {
                    Toast.makeText(activity, "onCancel", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun testGif(fragment: Fragment, imageView: ImageView) {
        val mGif = "https://p1.itc.cn/q_70/images03/20210120/e75329453c7a4a84b49363559775499b.gif"
        Glide.with(fragment).load(mGif).into(imageView)
    }

    fun testGlide(fragment: Fragment, imageView: ImageView) {
        val url =
            "https://k.sinaimg.cn/n/sinakd20117/0/w800h800/20240127/889b-4c8a7876ebe98e4d619cdaf43fceea7c.jpg/w700d1q75cms.jpg"
        Glide.with(fragment).load(url).into(imageView)
    }

    sealed interface UiState {
        data object Loading : UiState
        data class Success(val students: List<Student>) : UiState
    }
}