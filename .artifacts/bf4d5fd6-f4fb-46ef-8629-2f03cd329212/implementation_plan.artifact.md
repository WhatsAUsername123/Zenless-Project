# Fix Unresolved reference 'System' in RedeemCodeRepositoryImpl.kt

The user is experiencing a build error in `RedeemCodeRepositoryImpl.kt` where `Clock.System` is not resolved. This is because the file imports `kotlinx.datetime.Clock`, but the project is using Kotlin 2.3.20, where `Clock` and `Clock.System` are now part of the `kotlin.time` package in the standard library. Other files in the project already use `kotlin.time.Clock`.

## Proposed Changes

### feature:redeemcode

#### [MODIFY] [RedeemCodeRepositoryImpl.kt](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/src/commonMain/kotlin/feature/redeemcode/data/repository/RedeemCodeRepositoryImpl.kt)
- Change `import kotlinx.datetime.Clock` to `import kotlin.time.Clock`.

## Verification Plan

### Automated Tests
- Run `./gradlew :composeApp:compileAndroidMain` to verify the fix.
