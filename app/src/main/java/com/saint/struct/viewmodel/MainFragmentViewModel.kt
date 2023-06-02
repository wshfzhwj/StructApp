package com.saint.struct.viewmodel

import android.app.Activity
import android.content.Context
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.saint.biometiriclib.BiometricPromptManager
import com.saint.struct.bean.entity.Student
import com.saint.struct.database.SaintRoomDB
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class MainFragmentViewModel(list: MutableList<Student>) : ViewModel() {
    var mutableLiveData = MutableLiveData<Int>()
    var studentList = list
    lateinit var mRoomDatabase: SaintRoomDB
    var mManager: BiometricPromptManager? = null

    fun testDB(context: Context) {
        mRoomDatabase = SaintRoomDB.getInstance(context)
        thread {
            mRoomDatabase.studentDao().insertStudent(Student("ZhangSan", "13"))
            mRoomDatabase.studentDao().insertStudent(Student("LiSi", "12"))
            mRoomDatabase.studentDao().updateStudent(Student(2, "LiSi", "14"))
            studentList.clear()
            val list = mRoomDatabase.studentDao().getAll() as List<Student>
            studentList.addAll(list)
        }
    }

    suspend fun testDBByCoroutines(){
        coroutineScope{
            launch {
                mRoomDatabase.studentDao().insertStudent(Student("zhangsan", "11"))
                mRoomDatabase.studentDao().insertStudent(Student("lisi", "12"))
                mRoomDatabase.studentDao().updateStudent(Student("lisi", "14"))
                studentList.clear()
                studentList.addAll(mRoomDatabase.studentDao().getAll()!!)
            }
        }
    }

    fun testFinger(activity: Activity, textView: TextView) {
        mManager = BiometricPromptManager.from(activity)
        val stringBuilder = StringBuilder()
        stringBuilder.append("SDK version is " + Build.VERSION.SDK_INT)
        stringBuilder.append("\n")
        stringBuilder.append("isHardwareDetected : " + mManager!!.isHardwareDetected)
        stringBuilder.append("\n")
        stringBuilder.append("hasEnrolledFingerprints : " + mManager!!.hasEnrolledFingerprints())
        stringBuilder.append("\n")
        stringBuilder.append("isKeyguardSecure : " + mManager!!.isKeyguardSecure)
        stringBuilder.append("\n")
        textView.text = stringBuilder.toString()
        if (mManager!!.isBiometricPromptEnable) {
            mManager!!.authenticate(object : BiometricPromptManager.OnBiometricIdentifyCallback {
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
        val url = "https://alifei04.cfp.cn/creative/vcg/800/version23/VCG41175510742.jpg"
        Glide.with(fragment).load(url).into(imageView)
    }
}