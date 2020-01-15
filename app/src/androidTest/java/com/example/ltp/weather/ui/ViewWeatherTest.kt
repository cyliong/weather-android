package com.example.ltp.weather.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ltp.weather.R
import com.example.ltp.weather.WeatherApplication
import com.example.ltp.weather.home.HomeActivity
import com.example.ltp.weather.storage.HistoryManager
import com.example.ltp.weather.storage.SharedPreferencesStorage
import org.junit.Test
import org.junit.runner.RunWith

private const val HOME_SCREEN_TITLE = "Weather"
private const val CITY_TEXT = "London, United Kingdom"

@RunWith(AndroidJUnit4::class)
class ViewWeatherTest {

    @Test
    fun select_city_then_view_weather() {

        // Prepare historical data in recently viewed list
        val context = getApplicationContext<WeatherApplication>()
        val historyManager = HistoryManager(SharedPreferencesStorage(context))
        historyManager.addCityNameWithCountry(CITY_TEXT)

        // Launch Home screen
        ActivityScenario.launch(HomeActivity::class.java)

        // Given I am on the Home screen
        onView(withId(R.id.toolbar))
            .check(matches(hasDescendant(withText(HOME_SCREEN_TITLE))))

        // When I press the first city displayed on the recently viewed list
        onView(withId(R.id.recyclerViewCities))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // Then I should see the current weather information
        onView(withId(R.id.imageViewWeather))
            .check(matches(isDisplayed()))
        onView(withId(R.id.textViewTemperature))
            .check(matches(isDisplayed()))
        onView(withId(R.id.textViewDescription))
            .check(matches(isDisplayed()))
        onView(withId(R.id.textViewHumidity))
            .check(matches(isDisplayed()))
    }

}