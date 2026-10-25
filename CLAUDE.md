# CLAUDE.md - AI Assistant Guide for NothingLauncher

## Project Overview

**NothingLauncher** is a minimalist Android launcher application featuring a clean "nothing" aesthetic. It provides a simple home screen interface to launch installed applications with automatic light/dark theme support.

- **Package ID:** `com.gorrotowi.nothinglauncher`
- **License:** GPL-3.0
- **Language:** Kotlin
- **Min SDK:** 21 (Android 5.0 Lollipop)
- **Target SDK:** 29 (Android 10)

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
│   │   │   │       ├── AppListActivity.kt    # App list screen
│   │   │   │       ├── AppListAdapter.kt     # RecyclerView adapter
│   │   │   │       └── AppItem.kt            # Data class for app items
│   │   │   ├── res/
│   │   │   │   ├── layout/                   # XML layouts
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
| `AppListActivity` | `applist/AppListActivity.kt` | Lists installed apps, handles launching and uninstall |
| `AppListAdapter` | `applist/AppListAdapter.kt` | RecyclerView adapter for app list |
| `AppItem` | `applist/AppItem.kt` | Data class holding app name and package name |
| `NothingBaseActivity` | `NothingBaseActivity.kt` | Base class with shared functionality |

### Component Flow

```
MainActivity (Home)
    └─[Click "Nothing"]─→ AppListActivity (App List)
                              ├─[Tap app]─→ Launch app → Finish
                              └─[Long press]─→ Uninstall dialog
```

### Design Patterns Used

1. **Base Activity Pattern** - `NothingBaseActivity` provides common functionality (notch handling, theme setup)
2. **View Binding** - Type-safe view access (enabled in build.gradle)
3. **Adapter Pattern** - `AppListAdapter` with observable data source using Kotlin delegates
4. **Callback Pattern** - Lambda-based click listeners for adapter events

## Key Files to Know

### Entry Points
- `app/src/main/AndroidManifest.xml` - App configuration, permissions, activity declarations
- `app/src/main/java/.../MainActivity.kt` - Main entry point (launcher activity)

### Build Configuration
- `app/build.gradle` - Dependencies, SDK versions, build settings
- `build.gradle` (root) - Plugin versions (Kotlin 1.3.61, AGP 3.6.3)
- `gradle.properties` - AndroidX enabled, Jetifier enabled

### UI/Styling
- `app/src/main/res/values/colors.xml` - Light theme colors
- `app/src/main/res/values-night/colors.xml` - Dark theme colors
- `app/src/main/res/values/styles.xml` - Theme definition (DayNight)
- `app/src/main/res/font/questrial.xml` - Custom font

## Development Guidelines

### Code Style
- Use official Kotlin code style (configured in `gradle.properties`)
- Prefer Kotlin idioms (data classes, extension functions, null safety)
- Use View Binding for view access (avoid `findViewById`)

### Adding New Features
1. Extend `NothingBaseActivity` for new activities to inherit notch/theme handling
2. Use AndroidX libraries (project uses `android.useAndroidX=true`)
3. Support both light and dark themes by defining colors in both `values/` and `values-night/`

### Theming
The app uses `Theme.AppCompat.DayNight.NoActionBar` for automatic dark mode support:
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
- JUnit 4.12 for unit tests
- AndroidX Test + Espresso for instrumented tests
- Test runner: `androidx.test.runner.AndroidJUnitRunner`

## Dependencies Summary

| Library | Version | Purpose |
|---------|---------|---------|
| kotlin-stdlib-jdk7 | 1.3.61 | Kotlin standard library |
| appcompat | 1.1.0 | AndroidX compatibility |
| core-ktx | 1.2.0 | Kotlin extensions for Android |
| constraintlayout | 1.1.3 | Modern layout system |
| recyclerview | 1.1.0 | Efficient list rendering |

## Common Tasks

### Adding a new activity
1. Create Kotlin class extending `NothingBaseActivity`
2. Create layout XML in `res/layout/`
3. Register in `AndroidManifest.xml`
4. Use View Binding for view access

### Modifying theme colors
1. Update `app/src/main/res/values/colors.xml` for light theme
2. Update `app/src/main/res/values-night/colors.xml` for dark theme
3. Colors use `colorPrimary`, `colorPrimaryDark`, `colorAccent`, `colorBackground`

### Adding new dependencies
1. Add to `app/build.gradle` under `dependencies`
2. Prefer AndroidX versions of libraries
3. Run `./gradlew dependencies` to verify resolution

## Known Technical Debt

1. **Deprecated Plugin:** Uses `kotlin-android-extensions` (deprecated) - consider migrating to full View Binding
2. **Older Dependencies:** Gradle 5.6.4, AGP 3.6.3, Kotlin 1.3.61 could be updated
3. **Limited Tests:** Only example test files exist, no real test coverage
4. **ProGuard Disabled:** Release builds have `minifyEnabled=false`

## Git Workflow (GitFlow)

The repo follows **GitFlow**. Branch model:

| Branch | Purpose |
|--------|---------|
| `main` | Latest stable release. **Protected**: only `release/*` PRs can merge. |
| `develop` | Integration branch; all features/fixes land here. |
| `feature/*`, `fix/*`, `chore/*`, `claude/*` | Short-lived work branches, branched from `develop`. |
| `release/x.y.z` | Release cut, branched from `develop`. Only these merge into `main`. |

### Rules
- Never commit directly to `main` or `develop`.
- `main` only ever contains the latest stable code.
- Only `release/x.y.z` branches may be merged into `main` (enforced by branch protection + CI in `.github/workflows/gitflow.yml`).
- Features/fixes/chores target `develop` via PR.
- After a release is merged into `main`, merge `main` back into `develop`.

Each feature/fix/chore is developed in its **own git worktree** (a sibling
directory with its own branch), keeping `develop` clean and letting multiple
features be built in parallel without stashing or switching branches.

### Start a feature (from the main checkout, on `develop`)

```bash
git worktree add ../NothingLauncher-<feature-slug> -b <type>/<name> develop
cd ../NothingLauncher-<feature-slug>
# local.properties is gitignored; create it if the SDK isn't found:
#   echo 'sdk.dir='$ANDROID_HOME > local.properties
```

- Branch prefixes: `feature/`, `fix/`, `chore/`, `claude/`
- Worktree path: sibling `../NothingLauncher-<feature-slug>`

### Work & ship (inside the worktree)

```bash
./gradlew assembleDebug     # or ./gradlew test
# make changes, then:
git add <files> && git commit -m "..."
git push -u origin <type>/<name>
gh pr create --base develop --head <type>/<name> --title "..." --body "..."
```

### After the PR is merged (back in the main checkout)

```bash
cd ~/Nothing/NothingLauncher
git fetch --prune origin
git checkout develop && git merge --ff-only origin/develop
git branch -d <type>/<name>                 # local branch
# remove the worktree once you no longer need it:
git worktree remove ../NothingLauncher-<feature-slug>
```

### Cutting a release

```bash
# from an up-to-date develop
git checkout develop && git pull --ff-only
git checkout -b release/x.y.z
# bump version, apply final fixes, then:
git push -u origin release/x.y.z
gh pr create --base main --head release/x.y.z --title "Release x.y.z" --body "..."
```

After the release PR is merged into `main`:

```bash
git checkout develop && git merge main      # back-merge the release into develop
git push origin develop
```

- Keep commits focused and descriptive
- `main` must remain stable and only move forward via `release/*` merges

## Resources

- App icons are in `mipmap-*dpi/` directories (multiple density support)
- Adaptive icons configured for Android 8+ in `mipmap-anydpi-v26/`
- Custom font `Questrial` used throughout the UI
