package com.example.ltp.weather.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.ltp.weather.city.CityActivity
import com.example.ltp.weather.databinding.EmptyCityBinding
import com.example.ltp.weather.databinding.RowCityBinding
import com.example.ltp.weather.model.City

private const val CITY_EMPTY = 1
private const val CITY_LIST = 2

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    private var cities = listOf<City>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = if (viewType == CITY_EMPTY) {
            EmptyCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        } else {
            RowCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int) = if (cities.isEmpty()) CITY_EMPTY else CITY_LIST

    // When the item is empty, need to return 1 in order to show the empty layout.
    override fun getItemCount() = if (cities.isEmpty()) 1 else cities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.itemViewType == CITY_LIST) {
            holder.bind(cities[position])
        }
    }

    fun reload(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var city: City? = null

        init {
            if (binding is RowCityBinding) {
                binding.root.setOnClickListener {
                    city?.let {
                        navigateToCityScreen(binding.root.context, getCityNameWithCountry(it))
                    }
                }
            }
        }

        fun bind(city: City) {
            if (binding is RowCityBinding) {
                this.city = city
                binding.textViewCity.text = getCityNameWithCountry(city)
            }
        }

    }

    private fun getCityNameWithCountry(city: City) = "${city.name}, ${city.country}"

    private fun navigateToCityScreen(context: Context, cityName: String) {
        val intent = Intent(context, CityActivity::class.java).apply {
            putExtra(CityActivity.EXTRA_CITY, cityName)
        }
        context.startActivity(intent)
    }
}