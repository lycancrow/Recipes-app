package com.example.recepiesapp.view.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recepiesapp.R
import com.example.recepiesapp.models.dataClasses.CategoryInfo
import com.example.recepiesapp.util.getProgressDrawable
import com.example.recepiesapp.util.loadImage
import kotlinx.android.synthetic.main.home_view_item.view.*


class HomeListAdapter(
    var featuringCategories: ArrayList<CategoryInfo>,
    private var featuredCategoriesOnClickListener: OnFeaturedCategoryClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_FEATURED_CATEGORIES = 1
    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_FEATURED_CATEGORIES
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_FEATURED_CATEGORIES -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_view_item, parent, false)
                FeaturedCategoriesViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_FEATURED_CATEGORIES -> {
                // Configura la vista normal
                val featuredCategoriesViewHolder = holder as FeaturedCategoriesViewHolder
                val dataCategories =
                    featuringCategories[position] // Resta 1 para ignorar la primera fila
                featuredCategoriesViewHolder.bind(dataCategories, featuredCategoriesOnClickListener)
            }

        }
    }

    override fun getItemCount() = featuringCategories.size // Agrega 1 para el layout buttons_home

    fun updateRecipeCategory(newCategory: List<CategoryInfo>) {
        featuringCategories.clear()
        featuringCategories.addAll(newCategory)
        notifyDataSetChanged()
    }



    class FeaturedCategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val featuredOfferImage = view.imageCategory
        private val featuredOfferName = view.categoryName
        private val progressDrawable = getProgressDrawable(view.context)
        fun bind(
            dataCategory: CategoryInfo,
            onFeaturedCategoryClickListener: OnFeaturedCategoryClickListener
        ) {
            featuredOfferImage.loadImage(dataCategory.strCategoryImage, progressDrawable)
            itemView.setOnClickListener {
                Log.d("HomeListAdapter", "onCategoryClick called for ${dataCategory.categoryName}")
                onFeaturedCategoryClickListener.onCategoryClick(dataCategory)
            }
            featuredOfferName.text = dataCategory.categoryName
        }
    }

}

interface OnFeaturedCategoryClickListener {
    fun onCategoryClick(categoryClick: CategoryInfo)
}
