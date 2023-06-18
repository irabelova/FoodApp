package com.example.food.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.food.FoodApplication
import com.example.food.R
import com.example.food.databinding.FoodFragmentBinding
import com.example.food.di.DependencyProviders
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food

class FoodFragment : Fragment() {
    private lateinit var binding: FoodFragmentBinding
    private lateinit var provider: DependencyProviders
    private val viewModel: FoodViewModel by viewModels {
        FoodViewModel.FoodViewModelFactory(provider.getRepository())
    }
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onAttach(context: Context) {
        provider = (requireActivity().application as FoodApplication).provider
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoodFragmentBinding.inflate(inflater, container, false)
        foodAdapter = FoodAdapter()
        categoriesAdapter = CategoriesAdapter { viewModel.changeCategory(it) }
        binding.recycler.adapter = foodAdapter
        binding.recyclerHorizontal.adapter = categoriesAdapter
        parentFragmentManager.setFragmentResultListener(
            CitiesFragment.CITY_REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            binding.cityButton.text = bundle.getString(CitiesFragment.CITY_BUNDLE_KEY)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.categoryState.observe(viewLifecycleOwner) {
            when (it) {
                CategoryState.InitialLoading -> showLoadingCategories()
                is CategoryState.CategoryData -> showCategories(it.categories)
                CategoryState.InitialLoadingError -> showErrorCategories()
            }

        }
        viewModel.foodState.observe(viewLifecycleOwner) {
            when (it) {
                FoodState.Loading -> showLoadingFoods()
                is FoodState.FoodData -> showFoods(it.foodList)
                FoodState.Error -> showErrorFoods()
            }
        }
        viewModel.selectedCategory.observe(viewLifecycleOwner) {
            categoriesAdapter.updateSelectedCategory(it)
        }


        binding.cityButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, CitiesFragment())
                .commit()
        }
    }


    private fun showCategories(categories: List<Category>) {
        binding.statusContainer.visibility = View.GONE
        binding.statusImage.visibility = View.GONE
        categoriesAdapter.updateCategories(categories)
    }

    private fun showLoadingCategories() {
        binding.statusContainer.visibility = View.VISIBLE
        binding.statusImage.visibility = View.VISIBLE
        binding.statusImage.setImageResource(R.drawable.loading_animation)
    }

    private fun showErrorCategories() {
        binding.statusContainer.visibility = View.VISIBLE
        binding.statusImage.visibility = View.VISIBLE
        binding.statusImage.setImageResource(R.drawable.ic_connection_error)
    }

    private fun showFoods(foodList: List<Food>) {
        binding.recycler.visibility = View.VISIBLE
        binding.statusRecycleImage.visibility = View.GONE
        foodAdapter.submitList(foodList)
    }

    private fun showLoadingFoods() {
        binding.recycler.visibility = View.GONE
        binding.statusRecycleImage.visibility = View.VISIBLE
        binding.statusRecycleImage.setImageResource(R.drawable.loading_animation)
    }

    private fun showErrorFoods() {
        binding.recycler.visibility = View.GONE
        binding.statusRecycleImage.visibility = View.VISIBLE
        binding.statusRecycleImage.setImageResource(R.drawable.ic_connection_error)
    }

}