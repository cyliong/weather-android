package com.example.ltp.weather.city

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ltp.weather.WeatherApplication
import com.example.ltp.weather.databinding.ActivityCityBinding

class CityActivity : AppCompatActivity() {

    private val viewModel: CityViewModel by viewModels()

    private lateinit var binding: ActivityCityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as WeatherApplication).appComponent.inject(viewModel)

        binding = ActivityCityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val cityName = intent.getStringExtra(EXTRA_CITY) ?: ""
        supportActionBar?.title = cityName

        viewModel.getWeather(cityName).observe(this, { weather ->
            binding.contentCity.apply {
                textViewTemperature.text = weather.temperature.toString() + "°c"
                textViewDescription.text = weather.description
                textViewHumidity.text = weather.humidity.toInt().toString() + "%"
            }
            Glide.with(this).load(weather.iconUrl).into(binding.contentCity.imageViewWeather)
        })
    }

    companion object {
        const val EXTRA_CITY = "city"
    }

}
