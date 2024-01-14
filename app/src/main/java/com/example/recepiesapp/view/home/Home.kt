package com.example.recepiesapp.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recepiesapp.R
import com.example.recepiesapp.databinding.FragmentHomeBinding
import com.example.recepiesapp.models.dataClasses.CategoryInfo
import com.example.recepiesapp.util.ItemOffsetDecoration
import com.example.recepiesapp.view.especificListSelected.SelectedListOption

class Home : Fragment(R.layout.fragment_home), OnFeaturedCategoryClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private var categoryList: ArrayList<CategoryInfo> = arrayListOf()
    private val recipesCategoriesAdapter =
        HomeListAdapter(categoryList,
            object : OnFeaturedCategoryClickListener {
                override fun onCategoryClick(categoryClick: CategoryInfo) {
                    viewModel.onFoodCategoryClicked(categoryClick)
                }
            })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]


        viewModel.getOriginalRecipesCategory()


        binding.listOfCategories.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(ItemOffsetDecoration(16))
            adapter = recipesCategoriesAdapter
        }


        binding.searchBarHome.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    viewModel.filterRecipes(newText)
                } else {
                    // Restaurar la lista original si el texto está vacío
                    viewModel.getOriginalRecipesCategory()
                }
                return true
            }
        })


        observeViewModel()

        activity?.let {
            viewModel.navigateToDetails.observe(it) { it ->
                val intent = Intent(activity, SelectedListOption::class.java)
                if (it != null) {
                    intent.putExtra("title", it.categoryName)

                    intent.putExtra("selector", "1")
                }
                startActivity(intent)
            }
        }


    }

    private fun observeViewModel() {
        viewModel.recipesCategories.observe(viewLifecycleOwner, Observer { categoriesCheck ->
            if (categoriesCheck.isEmpty()) {
                Log.i("HomeFragment", "recipesCategories is empty")
            } else {
                categoriesCheck?.let {
                    Log.d("HomeFragment", "Recipes Categories received: $it")
                    recipesCategoriesAdapter.updateRecipeCategory(it)
                }
            }
        })
    }

    override fun onCategoryClick(categoryClick: CategoryInfo) {
        Log.d("HomeFragment", "onCategoryClick called for ${categoryClick.categoryName}")
        Toast.makeText(activity, "El onClick Funciona bien", Toast.LENGTH_SHORT).show()
        viewModel.onFoodCategoryClicked(categoryClick)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}




