package com.saint.struct.kotlin.ktl

import kotlinx.coroutines.*

class TestKt {
    var lastName: String = "zhang"
        get() = field.uppercase()   // 将变量赋值后转换为大写
        set

    var no: Int = 100
        get() = field                // 后端变量
        set(value) {
            if (value < 10) {       // 如果传入的值小于 10 返回该值
                field = value
            } else {
                field = -1         // 如果传入的值大于等于 10 返回 -1
            }
        }

    var heiht: Float = 145.4f
        private set

}

fun main(args: Array<String>) {
//    testIn()
//    testLambda()
//    testRange()
//    testCoroutines()
//    collectionTests()
//    runBlocking { testCoroutinesByJob(); }

//    runBlocking { val startTime = System.currentTimeMillis()
//        val job = launch(Dispatchers.Default) {
//            var nextPrintTime = startTime
//            var i = 0
////            while (i < 5) { // 一个执行计算的循环，只是为了占用 CPU
//            while (isActive) { // 可以被取消的计算循环
//                // 每秒打印消息两次
//                if (System.currentTimeMillis() >= nextPrintTime) {
//                    println("job: I'm sleeping ${i++} ...")
//                    nextPrintTime += 500L
//                }
//            }
//        }
//        delay(1300L) // 等待一段时间
//        println("main: I'm tired of waiting!")
//        job.cancelAndJoin() // 取消一个作业并且等待它结束
//        println("main: Now I can quit.")}

//    testField()
//    val bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.a);
//    System.out.println("memory getAllocationByteCount = " + bitmap.allocationByteCount);
//    System.out.println("memory getByteCount = " + bitmap.byteCount);
//    test(10) { num1: Int, num2: Int -> num1 + num2 }
    val list: List<String> = listOf("a", "aa", "aaa", "aaaa")
//    val result: String? = list.maxBy { it.length }
    println(::testCoroutinesCancel)
    print("Write anything here: ")

    val enteredString = readLine()
    println("You have entered this: $enteredString")
}

fun testCoroutinesCancel(): String {
    return "a"
}


private fun testField() {
    var kt: TestKt = TestKt()

    kt.lastName = "wang"

    println("lastName:${kt.lastName}")

    kt.no = 9
    println("no:${kt.no}")

    kt.no = 20
    println("no:${kt.no}")

}

fun collectionTests() {
    val list =
        listOf("Apple", "Google", "Microsoft", "Facebook", "Twitter", "Intel", "QualComm", "Tesla")
    // 遍历，以进行某种操作
    list.forEach { println(it) }
    //按条件进行过滤，返回条件为true的
    val short = list.filter { it.length < 6 }
    println(short) // [Apple, Intel, Tesla]
    // 把列表元素映射成为另外一种元素
    val lenList = list.map { it.length }
    println("Length of each item $lenList") //Length of each item [5, 6, 9, 8, 7, 5, 8, 5]
    // 按某种条件进行排序
    val ordered = list.sortedBy { it.length }
    println("Sorted by length $ordered") // Sorted by length [Apple, Intel, Tesla, Google, Twitter, Facebook, QualComm, Microsoft]
    // 折叠，用累积的结果继续遍历
    val joint = list.fold("") { partial, item -> if (partial != "") "$partial, $item" else item }
    println("Joint list with comma $joint") // Joint list with comma Apple, Google, Microsoft, Facebook, Twitter, Intel, QualComm, Tesla
    //分组，用某种条件 把列表分成两组
    val (first, second) = list.partition { it.length < 6 }
    println("Length shorter than 6 $first") // Length shorter than 6 [Apple, Intel, Tesla]
    println("Longer than 6 $second") // Longer than 6 [Google, Microsoft, Facebook, Twitter, QualComm]
    // 归类，按某种方法把元素归类，之后变成了一个Map
    val bucket = list.groupBy { it.length }
    println("$bucket is a map now") //{5=[Apple, Intel, Tesla], 6=[Google], 9=[Microsoft], 8=[Facebook, QualComm], 7=[Twitter]} is a map now
}

fun testCoroutinesBySleep() {
    GlobalScope.launch { // 在后台启动一个新的协程并继续
        delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
        println("World!") // 在延迟后打印输出
    }
    println("Hello,") // 协程已在等待时主线程还在继续
    Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
}

fun testCoroutinesByBlocking() {
    GlobalScope.launch { // 在后台启动一个新的协程并继续
        delay(1000L)
        println("World!")
    }
    println("Hello,") // 主线程中的代码会立即执行
    runBlocking {     // 但是这个表达式阻塞了主线程
        delay(2000L)  // ……我们延迟 2 秒来保证 JVM 的存活
    }
}

suspend fun testCoroutinesByJob() {
    val job = GlobalScope.launch { // 启动一个新协程并保持对这个作业的引用
        delay(1000L)
        println("World!")
    }
    println("Hello,")
    job.join() // 等待直到子协程执行结束
}


fun testRange() {
    val list = listOf("a", "b", "c")

    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }
    if (list.size !in list.indices) {
        println("list size is out of valid list indices range, too")
    }
}


fun test(a: Int, b: (num1: Int, num2: Int) -> Int): Int {
    println("------" + (a + b(3, 5)))
    return a + b.invoke(3, 5)
}

fun testIn() {
    val x = 10
    val y = 9
    if (x in 1..y + 1) {
        println("fits in range")
    }
}

fun testFold() {
    val items = listOf(1, 2, 3, 4, 5)

    // Lambdas 表达式是花括号括起来的代码块。
    items.fold(0) {
        // 如果一个 lambda 表达式有参数，前面是参数，后跟“->”
            acc: Int, i: Int ->
        print("acc = $acc, i = $i, ")
        val result = acc + i
        println("result = $result")
        // lambda 表达式中的最后一个表达式是返回值：
        result
    }

    // lambda 表达式的参数类型是可选的，如果能够推断出来的话：
    val joinedToString = items.fold("Elements:") { acc, i -> "$acc $i" }

    // 函数引用也可以用于高阶函数调用：
    val product = items.fold(1, Int::times)

    println("joinedToString = $joinedToString")
    println("product = $product")
}

fun testLambda() {
    //    val method : Int
//    get() = (1..1000).shuffled().first()
//
//    private inline fun<T> T.mApply(lambda:T.()->Unit):T{
//        lambda()
//        return this
//    }

    val a = "ab"
    val b = "cde"
    val hill = { a: String, b: String -> a.length < b.length }
    println(hill("ab", "cdf"))
}

fun testStrReplace() {
//sampleStart
    var a = 1
    // 模板中的简单名称：
    val s1 = "a is $a"

    a = 2
    // 模板中的任意表达式：
    val s2 = "${s1.replace("is", "was")}, but now is $a"
//sampleEnd
    println(s2)
}
