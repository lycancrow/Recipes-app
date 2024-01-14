package com.example.recepiesapp.models.dataClasses

import com.google.gson.annotations.SerializedName

data class DataClassSelectedCategory (
    @SerializedName("strMeal")
    val mealName: String?,
    @SerializedName("strMealThumb")
    val mealImage: String?,
    @SerializedName("idMeal")
    val mealId: String?
        )


data class DataClassSelectedCategoryList (
    @SerializedName("meals")
    val selectedCategoryList: List<DataClassSelectedCategory>
        )

