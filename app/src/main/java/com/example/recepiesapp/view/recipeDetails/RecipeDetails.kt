package com.example.recepiesapp.view.recipeDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.recepiesapp.databinding.ActivityRecipeDetailsBinding
import com.example.recepiesapp.models.dataClasses.Meals

class RecipeDetails : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailsBinding
    lateinit var recipesDetailsViewModel: RecipeDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val recipeName = intent.extras?.getString("selectedMeal")
        val recipeId = intent.extras?.getString("selectedMealId")


        binding.recipeTitleTextView.text = recipeName.toString()

        recipesDetailsViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel::class.java)
        recipesDetailsViewModel.getRecipeDetails(recipeId.toString())


        recipesDetailsViewModel._recipeDetails.observe(this) { recipeDetails ->
            recipeDetails?.let { updateUI(it) }
        }

    }


    private fun updateUI(recipeDetails: Meals) {
        binding.recipeInstructionsTextView.text = recipeDetails.strInstructions
        Glide.with(this).load(recipeDetails.strMealThumb).into(binding.recipeImageView)
    }
}