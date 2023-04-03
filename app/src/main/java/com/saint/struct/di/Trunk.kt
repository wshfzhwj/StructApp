package com.saint.struct.di

import com.saint.struct.di.annotation.BindElectricEngine
import com.saint.struct.di.annotation.BindGasEngine
import javax.inject.Inject

class Trunk @Inject constructor(val driver: Driver){

    @BindGasEngine
    @Inject
    lateinit var gasEngine: Engine

    @BindElectricEngine
    @Inject
    lateinit var electricEngine: Engine


    fun deliver(){
        gasEngine.start()
        electricEngine.start()
        println("Truck is delivering cargo. Driven by $driver")
        gasEngine.shutdown()
        electricEngine.shutdown()
    }
}