package com.example.recepiesapp.view.worldFoods

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recepiesapp.R
import com.example.recepiesapp.models.dataClasses.CategoryInfo
import com.example.recepiesapp.util.getProgressDrawable
import com.example.recepiesapp.util.loadImage
import kotlinx.android.synthetic.main.country_item.view.*


class CountryAdapter(
    var featuringCountries: ArrayList<CountryDataClass>,
    private var featuredCountriesOnClickListener: OnFeaturedCountryClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_FEATURED_COUNTRIES = 1

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_FEATURED_COUNTRIES
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            VIEW_TYPE_FEATURED_COUNTRIES -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.country_item, parent, false)
                FeaturedCountriesViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {

            VIEW_TYPE_FEATURED_COUNTRIES -> {
                // Configura la vista normal
                val featuredCountryViewHolder = holder as FeaturedCountriesViewHolder
                val dataCountries =
                    featuringCountries[position] // Resta 1 para ignorar la primera fila
                featuredCountryViewHolder.bind(dataCountries, featuredCountriesOnClickListener)
            }

        }
    }

    override fun getItemCount() = featuringCountries.size

    fun updateCountry(newCountry: List<CountryDataClass>) {
        featuringCountries.clear()
        featuringCountries.addAll(newCountry)
        notifyDataSetChanged()
    }

    class FeaturedCountriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val featuredCountryImage = view.imageCountry
        private val featuredCountryName = view.countryName

        private val progressDrawable = getProgressDrawable(view.context)
        fun bind(
            dataCountries: CountryDataClass,
            onFeaturedOfferClickListener: OnFeaturedCountryClickListener
        ) {
            featuredCountryImage.loadImage(dataCountries.flags.png, progressDrawable)
            itemView.setOnClickListener {
                onFeaturedOfferClickListener.onCountryClick(dataCountries)
            }
            featuredCountryName.text = dataCountries.name
        }
    }
}

interface OnFeaturedCountryClickListener {
    fun onCountryClick(countryClick: CountryDataClass)
}


