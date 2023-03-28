package com.saint.struct.di

import com.saint.struct.interfaces.di.Engine
import javax.inject.Inject

class ElectricEngine @Inject constructor() : Engine {
    override fun start() {
        println("ElectricEngine engine start...")
    }

    override fun shutdown() {
        println("ElectricEngine engine shutdown...")
    }
}