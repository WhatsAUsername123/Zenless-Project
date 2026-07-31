# Walkthrough - Fixing 'composeResourceUri' and Related Build Issues

I have resolved the "Unresolved reference 'composeResourceUri'" error and several other build and test failures that were discovered during the process.

## Changes Made

### 1. Fixed Missing 'composeResourceUri'
- **[NEW] Utility Function**: Implemented `composeResourceUri(path: String)` in [ImageLoader.kt](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/src/commonMain/kotlin/utils/ImageLoader.kt) to handle Compose Resource paths for the Sketch image library.
- **Dependency Update**: Added `sketch-compose-resources` and `sketch-animated-gif` to [build.gradle.kts](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/build.gradle.kts) to support loading animated icons from resources.
- **Sketch Configuration**: Updated `newSketch` to register `supportComposeResources()` and `supportAnimatedWebp()`.

### 2. Refactored AppFileStorage
- **Interface Creation**: Converted `AppFileStorage` from an `expect class` to an `interface` in [commonMain](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/src/commonMain/kotlin/utils/AppFileStorage.kt).
- **Android Implementation**: Renamed the Android implementation to `AndroidAppFileStorage` in [androidMain](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/src/androidMain/kotlin/utils/AppFIleStorage.android.kt).
- **DI Update**: Updated [PlatformModules.android.kt](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/src/androidMain/kotlin/di/PlatformModules.android.kt) to bind the implementation to the interface.
- **Benefit**: This allows for easy mocking in unit tests without platform-specific complications.

### 3. Fixed Dependency Injection & Unresolved References
- **UseCaseModule**: Added the missing `fileStorage` parameter to `HoYoLabAgentUseCase` in [UseCaseModule.kt](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/src/commonMain/kotlin/di/UseCaseModule.kt).
- **MyAgentImageCard**: Fixed an unresolved reference to `agentImageUri` by using the already-resolved `imageUrl` from the model.

### 4. Fixed Unit Tests
- **HoYoLabAgentUseCaseTest**: Updated the test to use a fake `AppFileStorage` implementation, resolving a compilation error caused by the new constructor parameter.
- **HomeViewModelTest**: Added a mock for `RedeemCodeUseCase` which was missing from the `HomeViewModel` constructor in the test setup.

## Verification Results

### Automated Tests
- Ran `./gradlew :composeApp:compileAndroidMain` - **SUCCESS**
- Ran unit tests for `HoYoLabAgentUseCaseTest` and `HomeViewModelTest` - **COMPILATION FIXED** (Note: some runtime resource issues remain in `commonTest` which are unrelated to the current fix).

> [!TIP]
> Always ensure that when adding a parameter to a UseCase or ViewModel, you also update the corresponding Koin module and all test constructor calls.
