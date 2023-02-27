object Build {

    private const val androidBuildToolsVersion = "7.4.1"

    const val androidApplication = "com.android.application$androidBuildToolsVersion"

    const val androidLibrary = "com.android.library$androidBuildToolsVersion"

    const val kotlinAndroid = "org.jetbrains.kotlin.android$androidBuildToolsVersion"

    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

    const val hiltAndroid = "com.google.dagger:hilt-android-gradle-plugin:${Hilt.hiltVersion}"

    const val sqlDelightGradlePlugin = "com.squareup.sqldelight:gradle-plugin:${SqlDelight.version}"
}