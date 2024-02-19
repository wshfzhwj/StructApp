package com.saint.kotlin.test.kotlin

//密封类，所有成员都继承本类
//当某个成员需要特殊属性的时候，用枚举就比较难实现这个需求
sealed class MyScore {
    object FAIL: MyScore()//分数差
    object PASS : MyScore()//分数及格
    object WELL : MyScore()//分数良好
    class EXCELLENT(val name: String) : MyScore()//分数优秀

}

class Teachers(private val myScore: MyScore) {
    fun show() = when (myScore) {
        is MyScore.FAIL -> "分数差"
        is MyScore.PASS -> "分数及格"
        is MyScore.WELL -> "分数良好"
        is MyScore.EXCELLENT -> "分数优秀，name=${myScore.name}"
    }
}

fun main() {
    println(Teachers(MyScore.FAIL).show())
    println(Teachers(MyScore.EXCELLENT("kotlin")).show())
}
