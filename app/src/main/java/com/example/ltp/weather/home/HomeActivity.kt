package com.example.ltp.weather.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ltp.weather.WeatherApplication
import com.example.ltp.weather.databinding.ActivityHomeBinding
import javax.inject.Inject

private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: HomeViewModel

    private lateinit var binding: ActivityHomeBinding

    private val citiesAdapter = CitiesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as WeatherApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.contentHome.apply {
            imageButtonSearch.setOnClickListener { onSearchCity(it) }
            editTextCity.setOnEditorActionListener { view, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    onSearchCity(view)
                    true
                } else {
                    false
                }
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
        val recyclerView = binding.contentHome.recyclerViewCities
        val linearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
            linearLayoutManager.orientation)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = citiesAdapter
        }.addItemDecoration(dividerItemDecoration)

        val recentList = viewModel.getRecentCities()
        if (recentList.isNotEmpty()) {
            citiesAdapter.reload(recentList)
        }
    }

    private fun onSearchCity(view: View) {
        hideSoftKeyboard(view)

        val searchText = binding.contentHome.editTextCity.text.toString().trim()

        if (searchText.isNotBlank()) {
            viewModel.loadCities(searchText)
        } else {
            Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideSoftKeyboard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
