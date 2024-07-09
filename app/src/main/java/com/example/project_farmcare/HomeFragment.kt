package com.example.project_farmcare

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ActionMode.Callback
import com.example.project_farmcare.pojo.Meal
import com.example.project_farmcare.pojo.MealList
import com.example.project_farmcare.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated (view, savedInstanceState)
        RetrofitInstance.api.getRandomMeal().enqueue(object: retrofit2.Callback<MealList> {
            override fun onResponse(p0: Call<MealList>, p1: Response<MealList>) {
                if(p1.body() !=null) {
                    val randomMeal: Meal = p1.body()!!.meals[0]
                    Log.d("TEST","meal id ${randomMeal.idMeal} name ${randomMeal.strMeal}")
                }else{
                    return
                }
            }

            override fun onFailure(p0: Call<MealList>, p1: Throwable) {
                Log.d("Home Fragment", p1.message.toString())
            }

        })
    }


}

