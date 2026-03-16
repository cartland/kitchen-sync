plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.ksp)
}

kotlin {
    androidLibrary {
        namespace = "com.cartland.kitchensync.data"
        compileSdk =
            libs.versions.android.compileSdk
                .get()
                .toInt()
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
    }

    jvm()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":domain"))
            api(project(":data-local"))
            implementation(libs.kotlin.inject.runtime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.kotlin.inject.compiler)
    add("kspAndroid", libs.kotlin.inject.compiler)
    add("kspIosX64", libs.kotlin.inject.compiler)
    add("kspIosArm64", libs.kotlin.inject.compiler)
    add("kspIosSimulatorArm64", libs.kotlin.inject.compiler)
    add("kspJvm", libs.kotlin.inject.compiler)
}
