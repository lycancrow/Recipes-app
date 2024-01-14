package com.example.recepiesapp.view.worldFoods

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recepiesapp.dependencyInjection.DaggerApiComponent
//import com.example.recepiesapp.dependencyInjection.DaggerApiComponent
import com.example.recepiesapp.models.CountryService
import com.example.recepiesapp.models.RecipeService
import com.example.recepiesapp.models.dataClasses.CategoryInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryViewModel : ViewModel() {

    @Inject
    lateinit var countryService: CountryService
    init{
        DaggerApiComponent.create().inject(this)
    }

    private val _countries = MutableLiveData<List<CountryDataClass>>()
    val countries: LiveData<List<CountryDataClass>> get() = _countries
    private val disposable = CompositeDisposable()
    private val _selectedCountry = MutableLiveData<CountryDataClass>()
    val selectedCountry: LiveData<CountryDataClass> get() = _selectedCountry



    fun get33Countries() {
        // Lista de nombres de los 33 países
        val countriesList = listOf(
            "United Kingdom", "United States", "France", "Canada", "Jamaica", "China",
            "Netherlands", "Egypt", "Greece", "India", "Ireland", "Italy", "Japan",
            "Saint Kitts and Nevis", "Malaysia", "Mexico", "Morocco", "Croatia", "Norway",
            "Portugal", "Russia", "Argentina", "Spain", "Slovakia", "Thailand", "Saudi Arabia",
            "Vietnam", "Turkey", "Syria", "Algeria", "Tunisia", "Poland", "Philippines"
        )

        disposable.add(
            Observable.fromIterable(countriesList)
                .flatMapSingle { countryName -> countryService.getAllCountries(countryName).subscribeOn(Schedulers.io()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->

                        _countries.value = (_countries.value ?: emptyList()) + result

                        Log.i("salidaPaises", _countries.value.toString() )
                    },
                    { error -> /* Manejar el error, si es necesario */ }
                )
        )

    }


    fun filterCountries(query: String) {
        val filteredCategories = _countries.value?.filter { category ->
            category.name?.contains(query, ignoreCase = true) == true
        }
        filteredCategories?.let {
            _countries.value = it
        }
    }


    fun onCountryCategoryClicked(country: CountryDataClass) {
        _selectedCountry.value = country
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        _countries.value = emptyList()
    }

}
