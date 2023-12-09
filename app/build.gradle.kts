plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "capstone.product.tim.majorsmatch"
    compileSdk = 34

    defaultConfig {
        applicationId = "capstone.product.tim.majorsmatch"
        minSdk = 21
        //noinspection OldTargetApi
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "API_BASE_URL", "\"https://story-api.dicoding.dev/v1/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
    testOptions.unitTests.isIncludeAndroidResources = true
}

    dependencies {

        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.10.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("com.google.android.gms:play-services-maps:18.2.0")
        implementation("androidx.paging:paging-runtime-ktx:3.2.1")
        implementation("com.android.support:support-annotations:28.0.0")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")

        testImplementation("androidx.arch.core:core-testing:2.2.0")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.2")
        androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
        androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.2")
        implementation("androidx.paging:paging-runtime-ktx:3.2.1")

        testImplementation("org.mockito:mockito-core:5.5.0")
        testImplementation("org.mockito:mockito-inline:5.2.0")

        implementation("androidx.room:room-runtime:2.6.0")
        implementation ("androidx.room:room-ktx:2.6.0")

        implementation("androidx.datastore:datastore-preferences:1.0.0")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
        implementation("androidx.activity:activity-ktx:1.8.0")
        implementation ("com.github.bumptech.glide:glide:4.16.0")

        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

        implementation("androidx.camera:camera-camera2:1.3.0")
        implementation("androidx.camera:camera-lifecycle:1.3.0")
        implementation ("com.google.android.gms:play-services-maps:18.2.0")
        implementation("androidx.camera:camera-view:1.3.0")
}