package com.example.recepiesapp.dependencyInjection

import com.example.recepiesapp.models.CountryService
import com.example.recepiesapp.models.RecipeService
import com.example.recepiesapp.view.especificListSelected.SelectedListViewModel
import com.example.recepiesapp.view.home.HomeViewModel
import com.example.recepiesapp.view.recipeDetails.RecipeDetailsViewModel
import com.example.recepiesapp.view.worldFoods.CountryViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: RecipeService)
    fun inject(service: CountryService)
    fun inject(viewModel: HomeViewModel)
    fun inject(viewModel: CountryViewModel)
    fun inject(viewModel: SelectedListViewModel)
    fun inject(viewModel: RecipeDetailsViewModel)
}