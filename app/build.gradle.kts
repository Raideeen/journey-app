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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Add the Kotlin extensions for the Navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")

    // Coil library for loading images
    implementation("io.coil-kt:coil:2.5.0")

    // Material Design
    implementation("com.google.android.material:material:1.10.0")

    // Rounded Image View
    implementation("com.makeramen:roundedimageview:2.3.0")

    // Markdown support with Markwon
    val markwonVersion = "4.6.2"

    implementation("io.noties.markwon:core:$markwonVersion")
    implementation("io.noties.markwon:editor:$markwonVersion")
    implementation("io.noties.markwon:ext-strikethrough:$markwonVersion")
    implementation("io.noties.markwon:ext-tables:$markwonVersion")
    implementation("io.noties.markwon:image:$markwonVersion")

    // Picasso library for loading images
    implementation("com.squareup.picasso:picasso:2.71828")

    // Room database
    val roomVersion = "2.6.1"

    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // Coroutines
    implementation("androidx.room:room-ktx:$roomVersion")

    // Material Design
    implementation("com.google.android.material:material:1.10.0")

    // Retrofit & OkHttp
    val retrofitVersion = "2.9.0"
    val okHttpVersion = "4.12.0"

    // Retrofit & OkHttp
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

    // JSON Converter
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
}