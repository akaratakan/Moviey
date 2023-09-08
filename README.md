## Architecture
**Moviey** is based on the Clean architecture and the MVVM pattern, which follows the [Google's official architecture guidance](https://developer.android.com/topic/architecture).

The overall architecture of **Moviey** is composed of three layers; the UI layer, domain layer and the data layer.

<p align="center">
<img src="https://developer.android.com/static/topic/libraries/architecture/images/mad-arch-overview.png"/>
</p>

## Modularization

**Moviey** adopted modularization strategies below:

- **Reusability**: Modulizing reusable codes properly enable opportunities for code sharing and limits code accessibility in other modules at the same time.
- **Parallel Building**: Each module can be run in parallel and it reduces the build time.
- **Strict visibility control**: Modules restrict to expose dedicated components and access to other layers, so it prevents they're being used outside the module
- **Decentralized focusing**: Each developer team can assign their dedicated module and they can focus on their own modules.

For more information, check out the [Guide to Android app modularization](https://developer.android.com/topic/modularization).


### UI Layer

The UI layer consists of UI elements to configure screens that could interact with users and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that holds app states and restores data when configuration changes.
UI elements observe the data flow via [Compose](https://developer.android.com/jetpack/compose), which is the most essential part of the Modern Android UI development.

### Domain Layer

The domain layer is responsible for encapsulating complex business logic, or simple business logic that is reused by multiple ViewModels.

### Data Layer

The data Layer consists of repositories, which include business logic, such as querying data from the local database and requesting remote data from the network. It is implemented as an offline-first source of business logic and follows the [single source of truth](https://en.wikipedia.org/wiki/Single_source_of_truth) principle.<br>

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/): for asynchronous.
- [Hilt](https://dagger.dev/hilt/): for dependency injection.
- [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle): Observe Android lifecycles and handle UI states upon the lifecycle changes.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
- [Compose](https://developer.android.com/jetpack/compose): Jetpack Compose is the modern toolkit for building native Android UI. 
- [Room](https://developer.android.com/jetpack/androidx/releases/room): Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Construct the REST APIs and paging network data.
- [Moshi](https://github.com/square/moshi/): A modern JSON library for Kotlin and Java.
- [Material-Components-3](https://github.com/material-components/material-components-android): Build Jetpack Compose UIs with Material Design 3 Components, the next evolution of Material Design. 
- [Coil](https://coil-kt.github.io/coil/compose/): An image loading library for Android backed by Kotlin Coroutines.
- [Timber](https://github.com/JakeWharton/timber): A logger with a small, extensible API.


