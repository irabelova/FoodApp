package com.example.food.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.food.FoodBottomMenuItem
import com.example.food.R


@Composable
fun FoodBottomNavigationMenu(navController: NavController, modifier: Modifier = Modifier) {

    val items = listOf(
        FoodBottomMenuItem.Menu,
        FoodBottomMenuItem.Profile,
        FoodBottomMenuItem.Basket
    )
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.bottomBackground),
            modifier = Modifier.align(alignment = Alignment.BottomCenter)
        ) {

            items.forEach { item ->
                val isSelected = currentDestination?.hierarchy?.any {
                    it.route == item.route
                } == true
                val color =
                    if (isSelected)
                        colorResource(id = R.color.outlineColor)
                    else
                        colorResource(id = R.color.inactiveItemInBottomMenu)

                BottomNavigationItem(
                    icon = {
                        Icon(
                            modifier = modifier,
                            imageVector = item.imageVector,
                            contentDescription = null,
                            tint = color
                        )
                    },
                    label = {
                        Text(
                            modifier = modifier,
                            text = stringResource(item.text),
                            color = color
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                            //Pop up to the start destination of the graph to
                            //avoid building up a large stack of destinations
                            //on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = false
                            }

                            //Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true

                            //Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

