plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

buildscript {
    extra.apply {
        set("PUBLISH_GROUP_ID", "io.github.t-regbs")
        set("PUBLISH_VERSION", "0.1.0")
        set("PUBLISH_ARTIFACT_ID", "lazerpay-android")
        set("PUBLISH_DESCRIPTION", "Lazerpay Android SDK")
        set("PUBLISH_URL", "https://github.com/t-regbs/lazerpay-android")
        set("PUBLISH_LICENSE_NAME", "Apache License")
        set("PUBLISH_LICENSE_URL", "https://opensource.org/licenses/Apache-2.0")
        set("PUBLISH_DEVELOPER_ID", "t-regbs")
        set("PUBLISH_DEVELOPER_NAME", "Oluwatimilehin Aregbesola")
        set("PUBLISH_DEVELOPER_EMAIL", "aregbestimi@gmail.com")
        set("PUBLISH_SCM_CONNECTION", "scm:git:github.com/t-regbs/lazerpay-android.git")
        set("PUBLISH_SCM_DEVELOPER_CONNECTION", "scm:git:ssh://github.com/t-regbs/lazerpay-android.git")
        set("PUBLISH_SCM_URL", "https://github.com/t-regbs/lazerpay-android/tree/master")
    }
}

apply("${rootProject.projectDir}/scripts/publish-module.gradle")

android {
    defaultConfig {
        compileSdk = Integer.parseInt(libs.versions.android.compile.sdk.get())
        minSdk = Integer.parseInt(libs.versions.android.min.sdk.get())
        targetSdk = Integer.parseInt(libs.versions.android.target.sdk.get())

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    implementation(project(":lazerpay-common"))
    implementation(libs.androidx.corektx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.webkit)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.ktx)

    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt(libs.moshi.codegen)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockito.kotlin)

    androidTestImplementation(libs.test.mockito.android)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.test.espresso)
}
