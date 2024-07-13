import org.jetbrains.kotlin.gradle.utils.API

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.faizulla.music"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.faizulla.music"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String","API_KEY",  "\"4dd7c02246msh78b9002059c6478p11c501jsn0ddc90a4554f\"")
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
    buildFeatures{
        viewBinding =true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")


    //Room Database
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt ("androidx.room:room-compiler:2.6.1")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")



    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-runtime:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")

    implementation ("androidx.room:room-ktx:2.2.5")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.5")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation ("androidx.activity:activity-ktx:1.1.0")


    implementation ("androidx.test:core:1.2.0")
    testImplementation ("junit:junit:4.13")
    testImplementation ("org.hamcrest:hamcrest-all:1.3")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("org.robolectric:robolectric:4.3.1")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.2.1")
    testImplementation ("com.google.truth:truth:1.0.1")
    testImplementation ("org.mockito:mockito-core:2.21.0")

    androidTestImplementation ("junit:junit:4.13")
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.2.1")
    androidTestImplementation ("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation ("com.google.truth:truth:1.0.1")
    androidTestImplementation ("androidx.test.ext:junit:1.1.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.2.0")
    androidTestImplementation ("org.mockito:mockito-core:2.21.0")


    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")

    
    implementation("com.google.dagger:hilt-android:2.46")
    kapt("com.google.dagger:hilt-android-compiler:2.46")

    implementation ("com.github.bumptech.glide:glide:4.12.0")

    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")


}