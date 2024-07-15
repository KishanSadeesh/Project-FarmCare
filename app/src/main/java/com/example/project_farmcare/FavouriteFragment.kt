package com.example.project_farmcare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.project_farmcare.Adapters.MealsAdapter
import com.example.project_farmcare.databinding.FragmentFavouriteBinding
import com.example.project_farmcare.packages.MainActivity
import com.google.android.material.snackbar.Snackbar

class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favouritesAdapter: MealsAdapter


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

        val itemtouchHelper = object : ItemTouchHelper.SimpleCallback (
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) =  true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favouritesAdapter.differ.currentList[position])

                Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(favouritesAdapter.differ.currentList[position])
                    }
                ).show()
            }

        }
        ItemTouchHelper(itemtouchHelper).attachToRecyclerView(binding.favRecyclerView)
    }

    private fun prepareRecyclerView() {
        favouritesAdapter = MealsAdapter()
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