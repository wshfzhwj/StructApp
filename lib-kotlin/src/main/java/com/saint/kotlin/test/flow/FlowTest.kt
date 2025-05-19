package com.saint.kotlin.test.flow

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.util.UUID
import java.util.concurrent.Executors
import kotlin.experimental.ExperimentalTypeInference
import kotlin.system.measureTimeMillis

/**
 * 流构建器
 *      flowOf 构建器定义了一个发射固定值集的流。
 *      使用 .asFlow() 扩展函数，可以将各种集合与序列转换为流
 * 过渡流操作符 map 与 filter
 * 转换操作符 transform
 * 末端流操作符
 *      转化为各种集合，例如 toList 与 toSet。
 *      获取第一个（first）值与确保流发射单个（single）值的操作符。
 *      使用 reduce 与 fold 将流规约到单个值。
 *
 *
 *      map filter onEach debounce sample reduce fold flatMapConcat
 *      flatMapMerge flatMapLatest zip buffer conflate
 */
class FlowTest {

    suspend fun testFlowOf() {
        flowOf(1, 2, 3, 4, 5)
            .onEach {
                delay(100)
            }.collect {
                println(it)
            }

        flow<Int>{
            emit(1)
        }
    }



    suspend fun testAsFlow() {
        listOf(1, 2, 3, 4, 5).asFlow().map { (it > 3) }
            .onEach {
                delay(100)
            }.collect {
                println(it)
            }
    }

    // SharedFlow 示例
    private val sharedFlow = MutableSharedFlow<String>()
    suspend fun testSharedFlow() {
        // 订阅
        sharedFlow.collect { value ->
            println("Received: $value")
        }
        // 发送数据
        sharedFlow.emit("Hello, SharedFlow!")
    }

    // StateFlow 示例
    private val stateFlow = MutableStateFlow("Initial State")

    // 订阅
    suspend fun testStateFlow() {
        stateFlow.collect { value ->
            println("Current State: $value")
        }
        // 更新状态
        stateFlow.value = "New State"
    }


    fun test() = runBlocking {
        val myDispatcher = Executors.newSingleThreadExecutor()
            .asCoroutineDispatcher()
        flow {
            println("emit on ${Thread.currentThread().name}")
            emit("data")
        }.map {
            println("run first map on${Thread.currentThread().name}")
            "$it map"
        }
            //作用于前面flow创建与第一个map
            .flowOn(Dispatchers.IO)
            .map {
                println("run second map on ${Thread.currentThread().name}")
                "${it},${it.length}"
            }
            //作用于第二个map
            .flowOn(myDispatcher)
            .collect {
                println("result $it on ${Thread.currentThread().name}")
            }
    }

    fun zip() {
        runBlocking {
            val flow = sendRealtimeWeatherRequest()
            val flow2 = sendSevenDaysWeatherRequest()
            flow.zip(flow2) { i, s -> i.toString() + s }.collect {
                println(it) // Will print "1a 2b 3c"
            }
        }
    }

    fun sendRealtimeWeatherRequest(): Flow<String> = flow {
        // send request to realtime weather
        emit("1")
        emit("2")
        emit("3")
    }

    fun sendSevenDaysWeatherRequest(): Flow<String> = flow {
        // send request to get 7 days weather
        emit("a")
        emit("b")
    }

    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100) // 假装我们异步等待了 100 毫秒
            emit(i) // 发射下一个值
        }
    }

    fun testSequence() = run {
        val intSequence = sequence<Int> {
            Thread.sleep(1000) // 模拟耗时任务1
            yield(1)
            Thread.sleep(1000) // 模拟耗时任务2
            yield(2)
            Thread.sleep(1000) // 模拟耗时任务3
            yield(3)
        }

        intSequence.forEach {
            println(it)
        }
    }

}

fun main() = runBlocking{
    val flowTest = FlowTest()
//    val scope = CoroutineScope(SupervisorJob())
//    scope.launch{
//        flowTest.testAsFlow()
//        flowTest.testFlowOf()
//        flowTest.testChannelFlow()
//    }

//    flowTest.test()
    flowTest.testFlowOf()
}

//sampleStart
//fun simple(): Flow<Int> = flow { // 流构建器
//    for (i in 1..3) {
//        delay(100) // 假装我们在这里做了一些有用的事情
//        emit(i) // 发送下一个值
//    }
//}
//
//fun main() = runBlocking<Unit> {
//    // 启动并发的协程以验证主线程并未阻塞
//    launch {
//        for (k in 1..3) {
//            println("I'm not blocked $k")
//            delay(100)
//        }
//    }
//    // 收集这个流
//    simple().collect { value -> println(value) }
//}
//sampleEnd

//sampleStart
//fun main() = runBlocking<Unit> {
//    val sum = (1..5).asFlow()
//        .map { it * it } // 数字 1 至 5 的平方
//        .reduce { a, b -> a + b } // 求和（末端操作符）
//    println(sum)
//}
//sampleEnd

//sampleStart
//fun simple(): Flow<Int> = flow {
//    for (i in 1..3) {
//        Thread.sleep(100) // 假装我们以消耗 CPU 的方式进行计算
//        log("Emitting $i")
//        emit(i) // 发射下一个值
//    }
//}.flowOn(Dispatchers.Default) // 在流构建器中改变消耗 CPU 代码上下文的正确方式
//
//fun main() = runBlocking<Unit> {
//    simple().collect { value ->
//        log("Collected $value")
//    }
//}
//sampleEnd


//sampleStart
//fun main() = runBlocking<Unit> {
//    val time = measureTimeMillis {
//        simple()
//            .buffer() // 缓冲发射项，无需等待
//            .collect { value ->
//                delay(300) // 假装我们花费 300 毫秒来处理它
//                println(value)
//            }
//    }
//    println("Collected in $time ms")
//}
//sampleEnd


//fun main() = runBlocking{
//    runBlocking {
//        listOf(1, 2, 3, 4, 5).filter { it > 3 }.asFlow()
//            .onEach {
//                delay(100)
//            }.collect {
//                println(it)
//            }
//}
//
//    val flow1 = flowOf(1, 2, 3)
//    val flow2 = flowOf("one", "two")
//    flow1.combine(flow2) { i, s ->
//        "$i -> $s"
//    }.collect {
//        println(it)
//    }
//--------- 打印 ---------
//1 -> one
//2 -> two
//3 -> three
//3 -> four


//sampleStart
//    val flowTest = FlowTest()
//    flowTest.testSequence()
//    val time = measureTimeMillis {
//        flowTest.simple()
//            .conflate() // 合并发射项，不对每个值进行处理
//            .collect { value ->
//                delay(300) // 假装我们花费 300 毫秒来处理它
//                println(value)
//            }
//    }
//    println("Collected in $time ms")
//sampleEnd
//    val nums = (1..4).asFlow().shareIn(this,SharingStarted.Eagerly).onEach { delay(300) } // 发射数字 1.;3，间隔 300 毫秒
//        countdown(1_000, 200) { remainTime -> println(remainTime) }.collect{}
//        foo()
//        (1..4).asFlow().skipOddAndDuplicateEven().collect { println(it) }
//}

fun foo() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return@forEach // 非局部直接返回到 foo() 的调用者
        print(it)
    }
    println("this point is unreachable")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun EditText.textChangeFlow(): Flow<CharSequence> = callbackFlow {
    // 构建输入框监听器
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        // 在文本变化后向流发射数据
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { trySend(it).isSuccess }
        }
    }
    addTextChangedListener(watcher) // 设置输入框监听器
    awaitClose { removeTextChangedListener(watcher) } // 阻塞以保证流一直运行
}


fun Flow<Int>.skipOddAndDuplicateEven(): Flow<Int> = transforms { value ->
    if (value % 2 == 0) { // Emit only even values, but twice
        emit(value)
        emit(value)
    } // Do nothing if odd
}

@OptIn(ExperimentalTypeInference::class)
public inline fun <T, R> Flow<T>.transforms(
    @BuilderInference crossinline transform: suspend FlowCollector<R>.(value: T) -> Unit
): Flow<R> = flow {
    collect { value ->
        return@collect transform(value)
    }// Note: safe flow is used here, because collector is exposed to transform on each operation
}

fun nonLocalReturn() {
    println("useReturn - start")
    // out@ 标签修饰的后面的 lambda 表达式
    listOf(1, 2).forEach out@{
        println("out@ start $it")
        listOf("a", "b").forEach inside@{
            // return 作为一个非局部返回
            // 会停止 listOf(1,2) 和 listOf("a","b") 的遍历
            if (it == "a") return
            println("it $it")
        }
        println("out@ - end == $it")
    }
    println("userReturn - end - 不会被打印")
}

fun <T> countdown(
    duration: Long,
    interval: Long,
    onCountdown: suspend (Long) -> T
): Flow<T> =
    flow { (duration - interval downTo 0 step interval).forEach { emit(it) } }
        .onEach { delay(interval) }
        .onStart { emit(duration) }
        .map { onCountdown(it) }
        .flowOn(Dispatchers.Default)

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")
