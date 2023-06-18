package com.example.food.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food.R

class CityAdapter(
    private val citiesList: List<String>,
    private val clickListener: (String) -> Unit
) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {


    class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityItem: TextView = view.findViewById(R.id.city_item)
        val card: View = view.findViewById(R.id.card)

    }

    override fun getItemCount(): Int {
        return citiesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
    val layout = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.city_item, parent, false)
    return CityViewHolder(layout)
}

override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
    val item = citiesList[position]
    holder.cityItem.text = item
    holder.card.setOnClickListener { clickListener(item) }
}
}