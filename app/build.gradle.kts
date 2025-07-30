import java.util.Properties
import java.io.FileInputStream
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "dev.lucasteixeira.desafioexploradordefilmes"
    compileSdk = 36

    defaultConfig {
        applicationId = "dev.lucasteixeira.desafioexploradordefilmes"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val localPropertiesFile = project.rootProject.file("local.properties")
        val properties = Properties()

        if (localPropertiesFile.exists()) {
            FileInputStream(localPropertiesFile).use { input ->
                properties.load(input)
            }
        } else {

            println("AVISO: Arquivo local.properties não encontrado na raiz do projeto.")
        }
        val apiKeyFromProperties = properties.getProperty("API_KEY")

        if (apiKeyFromProperties == null || apiKeyFromProperties.trim().isEmpty()) {
            println("AVISO: A propriedade API_KEY não foi encontrada ou está vazia no arquivo local.properties.")
            buildConfigField("String", "API_KEY", "\"\"")
        } else {

            buildConfigField("String", "API_KEY", "\"$apiKeyFromProperties\"")
        }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.coil.compose)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    implementation(libs.compose.runtime.livedata)
    implementation(libs.navigation.compose)

    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.activity.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}