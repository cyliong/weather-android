package com.example.ltp.weather.city

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ltp.weather.R
import com.example.ltp.weather.databinding.ActivityCityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityActivity : AppCompatActivity() {

    private val viewModel: CityViewModel by viewModels()

    private lateinit var binding: ActivityCityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val cityName = intent.getStringExtra(EXTRA_CITY) ?: ""
        supportActionBar?.title = cityName

        viewModel.getWeather(cityName).observe(this, { weather ->
            binding.contentCity.apply {
                textViewTemperature.text = getString(R.string.temperature, weather.temperature)
                textViewDescription.text = weather.description
                textViewHumidity.text = getString(R.string.humidity, weather.humidity)
            }
            Glide.with(this).load(weather.iconUrl).into(binding.contentCity.imageViewWeather)
        })
    }

    companion object {
        const val EXTRA_CITY = "city"
    }

}
