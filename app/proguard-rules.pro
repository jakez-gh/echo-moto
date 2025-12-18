# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep Kotlin Metadata
-keep class kotlin.Metadata { *; }

# Keep Jetpack Compose
-keep class androidx.compose.** { *; }
-keep class androidx.compose.runtime.** { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Keep data classes
-keepclassmembers class com.echo.companion.** {
    <init>(...);
}

# Keep ViewModel
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}