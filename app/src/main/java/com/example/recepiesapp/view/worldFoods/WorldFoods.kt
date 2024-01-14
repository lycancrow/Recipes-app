package com.example.recepiesapp.view.worldFoods

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recepiesapp.R
import com.example.recepiesapp.databinding.FragmentWorldFoodsBinding
import com.example.recepiesapp.models.dataClasses.CategoryInfo
import com.example.recepiesapp.util.ItemOffsetDecoration
import com.example.recepiesapp.view.especificListSelected.SelectedListOption
import com.example.recepiesapp.view.home.HomeViewModel
import com.example.recepiesapp.view.home.OnFeaturedCategoryClickListener


class WorldFoods : Fragment(R.layout.fragment_world_foods){

    private var _binding: FragmentWorldFoodsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelCountry: CountryViewModel
    private var countryList: ArrayList<CountryDataClass> = arrayListOf()

    private val countryAdapter = CountryAdapter(countryList,
        object : OnFeaturedCountryClickListener{
            override fun onCountryClick(countryClick: CountryDataClass) {
                viewModelCountry.onCountryCategoryClicked(countryClick)
            }
        })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorldFoodsBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelCountry = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModelCountry.get33Countries()

        binding.listCountry.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(ItemOffsetDecoration(16))
            adapter = countryAdapter
        }


        binding.searchBarCountry.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    viewModelCountry.filterCountries(newText)
                } else {
                    // Restaurar la lista original si el texto está vacío
                    viewModelCountry.get33Countries()
                }
                return true
            }
        })


        observeViewModel()

        activity?.let {
            viewModelCountry.selectedCountry.observe(it){it ->
                val intent = Intent(activity, SelectedListOption::class.java)
                if (it != null) {
                    intent.putExtra("title", it.name)
                    intent.putExtra("demonym", it.demonym)
                    intent.putExtra("selector", "2")
                }
                startActivity(intent)
            }
        }
    }


    private fun observeViewModel() {
        viewModelCountry.countries.observe(viewLifecycleOwner) { categoriesCheck ->
            if (categoriesCheck.isEmpty()) {
                Log.i("salidaRespuesta", "hay un error de red")
            } else {
                categoriesCheck?.let {
                    countryAdapter.updateCountry(it)
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}