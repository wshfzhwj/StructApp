package com.saint.kotlin.test.coroutines

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

class KotlinCoroutines {
    private fun testCoroutineScope(context: CoroutineContext) {
        val coroutineScope = CoroutineScope(context)
        coroutineScope.launch(Dispatchers.IO) {
        }

        runBlocking(Dispatchers.Main) {

        }

        coroutineScope.launch(Dispatchers.Main) {    //在 UI 线程开始
            withContext(Dispatchers.IO) {//切换到 IO 线程，并在执行完成后切回 UI 线程
            }
            // avatarIv.setImageBitmap(image)//回到 UI 线程更新 UI
        }
    }

    private suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // pretend we are doing something useful here
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // pretend we are doing something useful here, too
        return 29
    }


    suspend fun doWorld() = coroutineScope {  // this: CoroutineScope
        launch {
            delay(1000L)
            println("World!")
        }
        println("Hello")
    }

    suspend fun func2() = coroutineScope() {
        val time = measureTimeMillis {
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }


    suspend fun func3() = coroutineScope {
        var count = 0
        repeat(1000) {
            launch(Dispatchers.IO) {
                count++
            }
        }
        println(count)
    }


    suspend fun func4() = coroutineScope {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                if (isActive) {
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        log("job: I'm sleeping ${i++} ...")
                        nextPrintTime += 500L
                    }
                } else {
                    return@launch
                }
            }
        }
        delay(1300L)
        log("func: I'm tired of waiting!")
        job.cancelAndJoin()
        log("func: Now I can quit.")
    }

    suspend fun func5() = coroutineScope {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    log("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L)
        log("func: I'm tired of waiting!")
        job.cancelAndJoin()
        log("func: Now I can quit.")
    }

    suspend fun func6() = coroutineScope { // this: CoroutineScope
        launch {
            delay(200L)
            println("Task from runBlocking")
        }

        coroutineScope { // Creates a coroutine scope
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // This line will be printed before the nested launch
        }

        println("Coroutine scope is over") // This line is not printed until the nested launch completes
    }

    fun func7() {
        println("Start")
        // 启动一个协程
        GlobalScope.launch {
            delay(1000)
            println("Hello")
        }
        Thread.sleep(2000) // 等待 2 秒钟
        println("Stop")
    }

    suspend fun func8() = coroutineScope {
        // this: CoroutineScope
        launch {
            // 在 runBlocking 作用域中启动一个新协程
            delay(1000L)
            println("World! ${Thread.currentThread().name}")
        }
        println("Hello, ${Thread.currentThread().name}")
    }

    fun func9() = runBlocking { // this: CoroutineScope
        launch {
            delay(200L)
            log("Task from runBlocking")
        }

        coroutineScope { // Creates a coroutine scope
            launch {
                delay(500L)
                log("Task from nested launch")
            }

            delay(100L)
            log("Task from coroutine scope") // This line will be printed before the nested launch
        }

        log("Coroutine scope is over") // This line is not printed until the nested launch completes
    }

    fun func10() {
        runBlocking {
            launch {
                // 在后台启动一个新的协程并继续
                delay(3000L)
                println("World!")
            }
        }
        println("Hello,")
    }

    fun func11() = runBlocking<Unit> {
        launch {
            log("func runBlocking")
        }
        launch(Dispatchers.Default) {
            log("Default")
            launch(Dispatchers.Unconfined) {
                log("Unconfined 1")
            }
        }
        launch(Dispatchers.IO) {
            log("IO")
            launch(Dispatchers.Unconfined) {
                log("Unconfined 2")
            }
        }
        launch(newSingleThreadContext("MyOwnThread")) {
            log("newSingleThreadContext")
            launch(Dispatchers.Unconfined) {
                log("Unconfined 4")
            }
        }
        launch(Dispatchers.Unconfined) {
            log("Unconfined 3")
        }
        GlobalScope.launch {
            log("GlobalScope")
        }
    }


    fun func12() {
        GlobalScope.launch(Dispatchers.IO) {
            delay(600)
            log("GlobalScope")
        }
        runBlocking {
            delay(500)
            log("runBlocking")
        }
        //主动休眠两百毫秒，使得和 runBlocking 加起来的延迟时间多于六百毫秒
        Thread.sleep(200)
        log("after sleep")
    }

    fun func13() = runBlocking {
        launch {
            delay(100)
            log("Task from runBlocking")
        }
        coroutineScope {
            launch {
                delay(500)
                log("Task from nested launch")
            }
            delay(50)
            log("Task from coroutine scope")
        }
        log("Coroutine scope is over")
    }

    fun func14() {
        var count = 0
        val mutex = Mutex()

        runBlocking {
            repeat(100) {
                launch(Dispatchers.IO) {
                    repeat(100) {
                        launch(Dispatchers.IO) {
                            mutex.withLock {
                                count++
                            }
                        }
                    }
                }
            }
        }

        println(count)
    }

    suspend fun fetchDocs() {                             // Dispatchers.func
        val result = get("https://developer.android.com") // Dispatchers.IO for `get`
        // Dispatchers.func
    }

    suspend fun get(url: String) = withContext(Dispatchers.IO) { /* ... */ }

    fun log(msg: Any?) = println("[${Thread.currentThread().name}] $msg")

    /**
     *  launch函数有4个参数：
     *  1.context: CoroutineContext = DefaultDispatcher, // 用Dispatchers指出协程运行在那个线程中
     *  2.start：CoroutineStart = CoroutineStart.DEFAULT, // 协程启动的方式
     *  DEFAULT: 线程空闲就启动； LAZY：手动调用start才启动； ATOMIC:
     *  3.parent:Job? = null
     *  4. block: suspend CoroutineScope.() - Unit, 协程的内容（无返回值，无参数的挂起函数）
     */
    fun request() = runBlocking<Unit> { // 开启一个最外层的协程
        val job: Job = launch { // 开启一个子协程
            repeat(1000) {
                println("挂起中$it")
                delay(500)
            }
        }
        // job和job2同时走

        val job2: Deferred<String> = async { // 再开启一个子协程
            delay(500)
            return@async "hello" // 协程返回字符串
        }
        println("job2的输出：${job2.await()}") // await表示等待 拿到job2的结果

        delay(1100)
        println("func: waiting")
        job.cancel()
        println("func: finish")
    }

    suspend fun launchFunc() {
        print("start")
        val job = CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            println("Cor launch")
        }
        print("end")
        job.join()
    }


    suspend fun fun13() = coroutineScope {
        launch {
            delay(2000L)
            println("World 2")
        }
        launch {
            delay(1000L)
            println("World 1")
        }
        println("Hello")
    }

    suspend fun fun14() = coroutineScope {
        doWorld()
        println("Done")
    }

    suspend fun fun15() = coroutineScope {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) { // 可以被取消的计算循环
                // 每秒打印消息两次
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // 等待一段时间
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消该作业并等待它结束
        println("main: Now I can quit.")

    }

    /**
     * 在 finally 中释放资源
     * 我们通常使用如下的方法处理在被取消时抛出 CancellationException 的可被取消的挂起函数。
     * 比如说，try {……} finally {……} 表达式以及 Kotlin 的 use 函数一般在协程被取消的时候执行它们的终结动作：
     */
    suspend fun fun16() = coroutineScope<Unit> {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                println("job: I'm running finally")
            }
        }
        delay(1300L) // 延迟一段时间
        println("main: I'm tired of waiting!")
        job.cancel() // 取消该作业并且等待它结束
        job.join() // 取消该作业并且等待它结束
        println("main: Now I can quit.")
    }

    /**
     * 运行不能取消的代码块
     * 在前一个例子中任何尝试在 finally 块中调用挂起函数的行为都会抛出 CancellationException，
     * 因为这里持续运行的代码是可以被取消的。通常，这并不是一个问题，所有良好的关闭操作（关闭一个文件、取消一个作业、或是关闭任何一种通信通道）
     * 通常都是非阻塞的，并且不会调用任何挂起函数。然而，在真实的案例中，
     * 当你需要挂起一个被取消的协程，你可以将相应的代码包装在 withContext(NonCancellable) {……} 中，
     * 并使用 withContext 函数以及 NonCancellable 上下文
     */
    suspend fun fun17() = coroutineScope {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                withContext(NonCancellable) {
                    println("job: I'm running finally")
                    delay(1000L)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1300L) // 延迟一段时间
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消该作业并等待它结束
        println("main: Now I can quit.")
    }

    suspend fun fun18() = coroutineScope {
        launch { // 运行在父协程的上下文中，即 runBlocking 主协程
            println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) { // 不受限的——将工作在主线程中
            println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) { // 将会获取默认调度器
            println("Default               : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(newSingleThreadContext("MyOwnThread")) { // 将使它获得一个新的线程
            println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
        }
    }

    fun func19() {
        GlobalScope.launch { // 在后台启动一个新的协程并继续
            //delay 是一个特殊的挂起函数 ，它不会造成线程阻塞，但是会挂起协程，并且只能在协程中使用。
            //Hello,
            //World!
            delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
            println("World!") // 在延迟后打印输出
        }
        println("Hello,") // 协程已在等待时主线程还在继续
        Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
    }

    fun func20() {
        //This is a delicate API and its use requires care. Make sure you fully read and understand documentation of the declaration that is marked as a delicate API.
        GlobalScope.launch { // 在后台启动一个新的协程并继续
            delay(1000L)
            println("World!")
        }
        println("Hello,") // 主线程中的代码会立即执行
        runBlocking {     // 但是这个表达式阻塞了主线程
            delay(2000L)  // ……我们延迟 2 秒来保证 JVM 的存活
        }
    }

    suspend fun func21() = coroutineScope {
        doWorld()
    }

    suspend fun func22() = coroutineScope {
        val job = GlobalScope.launch { // 启动一个新协程并保持对这个作业的引用
            delay(1000L)
            println("World!")
        }
        println("Hello,")
        job.join() // 等待直到子协程执行结束
    }

    suspend fun func23() = coroutineScope { // this: CoroutineScope
        launch {
            delay(200L)
            println("Task from runBlocking")
        }

        coroutineScope { // 创建一个协程作用域
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
        }

        println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
    }

    /**
     * 使计算代码可取消我们有两种方法来使执行计算的代码可以被取消。第一种方法是定期调用挂起函数来检查取消。
     * 对于这种目的 yield 是一个好的选择。 另一种方法是显式的检查取消状态。让我们试试第二种方法。
     * 在 finally 中释放资源
     * 运行不能取消的代码块
     * withContext 函数以及 NonCancellable
     *
     * 超时 withTimeout
     * withTimeout 的 withTimeoutOrNull
     *
     */
    fun simple(): Sequence<Int> = sequence { // 序列构建器
        for (i in 1..3) {
            Thread.sleep(100) // 假装我们正在计算
            yield(i) // 产生下一个值
        }
    }

    suspend fun func24() = coroutineScope() {
        launchCoroutineExceptionHandler(this) {
            println()
        }
    }

    private inline fun launchCoroutineExceptionHandler(
        scope: CoroutineScope,
        crossinline block: suspend CoroutineScope.() -> Unit
    ) {
        val job = scope.launch(GlobalCoroutineExceptionHandler(::handException)) {
            block()
        }

        job.invokeOnCompletion {
        }
    }

    private fun handException(context: CoroutineContext, exception: Throwable) {
    }

    /**
     * 协程超时withTimeout
     */
    suspend fun fun25() {
        try {
            withTimeout(1000) {
                // 可能耗时较长的操作
                delay(500)
            }
        } catch (e: TimeoutCancellationException) {
            println("Operation timed out")
        }
    }

    /**
     * 在协程中，如果一个协程失败，通常会导致整个父协程及其子协程都被取消。
     * 但有时，我们希望一个协程的失败不会影响其他协程的执行，这时可以使用 SupervisorJob。
     * SupervisorJob 是一种特殊的 Job，它允许子协程失败时只取消该子协程，而不影响其他子协程或父协程
     *
     * 我们创建了一个 SupervisorJob 作为父协程的 Job，然后启动两个子协程。如果子协程2失败，只有该子协程会被取消，
     * 而其他协程仍然可以继续执行。这有助于构建健壮的并发系统，其中一个子协程的失败不会影响其他子协程。
     */
    suspend fun fun26() = coroutineScope {
        val supervisorJob = SupervisorJob()

        val parentJob = launch(supervisorJob) {
            val childJob1 = launch {
                // 子协程1的操作
            }

            val childJob2 = launch {
                // 子协程2的操作，可能会失败
                throw Exception("Child job 2 failed")
            }
        }

        parentJob.join()
    }

    fun fun27() {
        val semaphore = Semaphore(3) // 允许同时运行的协程数

        runBlocking {
            repeat(10) {
                launch {
                    semaphore.acquire() // 获取信号
                    // 执行需要限制并发的操作
                    delay(1000)
                    println(it)
                    semaphore.release() // 释放信号
                }
            }
        }
    }

    fun testCoroutineScope4() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("exceptionHandler", "${coroutineContext[CoroutineName]} $throwable")
        }
        val coroutineScope = CoroutineScope(SupervisorJob() + CoroutineName("coroutineScope"))
        GlobalScope.launch(CoroutineName("scope1") + exceptionHandler) {
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
                Log.d("scope", "5--------- ${coroutineContext[CoroutineName]}-----${coroutineScope.isActive}")
            }
            Log.d("scope", "6--------- ${coroutineContext[CoroutineName]}")
        }
    }

    fun testCancel() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                // print a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("Hello ${i++}")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1000L)
        println("Cancel!")
        job.cancel()
        println("Done!")
    }

    fun testSupervisorJob() {
        val scope = CoroutineScope(CoroutineExceptionHandler { _, _ -> })
        scope.launch(SupervisorJob()) {
            launch(CoroutineName("A")) {
                println("start A")
                delay(10)
                throw RuntimeException()
            }
            launch(CoroutineName("B")) {
                println("start B")
                delay(100)
                println("正常执行,我不会收到影响")
            }
        }
    }


    fun testJoin() = runBlocking {
        val job = launch {
            println("job start")
            delay(1000L)
            println("job end")
        }
        delay(1L)
        println("runBlocking go on")
        println("begin runBlocking delay job isCompleted ? = ${job.isCompleted}")
        delay(1500L)
        println("after runBlocking delayjob isCompleted ? = ${job.isCompleted}")
        job.join()
        println("after join job isActive ? = ${job.isActive}")
        println("runBlocking end")
    }

    suspend fun testScope(){
        val handler = CoroutineExceptionHandler { _, exception ->
            println("捕获到的异常： $exception")
        }
        val scope = CoroutineScope(Job())
        scope.launch {
            launch(handler) {//子协程设置没有意义，不会打印数据，因为异常向上传递，而父协程中没有handler则无法捕获
                throw NullPointerException()//抛出空指针异常
            }
        }

        supervisorScope {
            launch(handler) {//SupervisorJob不会让异常向上传递，会使用子协程内部的异常处理器来处理
                throw IllegalArgumentException()//抛出非法参数异常
            }
        }
    }
}

//fun main() {
fun main() = runBlocking {
    val kotlinCoroutines = KotlinCoroutines()
    kotlinCoroutines.testScope()
}
