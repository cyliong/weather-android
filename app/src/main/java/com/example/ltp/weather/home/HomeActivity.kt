package com.example.ltp.weather.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ltp.weather.R
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

}
