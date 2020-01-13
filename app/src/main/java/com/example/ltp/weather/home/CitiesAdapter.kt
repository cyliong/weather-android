package com.example.ltp.weather.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ltp.weather.R
import com.example.ltp.weather.city.CityActivity
import com.example.ltp.weather.model.City
import kotlinx.android.synthetic.main.row_city.view.*

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    private var cities = listOf<City>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_city, parent, false))
    }

    override fun getItemCount() = cities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.textViewCity.text = getCityNameWithCountry(cities[position])
    }

    fun reload(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                navigateToCityScreen(view.context, getCityNameWithCountry(cities[adapterPosition]))
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