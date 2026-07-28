# Walkthrough - Fixed Unresolved reference 'System'

I have fixed the build error in `RedeemCodeRepositoryImpl.kt` where `Clock.System` could not be resolved.

## Changes

### feature:redeemcode

#### [RedeemCodeRepositoryImpl.kt](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/src/commonMain/kotlin/feature/redeemcode/data/repository/RedeemCodeRepositoryImpl.kt)

Updated the `Clock` import to use the Kotlin standard library version instead of the `kotlinx-datetime` version, aligning with the Kotlin 2.3.20 configuration.

```diff
-import kotlinx.datetime.Clock
+import kotlin.time.Clock
```

## Verification Results

### Automated Tests
- Ran `./gradlew :composeApp:compileAndroidMain` and it finished successfully.
