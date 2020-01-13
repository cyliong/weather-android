package com.example.ltp.weather.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ltp.weather.R
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

            }
        }

    }

    private fun getCityNameWithCountry(city: City) = "${city.name}, ${city.country}"

}