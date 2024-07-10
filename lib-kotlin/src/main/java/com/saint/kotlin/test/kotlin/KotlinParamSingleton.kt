package com.saint.kotlin.test.kotlin

import android.content.Context

class KotlinParamSingleton private constructor(val context: Context) {
    companion object : KotlinSingletonHelper<KotlinParamSingleton, Context>(::KotlinParamSingleton)

    fun doSth() {
        println(context)
    }
}