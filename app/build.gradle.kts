/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    // 编译 SDK 版本
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        // 应用 id
        applicationId = "com.saint.struct"
        // 最低支持版本
        minSdk = libs.versions.minSdk.get().toInt()
        // 目标 SDK 版本
        targetSdk = libs.versions.targetSdk.get().toInt()
        // 应用版本号
        versionCode = 1
        // 应用版本名
        versionName = "1.0.0"

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        // 开启 Dex 分包
        multiDexEnabled = true

        ndk {
            // 设置支持的 SO 库构架，注意这里要根据你的实际情况来设置
            abiFilters += listOf("armeabi-v7a")
        }
        signingConfig = signingConfigs.getByName("debug")
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file(rootProject.rootDir.absolutePath + "/saint_key.jks")
            keyAlias = "saint"
            keyPassword = "12345678"
//            storeFile = file(Configuration.SigningConfigs.store_file)
            storePassword = "12345678"
            enableV1Signing = true
            enableV2Signing = true
        }
        create("release") {
            keyAlias = "saint"
            keyPassword = "12345678"
            storeFile = file(rootProject.rootDir.absolutePath + "/saint_key.jks")
            storePassword = "12345678"
            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("debug")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // 维度
    flavorDimensions += listOf("version")

    productFlavors {
        // 正式线上版本
        create("online") {
            dimension = "version"
            // 版本名后缀
            versionNameSuffix = "_online"
            // 是否使用线上环境
            buildConfigField("boolean", "IS_ONLINE_ENV", "true")
        }

        // 测试版本
        create("offline") {
            dimension = "version"
            // 应用包名后缀
            applicationIdSuffix = ".offline"
            // 版本名后缀
            versionNameSuffix = "_offline"
            // 是否使用线上环境
            buildConfigField("boolean", "IS_ONLINE_ENV", "false")
        }

        // 开发版本
        create("dev") {
            dimension = "version"
            // 应用包名后缀
            applicationIdSuffix = ".dev"
            // 版本名后缀
            versionNameSuffix = "_dev"
            // 是否使用线上环境
            buildConfigField("boolean", "IS_ONLINE_ENV", "false")
        }
    }

//    allprojects {
//        gradle.projectsEvaluated {
//            tasks.withType(JavaCompile) {
//                options.compilerArgs << "-Xlint:unchecked" << "-Xlint:deprecation"
//            }
//        }
//    }

    sourceSets {
        named("main") {
            java.srcDirs("src/main/java", "src/main/kotlin")
            aidl.srcDirs("src/main/aidl")
            jniLibs.srcDirs("libs", "src/main/jniLibs")
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    // kotlin Jvm 版本
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += arrayOf("-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi")
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.FlowPreview"
    }
}

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
        arg("AROUTER_GENERATE_DOC", "enable")
    }

    correctErrorTypes = true
}

dependencies {
    kapt(libs.androidx.room.compiler)
    kapt(libs.google.hilt.compiler)
//    kapt(libs.androidx.hilt.viewmodel.compiler)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.databinding)
    implementation(libs.bundles.squareup)
    implementation(libs.bundles.google)
    implementation(libs.bundles.reactivex)
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.androidx.paging)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.bundles.androidx.room)

    //glide
    implementation(libs.glide) {
        exclude(group = "com.android.support")
    }

    //permission
    implementation(libs.permission)

    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":biometric"))
}



