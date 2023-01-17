rootProject.name = "StructApp"

include (":app",":biometric")

//插件脚本
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven ("https://maven.aliyun.com/repository/public/")
        google()
        mavenCentral()
    }
}

//依赖脚本
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven ("https://maven.aliyun.com/repository/public/")
    }
}

