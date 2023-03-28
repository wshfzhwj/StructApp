package com.saint.struct.di.annotation

import javax.inject.Qualifier

@Qualifier
//@Retention，是用于声明注解的作用范围，选择 AnnotationRetention.BINARY 表示该注解在编译之后会得到保留，但是无法通过反射去访问这个注解
@Retention(AnnotationRetention.BINARY)
annotation class BindElectricEngine
