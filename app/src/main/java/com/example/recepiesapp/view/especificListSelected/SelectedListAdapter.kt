package com.example.recepiesapp.view.especificListSelected


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recepiesapp.R
import com.example.recepiesapp.models.dataClasses.DataClassSelectedCategory
import com.example.recepiesapp.util.getProgressDrawable
import com.example.recepiesapp.util.loadImage
import kotlinx.android.synthetic.main.home_view_item.view.*


class SelectedListAdapter(
    var featuringRecipeCategories: ArrayList<DataClassSelectedCategory>,
    private var featuredRecipeCategoryOnClickListener: OnFeaturedRecipeCategoryClickListener
) : RecyclerView.Adapter<SelectedListAdapter.FeaturedRecipeByCategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedRecipeByCategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_view_item, parent, false)
        return FeaturedRecipeByCategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeaturedRecipeByCategoriesViewHolder, position: Int) {
        val dataRecipeByCategories = featuringRecipeCategories[position]
        holder.bind(dataRecipeByCategories, featuredRecipeCategoryOnClickListener)
    }

    override fun getItemCount() = featuringRecipeCategories.size

    fun updateRecipesByCategory(newRecipeByCategory: List<DataClassSelectedCategory>) {
        featuringRecipeCategories.clear()
        featuringRecipeCategories.addAll(newRecipeByCategory)
        notifyDataSetChanged()
    }

    inner class FeaturedRecipeByCategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val featuredOfferImage = view.imageCategory
        private val featuredOfferName = view.categoryName
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(
            dataClassSelectedCategory: DataClassSelectedCategory,
            onFeaturedRecipeCategoryClickListener: OnFeaturedRecipeCategoryClickListener
        ) {
            featuredOfferImage.loadImage(dataClassSelectedCategory.mealImage, progressDrawable)
            itemView.setOnClickListener {
                onFeaturedRecipeCategoryClickListener.onRecipeCategoryClick(dataClassSelectedCategory)
            }
            featuredOfferName.text = dataClassSelectedCategory.mealName
        }
    }
}

interface OnFeaturedRecipeCategoryClickListener {
    fun onRecipeCategoryClick(categoryRecipeClick: DataClassSelectedCategory)
}



/*
class SelectedListAdapter(
    var featuringRecipeCategories: ArrayList<DataClassSelectedCategory>,
    private var featuredRecipeCategoryOnClickListener: OnFeaturedRecipeCategoryClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private val VIEW_TYPE_FEATURED_RECIPE_CATEGORIES = 1

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_FEATURED_RECIPE_CATEGORIES
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_FEATURED_RECIPE_CATEGORIES -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_view_item, parent, false)
                SelectedListAdapter.FeaturedRecipeByCategoriesViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_FEATURED_RECIPE_CATEGORIES -> {
                // Configura la vista normal
                val featuredCategoriesViewHolder = holder as FeaturedRecipeByCategoriesViewHolder
                val dataRecipeByCategories =
                    featuringRecipeCategories[position] // Resta 1 para ignorar la primera fila
                featuredCategoriesViewHolder.bind(dataRecipeByCategories, featuredRecipeCategoryOnClickListener)
            }

        }
    }
    //-----
    override fun getItemCount() = featuringRecipeCategories.size

    fun updateRecipesByCategory(newRecipeByCategory: List<DataClassSelectedCategory>) {
        featuringRecipeCategories.clear()
        featuringRecipeCategories.addAll(newRecipeByCategory)
        notifyDataSetChanged()
    }


    class FeaturedRecipeByCategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val featuredOfferImage = view.imageCategory
        private val featuredOfferName = view.categoryName
        private val progressDrawable = getProgressDrawable(view.context)
        fun bind(
            dataClassSelectedCategory: DataClassSelectedCategory,
            onFeaturedRecipeCategoryClickListener: OnFeaturedRecipeCategoryClickListener
        ) {
            featuredOfferImage.loadImage(dataClassSelectedCategory.mealImage, progressDrawable)
            itemView.setOnClickListener {
                onFeaturedRecipeCategoryClickListener.onRecipeCategoryClick(dataClassSelectedCategory)
            }
            featuredOfferName.text = dataClassSelectedCategory.mealName
        }
    }


}
interface OnFeaturedRecipeCategoryClickListener {
    fun onRecipeCategoryClick(categoryRecipeClick: DataClassSelectedCategory)
}
*/