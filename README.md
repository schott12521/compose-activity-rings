# Compose Activity Rings

![image](https://github.com/user-attachments/assets/9a36262e-1f1e-4758-a7df-92dd7880e1b5)

Simple Kotlin Multiplatform library supporting iOS, Android, Web (WasmJS), and Desktop (JVM) to create Activity Rings similar to Apple's [Activity Rings for Fitness tracking](https://www.apple.com/watch/close-your-rings/)

# Usage

For now this package is only available via github packages.

Add the following to your settings.gradle.kts, making sure that you have set or provided mavenUser and mavenPassword to your github username and a Personal Access Token.
```
maven {
    url = uri("https://maven.pkg.github.com/schott12521/compose-activity-rings")
    credentials {
        username = mavenUser
        password = mavenPassword
    }
}
```
Then you can depend on the [latest version](https://github.com/schott12521/compose-activity-rings/releases) of the library, in libs.versions.toml:

```
activity-rings = "$latestVersion"

compose-activity-rings = { group = "com.slapps.compose.activityrings", name = "compose-activity-rings", version.ref = "activity-rings" }
```

## Try it

Wanna see what the library feels like? The latest version builds and deploys the Kotlin/WasmJS target to github pages: https://schott12521.github.io/compose-activity-rings/

## Docs

`ActivityRing` can be created via:

```
ActivityRings(
    gap: Dp,
    size: Dp,
    lineWidth: Dp,
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 350, easing = FastOutSlowInEasing),
    vararg rings: Ring
)
```

where `Ring` can be created via:

```
data class Ring(
    val progress: Float,
    val color: Color
)
```

For more reference, check the example app usage [here](https://github.com/schott12521/compose-activity-rings/blob/main/example/composeApp/src/commonMain/kotlin/App.kt)
