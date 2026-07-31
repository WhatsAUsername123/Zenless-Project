# Move `RandomGifFiller` to shared components and add to `OtherInfoCard`

The user wants to add the `RandomGifFiller` component. Since I've already implemented a multiplatform-safe version of this component, I will move it to a shared location and then integrate it into the `OtherInfoCard` as requested.

## Proposed Changes

### UI Components
#### [NEW] [RandomGifFiller.kt](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/src/commonMain/kotlin/ui/components/RandomGifFiller.kt)
- Move the `RandomGifFiller` implementation here from `SettingScreen.kt`.
- Use the `rememberGifImageLoader()` utility for platform-specific GIF support.

### Feature: Setting
#### [MODIFY] [SettingScreen.kt](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/src/commonMain/kotlin/feature/setting/presentation/SettingScreen.kt)
- Remove the local definition of `RandomGifFiller`.
- Import it from the new shared location.

#### [MODIFY] [OtherInfoCard.kt](file:///C:/Users/matthew/StudioProjects/Zenless-Project/composeApp/src/commonMain/kotlin/feature/setting/components/OtherInfoCard.kt)
- Import `RandomGifFiller`.
- Add `RandomGifFiller` at the bottom of the `ContentCard` in `OtherInfoCard`.

## Verification Plan

### Automated Tests
- Run `./gradlew :androidApp:assembleDevDebug` to verify the build.

### Manual Verification
- Verify that the GIF filler appears in both the Settings Screen and the Other Info Card.
- Verify that clicking the GIF changes it.
