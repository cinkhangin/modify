import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.vanniktech.maven)
}

android {
    namespace = "com.naulian.modify"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    debugImplementation(libs.compose.ui.tooling)
}

mavenPublishing {
    publishToMavenCentral( automaticRelease = true)

    signAllPublications()

    coordinates(
        groupId = "com.naulian",
        artifactId = "modify",
        version = "0.6.2"
    )
    //./gradlew publishAndReleaseToMavenCentral --no-configuration-cache

    pom {
        name.set("Modify")
        description.set("A Extension library for Jetpack Compose")
        inceptionYear.set("2024")
        url.set("https://github.com/cinkhangin/modify/")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("naulian")
                name.set("Naulian")
                url.set("https://github.com/cinkhangin/")
                email.set("hello@naulian.com")
            }
        }
        scm {
            url.set("https://github.com/cinkhangin/modify/")
            connection.set("scm:git:git://github.com/cinkhangin/modify.git")
            developerConnection.set("scm:git:ssh://git@github.com/cinkhangin/modify.git")
        }
    }
}