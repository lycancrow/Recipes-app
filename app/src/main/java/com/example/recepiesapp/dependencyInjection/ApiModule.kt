package com.example.recepiesapp.dependencyInjection

import com.example.recepiesapp.models.CountryService
import com.example.recepiesapp.models.interfaces.CountryApi
import com.example.recepiesapp.models.interfaces.RecipeApi
import com.example.recepiesapp.models.RecipeService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
@Module
class ApiModule {

    private val BASE_URL = "https://www.themealdb.com"
    private val BASE_COUNTRY_URL = "https://restcountries.com/"




    @Provides
    fun providesRecipeCategoryApi(): RecipeApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RecipeApi::class.java)
    }


    @Provides
    fun providesCountryApi(): CountryApi {
        return Retrofit.Builder()
            .baseUrl(BASE_COUNTRY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountryApi::class.java)
    }


    @Provides
    fun provideRecipeService(): RecipeService {
        return RecipeService()
    }

    @Provides
    fun provideCountryService(): CountryService {
        return CountryService()
    }
}



