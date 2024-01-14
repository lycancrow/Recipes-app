package com.example.recepiesapp.models.interfaces

import com.example.recepiesapp.view.worldFoods.CountryDataClass
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApi {
    @GET("v2/name/{countryName}")
    fun getCountryByName(@Path("countryName") countryName: String): Single<List<CountryDataClass>>
}