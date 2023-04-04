// Top-level build file where you can add configuration options common to all sub-projects/modules.libs.versions.toml
//方式一
//@Suppress("DSL_SCOPE_VIOLATION")  //添加DSL_SCOPE_VIOLATION,处理alias报错
//plugins {
//    //apply false 表示在父项目中声明，但是父项目不会立即加载，可以在子项目中通过ID的方式进行使用
//    alias(libs.plugins.kotlin.android) apply false
//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.android.library) apply false
//    alias(libs.plugins.android.hilt) apply false
//}
//方式二
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
    }
}


tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
