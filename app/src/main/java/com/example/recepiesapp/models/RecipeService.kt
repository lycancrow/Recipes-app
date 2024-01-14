package com.example.recepiesapp.models


import com.example.recepiesapp.dependencyInjection.DaggerApiComponent
import com.example.recepiesapp.models.dataClasses.CategoryInfo
import com.example.recepiesapp.models.dataClasses.DataClassSelectedCategory
import com.example.recepiesapp.models.dataClasses.Meals
import com.example.recepiesapp.models.interfaces.RecipeApi
import io.reactivex.Single
import javax.inject.Inject

class RecipeService {
    @Inject
    lateinit var api: RecipeApi


    init {
        DaggerApiComponent.create().inject(this)

    }


    fun getAllRecipesCategories(): Single<List<CategoryInfo>> {
        return api.getAllRecipesCategories()
            .map { dataClassCategories ->
                dataClassCategories.categories.map { category ->
                    CategoryInfo(
                        category.idCategory,
                        category.categoryName,
                        category.strCategoryImage,
                        category.strCategoryDescription
                    )
                }
            }

    }

    fun getRecipesByCategory(categoryMeals: String): Single<List<DataClassSelectedCategory>> {
        return api.getAllTipeMeals(categoryMeals)
            .map { dataClassRecipesByCategory ->
                dataClassRecipesByCategory.selectedCategoryList.map { meals ->
                    DataClassSelectedCategory(
                        meals.mealName,
                        meals.mealImage,
                        meals.mealId
                    )
                }
            }

    }


    fun getMealsByCountry(denomym: String): Single<List<DataClassSelectedCategory>> {
        return api.getMealsByCountry(denomym)
            .map { dataClassRecipesByCategory ->
                dataClassRecipesByCategory.selectedCategoryList.map { meals ->
                    DataClassSelectedCategory(
                        meals.mealName,
                        meals.mealImage,
                        meals.mealId
                    )
                }
            }

    }


    fun getMeal(mealName: String): Single<List<Meals>>{
        return api.getRecipe(mealName)
            .map { dataClassMeals ->
                dataClassMeals.meals.map { meal ->
                    Meals(
                        meal.idMeal,
                        meal.strMeal,
                        meal.strDrinkAlternate,
                        meal.strCategory,
                        meal.strArea,
                        meal.strInstructions,
                        meal.strMealThumb,
                        meal.strTags,
                        meal.strYoutube,
                        meal.strIngredient1,
                        meal.strIngredient2,
                        meal.strIngredient3,
                        meal.strIngredient4,
                        meal.strIngredient5,
                        meal.strIngredient6,
                        meal.strIngredient7,
                        meal.strIngredient8,
                        meal.strIngredient9,
                        meal.strIngredient10,
                        meal.strIngredient11,
                        meal.strIngredient12,
                        meal.strIngredient13,
                        meal.strIngredient14,
                        meal.strIngredient15,
                        meal.strIngredient16,
                        meal.strIngredient17,
                        meal.strIngredient18,
                        meal.strIngredient19,
                        meal.strIngredient20,

                        meal.strMeasure1,
                        meal.strMeasure2,
                        meal.strMeasure3,
                        meal.strMeasure4,
                        meal.strMeasure5,
                        meal.strMeasure6,
                        meal.strMeasure7,
                        meal.strMeasure8,
                        meal.strMeasure9,
                        meal.strMeasure10,
                        meal.strMeasure11,
                        meal.strMeasure12,
                        meal.strMeasure13,
                        meal.strMeasure14,
                        meal.strMeasure15,
                        meal.strMeasure16,
                        meal.strMeasure17,
                        meal.strMeasure18,
                        meal.strMeasure19,
                        meal.strMeasure20,

                        meal.strSource,
                        meal.strImageSource,
                        meal.strCreativeCommonsConfirmed,
                        meal.dateModified
                    )
                }
            }
    }



}
