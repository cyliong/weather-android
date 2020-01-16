package com.example.ltp.weather.city

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.ltp.weather.R
import com.example.ltp.weather.WeatherApplication
import kotlinx.android.synthetic.main.activity_city.*
import kotlinx.android.synthetic.main.content_city.*
import javax.inject.Inject

class CityActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as WeatherApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val cityName = intent.getStringExtra(EXTRA_CITY)
        supportActionBar?.title = cityName

        viewModel.loadWeather(cityName)

        viewModel.weatherData.observe(this, Observer {
            val imageUrl = it.iconUrl
            viewModel.loadWeatherImage(imageUrl)

            textViewTemperature.text = it.temperature.toString() + "°c"
            textViewDescription.text = it.description
            textViewHumidity.text = it.humidity.toInt().toString() + "%"
        })
        viewModel.weatherIconData.observe(this, Observer {
            imageViewWeather.setImageBitmap(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    companion object {
        const val EXTRA_CITY = "city"
    }

}
