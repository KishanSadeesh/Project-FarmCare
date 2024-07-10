package com.example.project_farmcare.retrofit

import com.example.project_farmcare.pojo.CategoryList
import com.example.project_farmcare.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {
    @GET( "random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>
}