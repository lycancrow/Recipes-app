package com.example.recepiesapp.view.especificListSelected

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recepiesapp.dependencyInjection.DaggerApiComponent
import com.example.recepiesapp.models.RecipeService
import com.example.recepiesapp.models.dataClasses.DataClassSelectedCategory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SelectedListViewModel : ViewModel() {
    @Inject
    lateinit var recipeService: RecipeService
    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()
    val recipesCategoriesSelected = MutableLiveData<List<DataClassSelectedCategory>>()
    private val _navigateToDetails = MutableLiveData<DataClassSelectedCategory?>()
    val navigateToDetails: LiveData<DataClassSelectedCategory?> get() = _navigateToDetails


    fun getMealsByCountry(demonym: String) {
        fetchMealsByCountry(demonym)
    }



    fun getMealsRecipesCategory(categoryMeals: String) {
        fetchMealsRecipesCategory(categoryMeals)
    }

    private fun fetchMealsRecipesCategory(categoryMeals: String) {
        disposable.add(
            recipeService.getRecipesByCategory(categoryMeals)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<DataClassSelectedCategory>>() {
                    override fun onSuccess(value: List<DataClassSelectedCategory>) {
                        recipesCategoriesSelected.value = value
                        Log.i("salidaRespuesta", recipesCategoriesSelected.value.toString())
                        Log.i("salidaViewModel", "si entro bien")
                    }

                    override fun onError(e: Throwable?) {
                        Log.i("salidaViewModel", "algo salio mal")
                        Log.e("error", e.toString())
                    }
                })
        )
    }



    private fun fetchMealsByCountry(denomym: String) {
        disposable.add(
            recipeService.getMealsByCountry(denomym)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<DataClassSelectedCategory>>() {
                    override fun onSuccess(value: List<DataClassSelectedCategory>) {
                        recipesCategoriesSelected.value = value
                        Log.i("salidaRespuesta", recipesCategoriesSelected.value.toString())
                        Log.i("salidaViewModel", "si entro bien")
                    }

                    override fun onError(e: Throwable?) {
                        Log.i("salidaViewModel", "algo salio mal")
                        Log.e("error", e.toString())
                    }
                })
        )
    }

    fun onMealCategoryClicked(category: DataClassSelectedCategory) {
        _navigateToDetails.value = category
    }

    fun filterRecipesByCategory(query: String){
        val filteredCategoriesBySelection = recipesCategoriesSelected.value?.filter { category ->
            category.mealName?.contains(query, ignoreCase = true) == true
        }
        filteredCategoriesBySelection?.let {
            recipesCategoriesSelected.value = it
        }
    }



    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        recipesCategoriesSelected.value = emptyList()
    }
}

