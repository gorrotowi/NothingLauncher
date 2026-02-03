# CLAUDE.md - AI Assistant Guide for NothingLauncher

## Project Overview

**NothingLauncher** is a minimalist Android launcher application featuring a clean "nothing" aesthetic. It provides a simple home screen interface to launch installed applications with automatic light/dark theme support.

- **Package ID:** `com.gorrotowi.nothinglauncher`
- **License:** GPL-3.0
- **Language:** Kotlin
- **Min SDK:** 21 (Android 5.0 Lollipop)
- **Target SDK:** 34 (Android 14)

## Quick Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run unit tests
./gradlew test

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Clean build
./gradlew clean

# Check dependencies
./gradlew dependencies
```

## Project Structure

```
NothingLauncher/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/gorrotowi/nothinglauncher/
│   │   │   │   ├── MainActivity.kt           # Home screen with "Nothing" button
│   │   │   │   ├── NothingBaseActivity.kt    # Base activity (notch handling, theme)
│   │   │   │   └── applist/
│   │   │   │       ├── AppListActivity.kt    # App list screen (Jetpack Compose)
│   │   │   │       └── AppItem.kt            # Data class for app items
│   │   │   ├── res/
│   │   │   │   ├── layout/                   # XML layouts (MainActivity only)
│   │   │   │   ├── values/                   # Colors, strings, styles (light)
│   │   │   │   ├── values-night/             # Dark theme colors
│   │   │   │   └── font/                     # Custom font (Questrial)
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                             # Unit tests
│   │   └── androidTest/                      # Instrumented tests
│   └── build.gradle                          # App module config
├── build.gradle                              # Root build config
├── settings.gradle
└── gradle/wrapper/
```

## Architecture

### Key Components

| Component | Location | Purpose |
|-----------|----------|---------|
| `MainActivity` | `MainActivity.kt` | Home screen entry point, displays "Nothing" button |
| `AppListActivity` | `applist/AppListActivity.kt` | Lists installed apps using Jetpack Compose LazyColumn |
| `AppItem` | `applist/AppItem.kt` | Data class holding app name and package name |
| `NothingBaseActivity` | `NothingBaseActivity.kt` | Base class with shared functionality |

### Component Flow

```
MainActivity (Home)
    └─[Click "Nothing"]─→ AppListActivity (App List - Compose UI)
                              ├─[Tap app]─→ Launch app → Finish
                              └─[Long press]─→ Uninstall dialog
```

### Design Patterns Used

1. **Base Activity Pattern** - `NothingBaseActivity` provides common functionality (notch handling, theme setup)
2. **Jetpack Compose** - Modern declarative UI for the app list screen
3. **View Binding** - Type-safe view access for XML-based screens (MainActivity)
4. **Composable Functions** - `AppList`, `AppListItem`, `NothingTheme` for modular UI

### Compose Architecture (AppListActivity)

The app list screen uses Jetpack Compose with:
- `NothingTheme` - Custom Material3 theme matching the app's light/dark aesthetic
- `LazyColumn` - Efficient scrollable list replacing RecyclerView
- `combinedClickable` - Handles tap and long-press gestures

## Key Files to Know

### Entry Points
- `app/src/main/AndroidManifest.xml` - App configuration, permissions, activity declarations
- `app/src/main/java/.../MainActivity.kt` - Main entry point (launcher activity)

### Build Configuration
- `app/build.gradle` - Dependencies, SDK versions, Compose configuration
- `build.gradle` (root) - Plugin versions (Kotlin 1.9.22, AGP 8.2.0)
- `gradle.properties` - AndroidX enabled, Jetifier enabled

### UI/Styling
- `app/src/main/res/values/colors.xml` - Light theme colors
- `app/src/main/res/values-night/colors.xml` - Dark theme colors
- `app/src/main/res/values/styles.xml` - Theme definition (DayNight)
- `app/src/main/res/font/questrial.xml` - Custom font (used in both XML and Compose)

## Development Guidelines

### Code Style
- Use official Kotlin code style (configured in `gradle.properties`)
- Prefer Kotlin idioms (data classes, extension functions, null safety)
- Use Jetpack Compose for new UI development
- Use View Binding for legacy XML-based screens

### Adding New Compose Screens
1. Create Activity extending `NothingBaseActivity`
2. Use `setContent { }` to define Compose UI
3. Wrap content in `NothingTheme` for consistent theming
4. Register in `AndroidManifest.xml`

### Theming
The app uses dual theming approaches:
- **XML screens**: `Theme.AppCompat.DayNight.NoActionBar`
- **Compose screens**: Custom `NothingTheme` with Material3 color schemes

Colors:
- Light theme: Black text (#000000) on white background (#FFFFFF)
- Dark theme: White text (#FFFFFF) on black background (#000000)

### Permissions
Current permissions in `AndroidManifest.xml`:
- `REQUEST_DELETE_PACKAGES` - Required for app uninstallation feature

## Testing

### Test Locations
- Unit tests: `app/src/test/java/com/gorrotowi/nothinglauncher/`
- Instrumented tests: `app/src/androidTest/java/com/gorrotowi/nothinglauncher/`

### Test Framework
- JUnit 4.13.2 for unit tests
- AndroidX Test + Espresso for instrumented tests
- Compose UI Testing available via `ui-test-junit4`
- Test runner: `androidx.test.runner.AndroidJUnitRunner`

## Dependencies Summary

| Library | Version | Purpose |
|---------|---------|---------|
| kotlin-stdlib | 1.9.22 | Kotlin standard library |
| appcompat | 1.6.1 | AndroidX compatibility |
| core-ktx | 1.12.0 | Kotlin extensions for Android |
| constraintlayout | 2.1.4 | Layout system for XML screens |
| compose-bom | 2024.01.00 | Jetpack Compose Bill of Materials |
| material3 | (BOM) | Material Design 3 components |
| activity-compose | 1.8.2 | Compose integration with Activity |

## Common Tasks

### Adding a new Compose screen
1. Create Kotlin class extending `NothingBaseActivity`
2. Use `setContent { NothingTheme { /* UI */ } }`
3. Define Composable functions for UI components
4. Register in `AndroidManifest.xml`

### Adding a new XML-based screen
1. Create Kotlin class extending `NothingBaseActivity`
2. Create layout XML in `res/layout/`
3. Use View Binding for view access
4. Register in `AndroidManifest.xml`

### Modifying theme colors
1. Update `app/src/main/res/values/colors.xml` for light theme
2. Update `app/src/main/res/values-night/colors.xml` for dark theme
3. Update `NothingTheme` in `AppListActivity.kt` for Compose screens

### Adding new dependencies
1. Add to `app/build.gradle` under `dependencies`
2. For Compose libraries, use BOM versioning: `implementation platform('androidx.compose:compose-bom:VERSION')`
3. Run `./gradlew dependencies` to verify resolution

## Build Requirements

- **Gradle:** 8.2+
- **Android Gradle Plugin:** 8.2.0
- **Kotlin:** 1.9.22
- **JDK:** 17
- **Compose Compiler:** 1.5.8

## Git Workflow

- Feature branches should be prefixed appropriately (e.g., `feature/`, `fix/`, `claude/`)
- Keep commits focused and descriptive
- The main branch should remain stable

## Resources

- App icons are in `mipmap-*dpi/` directories (multiple density support)
- Adaptive icons configured for Android 8+ in `mipmap-anydpi-v26/`
- Custom font `Questrial` used throughout the UI (both XML and Compose)
