package com.example.project_farmcare

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project_farmcare.Adapters.FavouritesMealsAdapter
import com.example.project_farmcare.databinding.FragmentFavouriteBinding
import com.example.project_farmcare.packages.MainActivity

class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favouritesAdapter: FavouritesMealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavourites()
    }

    private fun prepareRecyclerView() {
        favouritesAdapter = FavouritesMealsAdapter()
        binding.favRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favouritesAdapter

        }
    }

    private fun observeFavourites() {
        viewModel.observeFavoritesMealsLiveData().observe(viewLifecycleOwner, Observer {
            meals ->
            favouritesAdapter.differ.submitList(meals)

        })
    }


}