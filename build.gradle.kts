buildscript {
    val compose_version by extra("1.1.0-beta01")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.0" apply false
    id("com.android.library") version "7.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.jetbrains.kotlin.jvm") version "1.6.21" apply false
}
apply("$rootDir/scripts/publish-root.gradle")
tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}
