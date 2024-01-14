package com.example.recepiesapp.models.interfaces

import com.example.recepiesapp.models.dataClasses.CategoriesResponse
import com.example.recepiesapp.models.dataClasses.DataClassMeal
import com.example.recepiesapp.models.dataClasses.DataClassSelectedCategoryList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {
    @GET("/api/json/v1/1/categories.php")
    fun getAllRecipesCategories(): Single<CategoriesResponse>

    @GET("/api/json/v1/1/filter.php")
    fun getAllTipeMeals(@Query("c") categoryMeals: String): Single<DataClassSelectedCategoryList>

    @GET("/api/json/v1/1/filter.php")
    fun getMealsByCountry(@Query("a") countryDemonym: String): Single<DataClassSelectedCategoryList>

    @GET("/api/json/v1/1/lookup.php")
    fun getRecipe(@Query("i") recipeName: String): Single<DataClassMeal>
}


