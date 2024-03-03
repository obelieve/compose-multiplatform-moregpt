
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
//import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.6.10"
    id("app.cash.sqldelight") version "2.0.1"
}

kotlin {
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        moduleName = "composeApp"
//        browser {
//            commonWebpackConfig {
//                outputFileName = "composeApp.js"
//            }
//        }
//        binaries.executable()
//    }
    
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    
    jvm("desktop")
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    val multiplatformSettings = "1.0.0"
    val coroutinesVersion = "1.6.4"

    val ktorVersion = "2.3.7"
    val sqlDelightVersion = "2.0.1"
    val dateTimeVersion = "0.4.1"
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            //sqldelight
            implementation("io.ktor:ktor-client-android:$ktorVersion")
            implementation("app.cash.sqldelight:android-driver:$sqlDelightVersion")
            //ktor
            implementation(libs.ktor.client.okhttp)
            //okhttp
            implementation("com.squareup.okhttp3:okhttp:4.11.0")
            //sse
            implementation("com.squareup.okhttp3:okhttp-sse:4.11.0")
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.material3)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            //序列化
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
            // #1 - Basic settings
            implementation("com.russhwolf:multiplatform-settings-no-arg:$multiplatformSettings")
            // #2 - For custom class serialization
            implementation("com.russhwolf:multiplatform-settings-serialization:$multiplatformSettings")
            // #3 - For observing values as flows
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            implementation("com.russhwolf:multiplatform-settings-coroutines:$multiplatformSettings")
            //moko-mvvm
            // compose multiplatform
            implementation("dev.icerock.moko:mvvm-compose:0.16.1") // api mvvm-core, getViewModel for Compose Multiplatform
            implementation("dev.icerock.moko:mvvm-flow-compose:0.16.1") // api mvvm-flow, binding extensions for Compose Multiplatform
            implementation("dev.icerock.moko:mvvm-livedata-compose:0.16.1") // api mvvm-livedata, binding extensions for Compose Multiplatform

//            implementation("dev.icerock.moko:mvvm-core:0.16.1") // only ViewModel, EventsDispatcher, Dispatchers.UI
//            implementation("dev.icerock.moko:mvvm-flow:0.16.1") // api mvvm-core, CFlow for native and binding extensions
//            implementation("dev.icerock.moko:mvvm-livedata:0.16.1") // api mvvm-core, LiveData and extensions
//            implementation("dev.icerock.moko:mvvm-state:0.16.1") // api mvvm-livedata, ResourceState class and extensions
//            implementation("dev.icerock.moko:mvvm-livedata-resources:0.16.1") // api mvvm-core, moko-resources, extensions for LiveData with moko-resources
//            implementation("dev.icerock.moko:mvvm-flow-resources:0.16.1") // api mvvm-core, moko-resources, extensions for Flow with moko-resources

            //precompose
            val precompose_version = "1.6.0-beta02"
            api(compose.animation)
            api("moe.tlaster:precompose:$precompose_version")
            //webview
//            api("io.github.kevinnzou:compose-webview-multiplatform:1.8.8")
            //sqldelight
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            implementation("app.cash.sqldelight:runtime:$sqlDelightVersion")

            implementation("org.jetbrains.kotlinx:kotlinx-datetime:$dateTimeVersion")
            //constraintlayout
            implementation("tech.annexflow.compose:constraintlayout-compose-multiplatform:0.2.3")
            //lottie
            implementation("io.github.alexzhirkevich:compottie:1.1.0")
            //ktor
            implementation(libs.ktor.client.core)
            implementation("io.ktor:ktor-client-logging:$ktorVersion")
        }

        iosMain.dependencies {
            //sqldelight
            implementation("io.ktor:ktor-client-darwin:$ktorVersion")
            implementation("app.cash.sqldelight:native-driver:$sqlDelightVersion")
            //ktor
            implementation(libs.ktor.client.darwin)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation("app.cash.sqldelight:sqlite-driver:$sqlDelightVersion")
        }
    }
    // export correct artifact to use all classes of library directly from Swift
    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
//            export("dev.icerock.moko:mvvm-core:0.16.1")
//            export("dev.icerock.moko:mvvm-livedata:0.16.1")
//            export("dev.icerock.moko:mvvm-livedata-resources:0.16.1")
//            export("dev.icerock.moko:mvvm-state:0.16.1")
        }
    }
}

sqldelight {
    databases {
        create("AppDatabases") {
            packageName.set("com.zxy.core.sqldelight")
        }
    }
}

android {
    namespace = "co.moregpt.ai"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        applicationId = "co.moregpt.ai"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        debug{
            android.buildFeatures.buildConfig = true
            buildConfigField("String", "API_TOKEN", "\"\"")
            buildConfigField("String", "CHAT_BASE_URL", "\"https://api.openai.com\"")
        }
        release {
            android.buildFeatures.buildConfig = true
            isMinifyEnabled = true
            buildConfigField("String", "API_TOKEN", "\"\"")
            buildConfigField("String", "CHAT_BASE_URL", "\"https://api.openai.com\"")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        testImplementation("junit:junit:4.12")
        androidTestImplementation("com.android.support.test:runner:1.0.2")
        androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
        implementation("com.github.obelieve:Frame:1.0.0")
        implementation("org.greenrobot:eventbus:3.1.1")
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
}

compose.experimental {
    web.application {}
}
