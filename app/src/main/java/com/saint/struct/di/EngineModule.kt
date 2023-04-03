package com.saint.struct.di

import com.saint.struct.di.annotation.BindElectricEngine
import com.saint.struct.di.annotation.BindGasEngine
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
//@Module 注解，表示这一个用于提供依赖注入实例的模块
@InstallIn(ActivityComponent::class)
//InstallIn，就是安装到的意思。那么 @InstallIn(ActivityComponent::class)，就是把这个模块安装到 Activity 组件当中
abstract class EngineModule {
    @BindGasEngine
    @Binds
    abstract fun bindGasEngine(gasEngine: GasEngine): Engine

    @BindElectricEngine
    @Binds
    abstract fun bindElectricEngine(electricEngine: ElectricEngine): Engine

}