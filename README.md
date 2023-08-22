# Exchage Rates App
An application to track & save the currency exchange rates

### About the app
Application consists of 2 base screens: Latest and Favourites and 2 secondary: Currency selector and Sort. Any trading pair from Latest screen can be added to favourites or removed from those for convenience, saved values are updated as new data is received. Both Latest and Favourites share settings of selected base currency and sort mode.

Exchange rates data is updated in background with polling interval of **1 minute** or can be force updated with **swipe-to-refresh** on Latest screen.

Application supports screen rotataion with persistence of state and have an adapive UI to be useful on smaller and larger screen sizes.

API that is used for fetching data: https://github.com/exchangeratesapi/exchangeratesapi

### Restrictions
Please note that the key that is used in the application to access the API has no paid subscription plan and therefore API has some restrictions (as for 22 aug 2023):
* 1000 request per month
* The only base currency can be used is EUR, others are restricted and API will return an error if attempted to request with another base
  ``` {"success":false,"error":{"code":105,"type":"base_currency_access_restricted"}} ```

If you want to work with other base currencies feel free to paste your paid API access key into **BuildConfig**
```buildConfigField "String", "API_ACCESS_KEY", "\"YOUR_KEY\""```

### Stack used
Application is build with Clean Architechure principles in mind.

Detekt is used for static analysis, gradle task is launched with ```./gradlew detekt```
- Kotlin
- MVVM
- Coroutines
- Kotlin StateFlow,
- Dagger2
- Room
- Retrofit
- Viewbinding
- Detekt
