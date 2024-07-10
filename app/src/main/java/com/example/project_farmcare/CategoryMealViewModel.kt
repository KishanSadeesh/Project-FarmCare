package com.example.project_farmcare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project_farmcare.pojo.MealsByCategory
import com.example.project_farmcare.pojo.MealsByCategoryList
import com.example.project_farmcare.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response


class CategoryMealViewModel : ViewModel() {
    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : retrofit2.Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ){
                response.body().let { mealsList ->
                    mealsLiveData.postValue(mealsList!!.meals)
                }

        }

            override fun onFailure(p0: Call<MealsByCategoryList>, p1: Throwable) {
                Log.e("CategoryMealsViewModel", p1.message.toString())
            }

        })
    }

    fun observeMealsLiveData(): LiveData<List<MealsByCategory>> {
        return mealsLiveData
    }
}