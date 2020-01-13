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

private const val CITY_EMPTY = 1
private const val CITY_LIST = 2

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    private var cities = listOf<City>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = if (viewType == CITY_EMPTY) R.layout.empty_city else R.layout.row_city
        return ViewHolder(LayoutInflater
            .from(parent.context).inflate(layout, parent, false), viewType)
    }

    override fun getItemViewType(position: Int) = if (cities.isEmpty()) CITY_EMPTY else CITY_LIST

    // When the item is empty, need to return 1 in order to show the empty layout.
    override fun getItemCount() = if (cities.isEmpty()) 1 else cities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.itemViewType == CITY_LIST) {
            holder.view.textViewCity.text = getCityNameWithCountry(cities[position])
        }
    }

    fun reload(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View, viewType: Int) : RecyclerView.ViewHolder(view) {

        init {
            if (viewType == CITY_LIST) {
                view.setOnClickListener {
                    navigateToCityScreen(
                        view.context,
                        getCityNameWithCountry(cities[adapterPosition])
                    )
                }
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