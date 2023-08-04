package com.example.food

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.food.di.AppComponent
import com.example.food.di.DaggerAppComponent
import com.example.food.di.ViewModelProviderFactory
import com.example.food.presentation.ErrorScreen
import com.example.food.presentation.FoodBottomNavigationMenu
import com.example.food.presentation.LoadingScreen
import com.example.food.presentation.mainScreen.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var component: AppComponent

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
            FoodApp()
        }
    }

    @Composable
    fun FoodScreen() {
        val categoriesState = viewModel.categoryState.observeAsState().value
        val foodState = viewModel.foodState.observeAsState().value
        val selectedCategory = viewModel.selectedCategory.observeAsState().value

        when (categoriesState) {
            CategoryState.InitialLoading -> LoadingScreen()
            is CategoryState.CategoryData -> Food(
                categories = categoriesState.categories,
                selectedCategory = selectedCategory,
                foodState = foodState,
                onCategorySelected = { viewModel.changeCategory(it) },
                onReloadFood = { viewModel.reloadFoods() }
            )
            CategoryState.InitialLoadingError -> ErrorScreen(
                onButtonClicked = { viewModel.initialLoading() }
            )
            else -> {}
        }
    }

    @Composable
    private fun FoodNavHost(
        navController: NavHostController,
        modifier: Modifier = Modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = FoodBottomMenuItem.Menu.route,
            modifier = modifier
        ) {
            composable(FoodBottomMenuItem.Menu.route) {
                FoodScreen()
            }
            composable(FoodBottomMenuItem.Profile.route) {
                //TODO: to add new screen
            }
            composable(FoodBottomMenuItem.Basket.route) {
                //TODO: to add new screen
            }
            composable(FoodBottomMenuItem.Cities.route) {
                CitiesList(
                    onCitySelected = {
                    navController.popBackStack()
                        viewModel.changeCity(it)
                                     },
                )
            }
        }
    }


    @Composable
    private fun FoodApp() {
        val navController = rememberNavController()
        val selectedCity = viewModel.selectedCity.observeAsState().value


        Scaffold(
            modifier = Modifier,
            topBar = { FoodTopBar(
                navController = navController,
                selectedCity = selectedCity!!
            ) },
            bottomBar = {
                FoodBottomNavigationMenu(
                    navController = navController
                )
            }
        ) { innerPadding ->
            FoodNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
