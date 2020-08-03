# weather-android
This is a simple weather app showcasing the implementation of various Android technologies and patterns, including:
- MVVM pattern with Android Architecture Components (`ViewModel` and `LiveData`)
- View Binding
- Dependency injection with Dagger
- Networking with Retrofit and Gson
- Coroutines
- Repository pattern
- Unit testing with JUnit and Mockito
- UI testing with Espresso

## Features
- Search available cities by entering a city name
- Select a city from the list to view its weather condition
- 10 recently viewed cities will be remembered

## Requirements
- Android Studio version 4.0.1 or higher
- Android 5.0 (API level 21) or higher
- Kotlin 1.3 or higher

## Setup
1. Get your API key from [World Weather Online's developer portal](https://www.worldweatheronline.com/developer/).
2. Replace the API_KEY constant in Constants.kt with your API key.