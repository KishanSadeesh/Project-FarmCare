package com.example.project_farmcare.packages

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.project_farmcare.HomeViewModel
import com.example.project_farmcare.HomeViewModelFactory
import com.example.project_farmcare.R
import com.example.project_farmcare.db.MealDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val viewModel: HomeViewModel by lazy{
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelProviderFactory)[HomeViewModel::class.java]

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_nav);
        val navController = Navigation.findNavController(this, R.id.host_fragment);
        NavigationUI.setupWithNavController(bottomNavigation, navController)


      /* val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
        val navController = navHostFragment.navController*/
    }
}