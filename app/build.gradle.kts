plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
apply(rootProject.file("ktlint.gradle.kts"))

android {
    defaultConfig {
        applicationId = "com.timilehinaregbesola.lazerpay"
        versionCode = 1
        versionName = "1.0"

        compileSdk = Integer.parseInt(libs.versions.android.compile.sdk.get())
        minSdk = Integer.parseInt(libs.versions.android.min.sdk.get())
        targetSdk = Integer.parseInt(libs.versions.android.target.sdk.get())
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.corektx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

//    implementation(libs.ktlint)
//    implementation(libs.detekt)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.test.espresso)
}
