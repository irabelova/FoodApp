package com.example.food.presentation.mainScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.food.presentation.ErrorItem
import com.example.food.presentation.LoadingScreen
import java.text.NumberFormat
import java.util.*

@Composable
fun FoodElement(
    modifier: Modifier = Modifier,
    imageURl: String,
    title: String,
    description: String,
    price: Int,
    onItemClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClicked()
            }
    ) {
        SubcomposeAsyncImage(
            model = imageURl,
            loading = { LoadingScreen() },
            error = { ErrorItem() },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(135.dp)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .height(135.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = colorResource(id = com.example.food.R.color.titleText),
                maxLines = 1,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = colorResource(id = com.example.food.R.color.descriptionText),
                maxLines = 4,
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 8.dp)
                    .weight(1f)
            )
            Surface(
                border = BorderStroke(
                    1.dp,
                    color = if (price == 0)
                        colorResource(id = com.example.food.R.color.descriptionText)
                    else
                        colorResource(id = com.example.food.R.color.outlineColor)
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(90.dp)
                    .height(30.dp)
                    .align(Alignment.End)

            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = if (price == 0)
                            stringResource(id = com.example.food.R.string.not_available)
                        else
                            NumberFormat.getCurrencyInstance(Locale.US).format(price),
                        fontSize = 13.sp,
                        color = if (price == 0)
                            colorResource(id = com.example.food.R.color.descriptionText)
                        else
                            colorResource(id = com.example.food.R.color.outlineColor),
                        maxLines = 1,
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FoodElementPreview() {
    FoodElement(
        title = "Soup",
        description = "Description how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup",
        price = 10,
        imageURl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4ehfDVe_Y5YuvJ7oc14SWbndJyWn5Ya49cQ&usqp=CAU",
        onItemClicked = {}
    )
}
