package com.example.food

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.food.di.AppComponent
import com.example.food.di.DaggerAppComponent
import com.example.food.di.ViewModelProviderFactory
import com.example.food.presentation.mainScreen.CategoryState
import com.example.food.presentation.mainScreen.FoodViewModel
import com.example.food.presentation.ErrorScreen
import com.example.food.presentation.mainScreen.FoodScreen
import com.example.food.presentation.LoadingScreen
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var component: AppComponent

    @Inject
    protected lateinit var providerFactory: ViewModelProviderFactory

    private val viewModel: FoodViewModel by viewModels {
        providerFactory
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = DaggerAppComponent.factory().create(applicationContext)
        component.inject(this)

        setContent {
            val categoriesState = viewModel.categoryState.observeAsState().value
            val foodState = viewModel.foodState.observeAsState().value
            val selectedCategory = viewModel.selectedCategory.observeAsState().value

            when (categoriesState) {
                CategoryState.InitialLoading -> LoadingScreen()
                is CategoryState.CategoryData -> FoodScreen(
                    modifier = Modifier.fillMaxSize(),
                    categories = categoriesState.categories,
                    selectedCategory = selectedCategory,
                    foodState = foodState,
                    selectedIndex = 0,
                    onCategorySelected = { viewModel.changeCategory(it) },
                    onSelectedIndex = {},
                    onReloadFood = {viewModel.reloadFoods()}
                )
                CategoryState.InitialLoadingError -> ErrorScreen(
                    onButtonClicked = {viewModel.initialLoading()}
                )
                else -> {}
            }
        }
    }
}