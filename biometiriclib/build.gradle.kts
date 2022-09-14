plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = Configuration.AppConfigs.compile_sdk_version

    defaultConfig {
        minSdk = Configuration.AppConfigs.min_sdk_version
        targetSdk= Configuration.AppConfigs.target_sdk_version
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        named("main") {
            java.srcDirs("src/main/java", "src/main/kotlin", "src/main/aidl")
            jniLibs.srcDirs("libs", "jniLibs")
        }
    }
}

dependencies {
    implementation(Configuration.Dependencies.androidx_appcompat)
    // design
    implementation(Configuration.Dependencies.androidx_material)
    //test
    testImplementation(Configuration.Dependencies.test_junit)
    androidTestImplementation(Configuration.Dependencies.android_test_runner)
    androidTestImplementation(Configuration.Dependencies.android_test_espresso_core)
}