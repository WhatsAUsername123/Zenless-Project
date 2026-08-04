# Implementation Plan - Fix Text Argument Type Mismatch in RandomGifFiller.kt

The compilation error in `RandomGifFiller.kt` is caused by passing a property of type `Any` (`GifSnapshot.subtitle`) to the `Text` composable, which expects a `String` or `AnnotatedString`. Additionally, the `subtitle` property in `GifSnapshot` is currently uninitialized, which is invalid Kotlin for a non-abstract property.

## Proposed Changes

### [Component Name]

#### [MODIFY] [RandomGifFiller.kt](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/src/androidMain/kotlin/ui/components/RandomGifFiller.kt)

1.  Update the `GifSnapshot` data class to properly define and initialize the `subtitle` property. I will make it a `String` and, for now, derive it from `archiveId` or make it an explicit field in the constructor.
2.  If moved to the constructor, update all `GifSnapshot` instantiation sites.
3.  Alternatively, if it should be derived, use a custom getter.

I will move `subtitle` to the constructor as a `String` to allow flexibility, and update the existing list of snapshots.

## Verification Plan

### Automated Tests
- Run `./gradlew :composeApp:compileAndroidMain` to verify that the compilation error is resolved.

### Manual Verification
- None required as this is a compilation fix.
