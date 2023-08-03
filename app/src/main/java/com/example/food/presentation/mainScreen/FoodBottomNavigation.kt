package com.example.food.presentation.mainScreen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.food.R


sealed class FoodBottomMenuItem(
    val imageVector: ImageVector,
    @StringRes val text: Int,
) {
    object Menu : FoodBottomMenuItem(
        imageVector = Icons.Filled.Fastfood,
        text = R.string.menu,
    )

    object Profile : FoodBottomMenuItem(
        imageVector = Icons.Filled.Person,
        text = R.string.profile,
    )

    object Basket : FoodBottomMenuItem(
        imageVector = Icons.Filled.ShoppingBasket,
        text = R.string.basket,
    )
}

@Composable
fun FoodBottomNavigationMenu(selectedIndex: Int, onSelectedIndex: (Int) -> Unit, modifier: Modifier = Modifier) {

    val items = listOf(
        FoodBottomMenuItem.Menu,
        FoodBottomMenuItem.Profile,
        FoodBottomMenuItem.Basket,
    )
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.bottomBackground),
            modifier = Modifier.align(alignment = Alignment.BottomCenter)
        ) {

            items.forEachIndexed { index, item ->
                val color =
                    if (index == selectedIndex)
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
                    selected = index == selectedIndex,
                    onClick = {
                        onSelectedIndex(index)
                    }
                )
            }
        }
    }
}

@Composable
fun StatefulMenuItem(modifier: Modifier = Modifier) {
    var selectedIndex: Int by rememberSaveable { mutableStateOf(0) }

    FoodBottomNavigationMenu(
        selectedIndex = selectedIndex,
        onSelectedIndex = {selectedIndex = it},
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun FoodBottomNavigationMenuPreview() {
    StatefulMenuItem()
}