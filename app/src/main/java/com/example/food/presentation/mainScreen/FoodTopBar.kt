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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.food.R


@Composable
fun FoodTopBar(
    modifier: Modifier = Modifier
) {
    var selectedCity: String by rememberSaveable { mutableStateOf("Москва") }
    var expanded: Boolean by rememberSaveable { mutableStateOf(false) }

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
                            expanded = !expanded
                        }) {
                        if (expanded) {
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


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    FoodTopBar()
}