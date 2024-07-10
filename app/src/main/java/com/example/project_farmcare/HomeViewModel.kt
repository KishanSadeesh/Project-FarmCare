package com.example.project_farmcare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project_farmcare.pojo.Category
import com.example.project_farmcare.pojo.CategoryList
import com.example.project_farmcare.pojo.Meal
import com.example.project_farmcare.pojo.MealList
import com.example.project_farmcare.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {
    private val randomMealLiveData = MutableLiveData<Meal>()
    private var categoryLiveData = MutableLiveData<List<Category>>()

    fun getRandomMeal()
    {
        RetrofitInstance.api.getRandomMeal().enqueue(object: retrofit2.Callback<MealList> {
            override fun onResponse(p0: Call<MealList>, p1: Response<MealList>) {
                if(p1.body() !=null) {
                    val randomMeal: Meal = p1.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }else{
                    return
                }
            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {
                Log.d("Home Fragment", p1.message.toString())
            }
        })
    }



    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList -> categoryLiveData.postValue(categoryList.categories) }
            }

            override fun onFailure(p0: Call<CategoryList>, p1: Throwable) {
                Log.e("HomeViewModel", p1.message.toString())
            }
        })
    }


    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
    fun observeCategoriesLiveData():LiveData<List<Category>>{
        return categoryLiveData
    }

}