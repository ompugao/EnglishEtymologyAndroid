# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Android app that provides a mobile interface for the [ハイパー英語語源辞書](http://hidic.u-aizu.ac.jp/) (Hyper English Etymology Dictionary). The app allows users to search for English words and view their etymology information from the online dictionary.

## Architecture

- **Single Activity App**: Uses `ScrollingActivity` as the main and only activity
- **WebView-based**: Core functionality is a WebView that loads the dictionary website
- **Text Processing Integration**: Supports Android's text processing intent to receive text from other apps
- **Autocomplete Search**: Includes a large dataset of English words for autocomplete suggestions in `worddb.xml`

### Key Components

- `ScrollingActivity.kt`: Main activity containing WebView and search functionality
- `worddb.xml`: Contains array of English words for autocomplete (large file ~800KB)
- WebView with caching enabled for performance
- Intent filter for `PROCESS_TEXT` action to receive text from other apps

## Development Commands

### Build
```bash
./gradlew assembleDebug
```

### Run Tests
```bash
./gradlew test
```

### Clean Build
```bash
./gradlew clean
```

### Install Debug APK
```bash
./gradlew installDebug
```

### Generate Release APK
```bash
./gradlew assembleRelease
```

## Technical Details

- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35 (Android 15)
- **Language**: Kotlin
- **Build System**: Gradle 8.5 with Android Gradle Plugin 8.2.2
- **Kotlin Version**: 1.9.22
- **View Binding**: Enabled (replaces deprecated kotlin-android-extensions)

### Dependencies
- AndroidX AppCompat, Core KTX, Material Design
- ConstraintLayout for UI
- Standard testing dependencies (JUnit, Espresso)

### Permissions
- `INTERNET`: Required for loading dictionary website
- `usesCleartextTraffic`: Enabled for HTTP requests to the dictionary site

## CI/CD

GitHub Actions workflow (`android.yml`) automatically:
1. Runs unit tests on push to master
2. Builds debug APK
3. Creates releases for version tags (v*)