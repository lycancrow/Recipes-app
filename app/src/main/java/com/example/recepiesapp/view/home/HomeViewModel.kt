package com.example.recepiesapp.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recepiesapp.dependencyInjection.DaggerApiComponent
import com.example.recepiesapp.models.RecipeService
import com.example.recepiesapp.models.dataClasses.CategoryInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel: ViewModel() {

    @Inject
    lateinit var recipesService: RecipeService
    init{
        DaggerApiComponent.create().inject(this)
    }


    private val disposable = CompositeDisposable()
    val recipesCategories = MutableLiveData<List<CategoryInfo>>()
    private val _navigateToDetails = MutableLiveData<CategoryInfo?>()
    val navigateToDetails : LiveData<CategoryInfo?> get() = _navigateToDetails



    fun getOriginalRecipesCategory(){
        fetchRecipesCategory()
    }

    private fun fetchRecipesCategory(){
        disposable.add(
            recipesService.getAllRecipesCategories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<CategoryInfo>>(){
                    override fun onSuccess(value: List<CategoryInfo>) {
                        recipesCategories.value = value
                        Log.i("salidaRespuesta",recipesCategories.value.toString() )
                        Log.i("salidaViewModel","si entro bien")

                    }
                    override fun onError(e: Throwable?) {
                        Log.i("salidaViewModel","algo salio mal")
                        Log.e("error",e.toString())
                    }
                })
        )
    }



    fun onFoodCategoryClicked(category: CategoryInfo) {
        _navigateToDetails.value = category
    }


    fun filterRecipes(query: String) {
        val filteredCategories = recipesCategories.value?.filter { category ->
            category.categoryName?.contains(query, ignoreCase = true) == true
        }
        filteredCategories?.let {
            recipesCategories.value = it
        }
    }



    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        recipesCategories.value = emptyList() // Limpiar la lista al destruir el ViewModel
    }


}

