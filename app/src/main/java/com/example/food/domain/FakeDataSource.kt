package com.example.food.domain

import com.example.food.domain.models.Category
import com.example.food.domain.models.Food
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeDataSource @Inject constructor(): DataSource {
    private val category1 = Category(
        id = 1,
        name = "italian",
        displayName = "Italian"
    )
    private val category2 = Category(
        id = 2,
        name = "chinese",
        displayName = "Chinese"
    )

    private val category3 = Category(
        id = 3,
        name = "german",
        displayName = "German"
    )

    private val category4 = Category(
        id = 4,
        name = "japanese",
        displayName = "Japanese"
    )

    private val category5 = Category(
        id = 5,
        name = "mexican",
        displayName = "Mexican"
    )

    private val category6 = Category(
        id = 6,
        name = "american",
        displayName = "American"
    )


    private val categoryList = listOf(
        category1,
        category2,
        category3,
        category4,
        category5,
        category6
    )

    private val food1 = Food(
        id = 11,
        name = "Soup",
        thumbnailUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4ehfDVe_Y5YuvJ7oc14SWbndJyWn5Ya49cQ&usqp=CAU",
        description = "SoupSoup",
        price = 0,
    )

    private val food2 = Food(
        id = 12,
        name = "Pasta",
        thumbnailUrl = "https://vkusvill.ru/upload/resize/640692/640692_1200x600x90_c.webp",
        description = "PastaPasta",
        price = 15,
    )

    private val food3 = Food(
        id = 13,
        name = "Salad",
        thumbnailUrl = "https://www.allrecipes.com/thmb/mvO1mRRH1zTz1SvbwBCTz78CRJI=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/67700_RichPastaforthePoorKitchen_ddmfs_4x3_2284-220302ec8328442096df370dede357d7.jpg",
        description = "Salad",
        price = 25,
    )

    private val foodList = listOf(
        food1,
        food2,
        food3,
        food1,
        food2,
        food3
    )

    override suspend fun getCategories(): List<Category> {
        delay(2000)
        return categoryList
    }

    override suspend fun getFoodsByCategory(category: Category): List<Food> {
        delay(2000)
        return foodList
    }

    override suspend fun getFoodItem(id: Long): Food {
        delay(1000)
        return foodList.first { it.id == id }
    }
}