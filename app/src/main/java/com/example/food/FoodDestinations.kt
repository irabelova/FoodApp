package com.example.food

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.ui.graphics.vector.ImageVector

sealed class FoodBottomMenuItem(
    val imageVector: ImageVector,
    @StringRes val text: Int,
    val route: String,
) {
    object Menu : FoodBottomMenuItem(
        imageVector = Icons.Filled.Fastfood,
        text = R.string.menu,
        route = "menu"
    )

    object Profile : FoodBottomMenuItem(
        imageVector = Icons.Filled.Person,
        text = R.string.profile,
        route = "profile"
    )

    object Basket : FoodBottomMenuItem(
        imageVector = Icons.Filled.ShoppingBasket,
        text = R.string.basket,
        route = "basket"
    )

    object Cities: FoodBottomMenuItem(
        imageVector = Icons.Filled.Fastfood,
        text = R.string.menu,
        route = "cities"
    )
}