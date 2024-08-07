package com.example.project_farmcare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_farmcare.db.MealDatabase
import com.example.project_farmcare.pojo.Meal
import com.example.project_farmcare.pojo.MealList
import com.example.project_farmcare.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
   val mealDatabase: MealDatabase
):ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<Meal>()
    fun getMealDetails(id: String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!= null)
                {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }
                else
                    return
            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {
                Log.d("MealActivity", p1.message.toString())
            }

        })
    }

    fun observeMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData

    }

    fun insertMeal(meal: Meal)
    {
        viewModelScope.launch{
            mealDatabase.mealDao().upsert(meal)
        }
    }

}