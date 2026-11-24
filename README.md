# HyperOS Experience 2 — v0.1.0 foundation

Project Android Studio (Kotlin + Jetpack Compose) for a lightweight, HyperOS-inspired launcher prototype.

## Target
- Redmi A3, Android Go, 4 GB RAM
- No root and no custom ROM
- minSdk 26, targetSdk 35
- Application ID: `com.dani.hyperosexperience`

## Open and build
1. Extract this ZIP.
2. Open the `HyperOS-Experience-2` folder in Android Studio.
3. Allow Gradle sync and install the Android SDK Platform 35 if prompted.
4. Connect the Redmi A3 with USB debugging enabled.
5. Run the `app` configuration.

To create an APK: **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

## Current prototype
- Declares itself as a HOME/launcher activity.
- Reads apps that expose a launcher activity.
- Basic app drawer and search.
- Lightweight Compose UI.

## Planned milestones
- v0.2: app icons, persistent home grid, folders, wallpaper, hide-app list.
- v0.3: HyperOS-style Control Center companion overlay; only supported system actions will be wired.
- v0.4: notification listener and notification shade companion.
- v0.5: lock-screen companion and app-lock flow.
- v0.6: split-screen launch-pair shortcuts where Android/device firmware permits.
- v1.0: performance pass, accessibility review, device testing.

## Important Android limitations
This is an independent launcher, not Xiaomi firmware. It cannot replace Xiaomi framework components.
- A third-party app cannot fully replace the secure Android keyguard. The real system lock screen remains responsible for device security.
- Notification access must be granted manually by the user.
- Some quick settings toggles are restricted by Android; unsupported actions will open the relevant system panel/settings instead.
- Hide Apps initially hides apps only inside this launcher, not from Android Settings or other launchers.
- App Lock is an extra privacy barrier, not a guarantee against force-stop, uninstall, safe mode, or access through system settings.
- Split-screen behavior is controlled by Android and the device firmware; the app can provide shortcuts/requests but cannot guarantee support for every app.

## Notes
This is a starter foundation, not a finished HyperOS clone. Build and test on the actual Redmi A3 before daily use.
