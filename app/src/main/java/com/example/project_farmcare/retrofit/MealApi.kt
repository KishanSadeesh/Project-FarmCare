package com.example.project_farmcare.retrofit

import com.example.project_farmcare.pojo.CategoryList
import com.example.project_farmcare.pojo.MealList
import com.example.project_farmcare.pojo.MealsByCategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET( "random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String) : Call<MealsByCategoryList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id: String) : Call<MealList>

}