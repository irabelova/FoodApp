package com.example.food.presentation.mainScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.food.FoodBottomMenuItem
import com.example.food.R


@Composable
fun FoodTopBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    selectedCity: String = "Москва"
) {

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val isExpanded = currentDestination?.hierarchy?.any {
        it.route == FoodBottomMenuItem.Cities.route
    } == true

    TopAppBar(
        title = {
            Surface(
                color = colorResource(id = R.color.background)
            ) {
                Row(
                    modifier = modifier
                        .height(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = selectedCity)
                    IconButton(
                        onClick = {
                            if (!isExpanded) {
                                navController.navigate(FoodBottomMenuItem.Cities.route)
                            } else {
                                navController.popBackStack()
                            }
                        }) {
                        if (isExpanded) {
                            Icon(Icons.Filled.ExpandLess, null)
                        } else {
                            Icon(Icons.Filled.ExpandMore, null)
                        }
                    }
                }
            }
        },
        actions = {
            IconButton(onClick = {/* Do Something*/ }) {
                Icon(Icons.Filled.QrCodeScanner, null)
            }
        },
        backgroundColor = colorResource(id = R.color.background),
        contentColor = colorResource(id = R.color.titleText),
    )
}

