package com.example.recepiesapp.models

import com.example.recepiesapp.dependencyInjection.DaggerApiComponent
import com.example.recepiesapp.models.interfaces.CountryApi
import com.example.recepiesapp.view.worldFoods.CountryDataClass
import io.reactivex.Single
import javax.inject.Inject

class CountryService {
    @Inject
    lateinit var apiCountry: CountryApi
    init {
        DaggerApiComponent.create().inject(this)
    }
    fun getAllCountries(country: String): Single<List<CountryDataClass>> {
        return apiCountry.getCountryByName(country)
    }
}