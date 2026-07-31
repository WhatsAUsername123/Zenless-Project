# Fix Unresolved Reference 'composeResourceUri'

The build is failing because `composeResourceUri` is missing. This function is intended to provide a URI that the Sketch image loading library can use to load assets from Compose Resources (like animated GIFs).

## Proposed Changes

### Build Configuration

#### [MODIFY] [composeApp/build.gradle.kts](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/build.gradle.kts)
- Add `sketch-compose-resources` to support loading from Compose Resources.
- Add `sketch-animated-gif` to support decoding animated GIFs in Sketch.

### Utilities

#### [MODIFY] [utils/ImageLoader.kt](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/src/commonMain/kotlin/utils/ImageLoader.kt)
- Define `composeResourceUri(path: String)` utility function in the `utils` package.
- Update `newSketch` configuration to enable Compose Resources and Animated GIF support in the Sketch component registry.

## Verification Plan

### Automated Tests
- Run `./gradlew :composeApp:compileAndroidMain` to verify the unresolved reference is fixed.

### Manual Verification
- Deploy the app and navigate to a screen using `TopDestinationIcon` (e.g., the main navigation) to verify that animated icons (if any) are rendered correctly.
