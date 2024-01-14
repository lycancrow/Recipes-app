package com.example.recepiesapp.models.dataClasses

import com.google.gson.annotations.SerializedName

data class CategoryInfo(
    @SerializedName("idCategory")
    val idCategory: String?,
    @SerializedName("strCategory")
    val categoryName: String?,
    @SerializedName("strCategoryThumb")
    val strCategoryImage: String?,
    @SerializedName("strCategoryDescription")
    val strCategoryDescription: String?
)

data class CategoriesResponse(
    @SerializedName("categories")
    val categories: List<CategoryInfo>
)
