package com.saint.struct.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saint.struct.R
import com.saint.struct.tool.TAG
import kotlin.getValue

/**
 * 记录写法 无实际调用处
 */
class TestDelegateActivity : AppCompatActivity() {
    val viewModel: TestViewModel by viewModels{
        UserViewModelFactory(3)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coord_test4)
        Log.d(TAG,"viewModel = ${viewModel.age}" )
    }
}

class TestViewModel(val age: Int = 0) : ViewModel() {
}

// 自定义 Factory，接收 userId 作为参数
class UserViewModelFactory(private val userId: Int) : ViewModelProvider.Factory {
    // 重写 create 方法，返回带参数的 ViewModel
    @Suppress("UNCHECKED_CAST") // 抑制类型转换警告
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // 检查目标类型是否为 TestViewModel
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            return TestViewModel(userId) as T // 传入参数并返回实例
        }
        // 若类型不匹配，抛出异常
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}