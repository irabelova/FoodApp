package com.example.food.presentation.mainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.food.domain.models.Category
import com.example.food.domain.models.Food
import com.example.food.presentation.ErrorScreen
import com.example.food.presentation.LoadingScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Food(
    categories: List<Category>,
    selectedCategory: Category?,
    foodState: FoodState?,
    onCategorySelected: (Category) -> Unit,
    onReloadFood: () -> Unit
) {
    val listState = rememberLazyListState()
    val firstItemIndex =  remember {
        derivedStateOf {
            listState.firstVisibleItemIndex }
    }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if(firstItemIndex.value > 0) {
                Categories(
                    categoryList = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = onCategorySelected
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = listState
            ) {
                item {
                    Banners()
                }
                if(firstItemIndex.value == 0) {
                    item {
                        Categories(
                            categoryList = categories,
                            selectedCategory = selectedCategory,
                            onCategorySelected = onCategorySelected
                        )
                    }
                }
                when (foodState) {
                    FoodState.Loading -> item{ LoadingScreen() }
                    is FoodState.FoodData ->  items(foodState.foodList) { food ->
                        FoodElement(
                            modifier = Modifier.padding(16.dp),
                            imageURl = food.thumbnailUrl,
                            recipeName = food.name,
                            recipeDescription = food.description,
                            recipeTime = food.timeMinutes
                        )
                    }
                    FoodState.Error -> item {
                        ErrorScreen(
                            onButtonClicked = onReloadFood
                        )
                    }
                    else -> {}
                }
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun FoodPreview() {
    val food1 = Food(
        id = 11,
        name = "Soup",
        thumbnailUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4ehfDVe_Y5YuvJ7oc14SWbndJyWn5Ya49cQ&usqp=CAU",
        description = "SoupSoup",
        timeMinutes = 0
    )

    val food2 = Food(
        id = 12,
        name = "Pasta",
        thumbnailUrl = "https://vkusvill.ru/upload/resize/640692/640692_1200x600x90_c.webp",
        description = "PastaPasta",
        timeMinutes = 15
    )

    val food3 = Food(
        id = 13,
        name = "Salad",
        thumbnailUrl = "https://www.allrecipes.com/thmb/mvO1mRRH1zTz1SvbwBCTz78CRJI=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/67700_RichPastaforthePoorKitchen_ddmfs_4x3_2284-220302ec8328442096df370dede357d7.jpg",
        description = "Salad",
        timeMinutes = 25
    )

    val foodList = listOf(
        food1,
        food2,
        food3,
        food1,
        food2,
        food3
    )
    Food(
        categories = categoryList,
        selectedCategory = categoryList[0],
        foodState = FoodState.FoodData(foodList),
        onCategorySelected = {},
        onReloadFood = {}
    )
}

