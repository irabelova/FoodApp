package com.example.food.presentation.foodItemScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.food.R
import com.example.food.presentation.ErrorItem
import com.example.food.presentation.ErrorScreen
import com.example.food.presentation.LoadingScreen
import com.example.food.di.assistedViewModel
import java.text.NumberFormat
import java.util.Locale


@Composable
fun FoodItemScreen(
    navController: NavController,
    factory: FoodItemViewModel.Factory
) {
    val viewModel: FoodItemViewModel by navController.currentBackStackEntry!!.assistedViewModel {
        factory.create(
            it
        )
    }

    when (val foodItemState = viewModel.foodItemState.observeAsState().value) {
        FoodItemUiModel.Loading -> LoadingScreen()
        is FoodItemUiModel.Data -> FoodItem(
            imageURl = foodItemState.foodItem.thumbnailUrl,
            title = foodItemState.foodItem.name,
            description = foodItemState.foodItem.description,
            price = foodItemState.foodItem.price
        )

        FoodItemUiModel.Error -> ErrorScreen(
            onButtonClicked = { viewModel.getFoodItem() }
        )

        else -> {}
    }
}

@Composable
fun FoodItem(
    imageURl: String,
    title: String,
    description: String,
    price: Int
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SubcomposeAsyncImage(
            model = imageURl,
            loading = { LoadingScreen() },
            error = { ErrorItem() },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(40.dp)
                .copy(
                    bottomStart = ZeroCornerSize,
                    bottomEnd = ZeroCornerSize
                ), modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f)
                .offset(y = (-40).dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                PriceAndQuantityCounter(
                    modifier = Modifier.padding(vertical = 10.dp),
                    price = price
                )
                Divider(
                    color = colorResource(id = R.color.descriptionText),
                    thickness = 1.dp
                )
                Column(
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = "About",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = description,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.descriptionText)
                    )
                }
                Button(
                    onClick = {
                        //TODO:
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.selectedCategory)
                    ),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .height(60.dp)
                        .width(200.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Add to Cart",
                        color = colorResource(id = R.color.outlineColor),
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = colorResource(id = R.color.outlineColor),
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(20.dp, 20.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun PriceAndQuantityCounter(
    modifier: Modifier,
    price: Int
) {
    val counter = remember { mutableStateOf(1) }
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (price == 0)
                stringResource(id = R.string.not_available)
            else
                NumberFormat.getCurrencyInstance(Locale.US).format(price),
            color = if (price == 0)
                colorResource(id = R.color.descriptionText)
            else
                colorResource(id = R.color.outlineColor),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .width(110.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.descriptionText))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    counter.value--
                }) {
                    Icon(
                        imageVector = Icons.Filled.Remove,
                        contentDescription = "",
                        tint = Color.White
                    )
                }

                Text(
                    text = "${counter.value}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                IconButton(onClick = {
                    counter.value++
                }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "",
                        tint = Color.White,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodItemPreview() {
    FoodItem(
        title = "Soup",
        description = "Description how to cook soup, Description how to cook soupDescription how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup Description how to cook soup, Description how to cook soupDescription how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup",
        price = 10,
        imageURl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4ehfDVe_Y5YuvJ7oc14SWbndJyWn5Ya49cQ&usqp=CAU"
    )
}
