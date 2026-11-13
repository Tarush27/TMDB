# The Movies Database (TMDB)

A native Android app built with Kotlin and MVVM that lets users browse popular, top-rated and trending movies, plus trending TV shows, with full details available online and offline. Data comes from the TMDB REST API through Retrofit and is cached locally with Room for a reliable offline-first experience.

## Features
- Browse movies by category: popular, top-rated and trending
- Trending TV shows with detail screens
- Offline support: content is cached in a local Room (SQLite) database, so it stays readable without a network connection
- Smooth infinite scrolling with Jetpack Pagination
- Remote feature configuration with Firebase Remote Config
- Fast, memory-efficient image loading with Coil
- Persisted user preferences with Shared Preferences

## Architecture
- **MVVM** with ViewModel and LiveData for lifecycle-aware, reactive UI updates
- **Repository pattern** coordinating the remote API and the local Room database
- **Kotlin Coroutines** for asynchronous, non-blocking network and database work
- **Jetpack Navigation component** for in-app navigation between screens

## Tech Stack
| Area | Tools |
|---|---|
| Language | Kotlin |
| Architecture | MVVM, ViewModel, LiveData |
| Concurrency | Kotlin Coroutines |
| Networking | Retrofit, REST API (TMDB) |
| Local storage | Room (SQLite), Shared Preferences |
| Pagination | Jetpack Pagination |
| UI | Activities, Fragments, RecyclerView, Material Design Components, View Binding |
| Navigation | Jetpack Navigation component |
| Image loading | Coil |
| Remote config | Firebase Remote Config |

Screenshots:

![tmdb_eight](https://github.com/Tarush27/TMDB/assets/51998612/5ffd9e5a-8a84-4904-8d4b-0c88df77a2cb)
![tmdb_two](https://github.com/Tarush27/TMDB/assets/51998612/30070c32-8fe6-48e5-ba20-37ee0f9fc987)
![tmdb_twelve](https://github.com/Tarush27/TMDB/assets/51998612/58c00640-3ab6-4c86-af27-cb1c26098dbb)
![tmdb_three](https://github.com/Tarush27/TMDB/assets/51998612/3b6d3a83-540f-4b85-9c68-068768249721)
![tmdb_thirteen](https://github.com/Tarush27/TMDB/assets/51998612/ca3f6147-bd3f-4608-a125-8d5ba32c9c39)
![tmdb_ten](https://github.com/Tarush27/TMDB/assets/51998612/69d81913-e9b7-4c10-8f50-8a441c8aa475)
![tmdb_six](https://github.com/Tarush27/TMDB/assets/51998612/a5954cbc-d50c-4f90-8365-d9858fb4aae9)
![tmdb_seven](https://github.com/Tarush27/TMDB/assets/51998612/26a67a92-66ed-4b66-aa04-d18a806e1d75)
![tmdb_one](https://github.com/Tarush27/TMDB/assets/51998612/ea50c7bc-9669-4d71-a4cb-332172f43c07)
![tmdb_nine](https://github.com/Tarush27/TMDB/assets/51998612/51e85037-ff69-4c68-a0bf-8c90fb05cf1d)
![tmdb_fourteen](https://github.com/Tarush27/TMDB/assets/51998612/cc1f1182-bbe5-44e4-a782-51a48bbae6b5)
![tmdb_four](https://github.com/Tarush27/TMDB/assets/51998612/fd39770f-5bac-4854-a69f-bd6e654768ec)
![tmdb_five](https://github.com/Tarush27/TMDB/assets/51998612/aaf63d03-8819-4710-a253-78d9be65114c)
![tmdb_fifteen](https://github.com/Tarush27/TMDB/assets/51998612/975a1bcd-28b7-46db-ac39-76609b41cd1a)
![tmdb_eleven](https://github.com/Tarush27/TMDB/assets/51998612/610a5d2e-d04f-4502-bb00-9a37a4a49baf)
