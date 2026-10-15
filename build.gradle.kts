plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.example.luckyclub"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.example.luckyclub"
        minSdk = 23
        targetSdk = 35
        versionCode = 2
        versionName = "1.1"
    }
}
