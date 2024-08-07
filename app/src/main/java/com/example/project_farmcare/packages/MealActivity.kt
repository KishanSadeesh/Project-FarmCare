package com.example.project_farmcare.packages

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.project_farmcare.HomeFragment
import com.example.project_farmcare.MealViewModel
import com.example.project_farmcare.MealViewModelFactory
import com.example.project_farmcare.databinding.ActivityMealBinding
import com.example.project_farmcare.db.MealDatabase
import com.example.project_farmcare.pojo.Meal
import com.google.android.material.snackbar.Snackbar

class MealActivity : AppCompatActivity() {
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var youtubeLink: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)

        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]
        getMealInfo()
        setInfoView()

        loadingcase()

        mealMvvm.getMealDetails(mealId)
        observeMealDetailsLiveData()
        onFavoriteClick()
        onYoutubeClick()
    }

    private fun onFavoriteClick() {
        binding.btnFav.setOnClickListener{
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Snackbar.make(binding.root, "Meal Saved", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeClick() {
        binding.imgYoutube.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave: Meal? = null

    private fun observeMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this,object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal = value
                mealToSave = meal
                binding.collapsingToolbar.title = meal.strMeal
                binding.tvCategoryInfo.text = "Category : ${meal.strCategory}"
                binding.tvAreaInfo.text = "Area : ${meal.strArea}"
                binding.tvContent.text = meal.strInstructions
                youtubeLink = meal.strYoutube
            }

        })
    }
    private fun getMealInfo() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun setInfoView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
    }

    private fun loadingcase()
    {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvContent.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase()
    {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvContent.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }




}