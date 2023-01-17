plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk =  libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
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
    implementation(libs.androidx.appcompat)
    // design
    implementation(libs.material)
    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}