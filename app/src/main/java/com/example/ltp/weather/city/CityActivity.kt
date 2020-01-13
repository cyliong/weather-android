package com.example.ltp.weather.city

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ltp.weather.R
import kotlinx.android.synthetic.main.activity_city.*

class CityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        const val EXTRA_CITY = "city"
    }

}
