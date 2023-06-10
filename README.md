# HanaTest
HanaTest app is a Testing app for Network Call, Local Database, Exporting Json file with light weight UIs. HanaTest lite is pure Kotlin based project.
HanaTest app uses Android Official recommended architecture MVVM with the modifying of Clean Architecture. HanaTest app has scalability with Test-driven development (TDD).

## Tech

- [Kotlin - 1.8.20](https://kotlinlang.org/docs/releases.html#release-details)
- [Jetpack Libraries](https://developer.android.com/jetpack)
- [MVVM with Clean Architecture](https://developer.android.com/topic/architecture)
- [Retrofit](https://github.com/square/retrofit)
- [GSON:Json serialization](https://github.com/google/gson)
- [OkHttpProfiler :Network debugging](https://github.com/itkacher/OkHttpProfiler)
- [Room Database :SQLite](https://developer.android.com/training/data-storage/room)
- [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html)
- [Coroutines :concurrency](https://developer.android.com/kotlin/coroutines)
- [Flow :Stream programming](https://developer.android.com/kotlin/flow)
- [Dagger Hilt :Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [Material Components](https://developer.android.com/design/ui/mobile/guides/components/material-overview)

## How to run HanaTest app in Android Studio?
- Clone project with [this link](https://github.com/htetarkarzaw/HanaTest.git)
- Android studio will download necessary plugins and sync with gradle files.
- Check out latest commit point at `main` branch and sync project.
- Make sure that you use `JavaVersion_17` for your gradle JDK.
- After that you can run on your device or emulator.

## How to run HanaTest app test?
### To run Dao Test
- Open `src/androidTest/java/com/htetarkarzaw/hanatest/data/UserDaoTest.kt` and Run `UserDaoTest`

### To run unit Test
- Open `src/test[unitTest]/java/com/htetarkarzaw/hanatest/repository/HanaRepositoryTest.kt` and Run `HanaRepositoryTest`

