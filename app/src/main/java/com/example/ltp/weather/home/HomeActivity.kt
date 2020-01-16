package com.example.ltp.weather.home

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ltp.weather.R
import com.example.ltp.weather.WeatherApplication
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import javax.inject.Inject

private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: HomeViewModel

    private val citiesAdapter = CitiesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as WeatherApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        imageButtonSearch.setOnClickListener {
            val searchText = editTextCity.text.toString()

            if (searchText.trim().isNotBlank()) {
                viewModel.loadCities(searchText)
            } else {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.cityData.observe(this, Observer {
            if (it.isEmpty()) {
                Toast.makeText(this, "No matching cities found", Toast.LENGTH_SHORT).show()
            } else {
                Log.v(TAG, it.toString())
                citiesAdapter.reload(it)
            }
        })

        setupRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(recyclerViewCities.context,
            linearLayoutManager.orientation)
        recyclerViewCities.apply {
            layoutManager = linearLayoutManager
            adapter = citiesAdapter
        }.addItemDecoration(dividerItemDecoration)

        val recentList = viewModel.getRecentCities()
        if (recentList.isNotEmpty()) {
            citiesAdapter.reload(recentList)
        }
    }

}
