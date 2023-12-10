plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Add the Safe Args Gradle plugin from the Navigation component
    id("androidx.navigation.safeargs")
    // Add ksp plugin for Room
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.journey"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.journey"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    // Versions
    val espressoVersion = "3.5.1"
    val markwonVersion = "4.6.2"
    val roomVersion = "2.6.1"
    val retrofitVersion = "2.9.0"
    val okHttpVersion = "4.12.0"

    // AndroidX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // Material Design
    implementation("com.google.android.material:material:1.10.0")

    // Unit Testing Libraries
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito:mockito-inline:3.12.4") // For final class mocking
    testImplementation("org.robolectric:robolectric:4.8") // For Robolectric

    // Android Instrumented Testing Libraries
    androidTestImplementation("androidx.test:core-ktx:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0") // For LiveData testing
    androidTestImplementation("androidx.fragment:fragment-testing:1.6.2")

    // Coroutine Testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // For ViewModel Testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // For Navigation Testing
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.5")

    // Optional: MockWebServer for testing network calls
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")

    // Libraries
    implementation("io.coil-kt:coil:2.5.0") // Coil for loading images
    implementation("com.makeramen:roundedimageview:2.3.0") // Rounded Image View
    implementation("io.noties.markwon:core:$markwonVersion") // Markdown support with Markwon
    implementation("io.noties.markwon:editor:$markwonVersion")
    implementation("io.noties.markwon:ext-strikethrough:$markwonVersion")
    implementation("io.noties.markwon:ext-tables:$markwonVersion")
    implementation("io.noties.markwon:image:$markwonVersion")

    // Room database
    ksp("androidx.room:room-compiler:$roomVersion")

    // Retrofit & OkHttp
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

    // JSON Converter
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
}