plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    // 编译 SDK 版本
    compileSdk = Configuration.AppConfigs.compile_sdk_version

    defaultConfig {
        // 应用 id
        applicationId = Configuration.AppConfigs.application_id
        // 最低支持版本
        minSdk = Configuration.AppConfigs.min_sdk_version
        // 目标 SDK 版本
        targetSdk= Configuration.AppConfigs.target_sdk_version
        // 应用版本号
        versionCode = Configuration.AppConfigs.version_code
        // 应用版本名
        versionName = Configuration.AppConfigs.version_name

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        // 开启 Dex 分包
        multiDexEnabled = true

        ndk {
            // 设置支持的 SO 库构架，注意这里要根据你的实际情况来设置
            abiFilters += listOf("armeabi-v7a")
        }
    }

    signingConfigs {
        getByName("debug")  {
            storeFile = file(rootProject.rootDir.absolutePath + "/saint_key.jks");
            keyAlias = Configuration.SigningConfigs.key_alias
            keyPassword = Configuration.SigningConfigs.key_password
//            storeFile = file(Configuration.SigningConfigs.store_file)
            storePassword = Configuration.SigningConfigs.store_password
            enableV1Signing = true
            enableV2Signing = true
        }
        create("release") {
            keyAlias = Configuration.SigningConfigs.key_alias
            keyPassword = Configuration.SigningConfigs.key_password
            storeFile = file(Configuration.SigningConfigs.store_file)
            storePassword = Configuration.SigningConfigs.store_password
            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
            aidl.srcDirs( "src/main/aidl")
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
    }
}

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
        arg("AROUTER_GENERATE_DOC", "enable")
    }
}

dependencies {
    // v4
    implementation(Configuration.Dependencies.androidx_legacy)
    // v7
    implementation(Configuration.Dependencies.androidx_appcompat)
    // LifeCycle 拓展
//    implementation(Configuration.Dependencies.android_lifecycle_runtime)
//    implementation(Configuration.Dependencies.android_lifecycle_extensions)
    implementation(Configuration.Dependencies.androidx_lifecycle_runtime)
    implementation(Configuration.Dependencies.androidx_lifecycle_extensions)
    // design
    implementation(Configuration.Dependencies.androidx_material)
    // 约束性布局
    implementation(Configuration.Dependencies.androidx_constraint)
    //volley
    implementation(Configuration.Dependencies.volley)
    // ViewModel 拓展
    implementation(Configuration.Dependencies.androidx_lifecycle_viewmodel_ktx)
    //databinding
    implementation(Configuration.Dependencies.databinding)
    //test
    testImplementation(Configuration.Dependencies.test_junit)
    androidTestImplementation(Configuration.Dependencies.android_test_runner)
    androidTestImplementation(Configuration.Dependencies.android_test_espresso_core)

    //retrofit
    implementation(Configuration.Dependencies.retrofit)
    implementation(Configuration.Dependencies.retrofit_adapter_rxjava2)
    implementation(Configuration.Dependencies.retrofit_converter_gson)
    //rxjava
    implementation(Configuration.Dependencies.rxjava)
    implementation(Configuration.Dependencies.rxandroid)
    // OkHttp
    implementation(Configuration.Dependencies.okhttp)
    implementation(Configuration.Dependencies.okhttp_logging)

    //core-ktx
    implementation(Configuration.Dependencies.androidx_core_ktx)
    // Kotlin
    implementation(Configuration.Dependencies.kotlin_stdlib)
    // 协程
    implementation(Configuration.Dependencies.kotlinx_coroutines_android)
    implementation(Configuration.Dependencies.kotlinx_coroutines_core)
    // gson
    implementation(Configuration.Dependencies.gson)
    // paging
    implementation(Configuration.Dependencies.androidx_paging_runtime)
    implementation(Configuration.Dependencies.androidx_paging_rxjava2)
    //work
    implementation(Configuration.Dependencies.work)
    //room
    implementation (Configuration.Dependencies.androidx_room_runtime)
    implementation (Configuration.Dependencies.androidx_room_ktx)
    //leakcanary
    implementation (Configuration.Dependencies.leakcanary)

    kapt  (Configuration.Dependencies.androidx_room_compiler)
    //glide
    implementation(Configuration.Dependencies.glide) {
        exclude (group = "com.android.support")
    }
    //permission
    implementation (Configuration.Dependencies.permission)


    implementation (project (":biometiriclib"))
}
repositories {
    mavenCentral()
}



