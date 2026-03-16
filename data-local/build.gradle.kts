plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

kotlin {
    androidLibrary {
        namespace = "com.cartland.kitchensync.datalocal"
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
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.kotlin.inject.runtime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }

    sourceSets.all {
        languageSettings.optIn("kotlin.time.ExperimentalTime")
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    add("kspCommonMainMetadata", libs.kotlin.inject.compiler)

    add("kspAndroid", libs.androidx.room.compiler)
    add("kspAndroid", libs.kotlin.inject.compiler)

    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosX64", libs.kotlin.inject.compiler)

    add("kspIosArm64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.kotlin.inject.compiler)

    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.kotlin.inject.compiler)

    add("kspJvm", libs.androidx.room.compiler)
    add("kspJvm", libs.kotlin.inject.compiler)
}

configurations.named("kspCommonMainMetadata") {
    exclude(group = "androidx.room", module = "room-compiler")
}
