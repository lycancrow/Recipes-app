package com.example.recepiesapp.view.especificListSelected

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recepiesapp.databinding.ActivitySelectedListOptionBinding
import com.example.recepiesapp.models.dataClasses.DataClassSelectedCategory
import com.example.recepiesapp.util.ItemOffsetDecoration
import com.example.recepiesapp.view.recipeDetails.RecipeDetails


class SelectedListOption : AppCompatActivity() {
    private lateinit var binding: ActivitySelectedListOptionBinding
    private lateinit var viewModel: SelectedListViewModel
    private val selectedListAdapter = SelectedListAdapter(arrayListOf(), object : OnFeaturedRecipeCategoryClickListener {
        override fun onRecipeCategoryClick(categoryRecipeClick: DataClassSelectedCategory) {
            viewModel.onMealCategoryClicked(categoryRecipeClick)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedListOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val categoryName = intent.extras?.getString("title")
        val categoryDemonym = intent.extras?.getString("demonym")
        val selector = intent.extras?.getString("selector")
        //demonym

        viewModel = ViewModelProviders.of(this)[SelectedListViewModel::class.java]
        if(selector == "1"){
            viewModel.getMealsRecipesCategory(categoryName.orEmpty())
        }else if(selector == "2"){
            viewModel.getMealsByCountry(categoryDemonym.toString())
        }

        binding.titleSelectedListOption.text = categoryName.orEmpty()

        binding.selectedListRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(ItemOffsetDecoration(16))
            adapter = selectedListAdapter
        }

        observeViewModel()


        binding.searchBarMeals.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    viewModel.filterRecipesByCategory(newText)
                } else {
                    // Restaurar la lista original si el texto está vacío
                    if(selector == "1"){
                        viewModel.getMealsRecipesCategory(categoryName.orEmpty())
                    }else if(selector == "2"){
                        viewModel.getMealsByCountry(categoryDemonym.toString())
                    }

                }
                return true
            }
        })

    }

    private fun observeViewModel() {
        viewModel.recipesCategoriesSelected.observe(this) { meals ->
            meals?.let {
                selectedListAdapter.updateRecipesByCategory(it)
            }
        }

        viewModel.navigateToDetails.observe(this) { meal ->
            meal?.let {
                val intent = Intent(this, RecipeDetails::class.java)
                intent.putExtra("selectedMeal", it.mealName)
                intent.putExtra("selectedMealId", it.mealId)
                startActivity(intent)
            }
        }
    }




}

