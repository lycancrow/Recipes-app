package com.example.recepiesapp.view.recipeDetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recepiesapp.dependencyInjection.DaggerApiComponent
import com.example.recepiesapp.models.RecipeService
import com.example.recepiesapp.models.dataClasses.Meals
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecipeDetailsViewModel : ViewModel() {

    @Inject
    lateinit var recipesService: RecipeService
    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()
    val _recipeDetails = MutableLiveData<Meals>()

    fun getRecipeDetails(mealName: String) {
        disposable.add(
            recipesService.getMeal(mealName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Meals>>(){
                    override fun onSuccess(value: List<Meals>?) {
                        // Assuming value is not null and contains only one item
                        value?.get(0)?.let {
                            _recipeDetails.value = it
                            Log.i("salidaRespuestaDetails", _recipeDetails.value.toString())
                            val gson = Gson()
                            val json = gson.toJson(_recipeDetails.value)
                            Log.i("salidaRespuestaDetails", json)
                            Log.i("salidaRespuestaDetails", "si entro a la api")
                        }
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("salidaRespuestaDetails", e.toString())
                    }
                })
        )
    }




}