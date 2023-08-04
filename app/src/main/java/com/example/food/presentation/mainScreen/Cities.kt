package com.example.food.presentation.mainScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.food.R


@Composable
fun City(
    modifier: Modifier = Modifier,
    text: String,
    onClickCity: (String) -> Unit,
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        border = BorderStroke(
            1.dp,
            color = colorResource(id = R.color.inactiveTextButton)
        ),
    ) {
        Row(
            modifier = modifier.clickable { onClickCity(text) },
            verticalAlignment = Alignment.CenterVertically
        ) {
                Text(
                    modifier = modifier
                        .padding(start = 16.dp),
                    fontSize = 16.sp,
                    text = text,
                    color = colorResource(id = R.color.black)
                )
        }
    }
}


@Composable
fun CitiesList(
    modifier: Modifier = Modifier,
    onCitySelected: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        citiesList.forEach { city ->
            City(
                text = city,
                onClickCity = {
                    onCitySelected(it)},
            )
        }
    }
}


val citiesList = listOf(
    "Москва",
    "Санкт-Петербург",
    "Новосибирск",
    "Воронеж",
    "Екатеринбург",
    "Краснодар",
    "Красноярск",
    "Пермь",
    "Волгоград",
    "Казань",
    "Нижний Новгород",
    "Омск",
    "Ростов-на-Дону",
    "Самара",
    "Уфа",
    "Челябинск"
)

@Preview(showBackground = true)
@Composable
fun CitiesListPreview() {
    CitiesList(
        modifier = Modifier,
        onCitySelected = {}
    )
}

