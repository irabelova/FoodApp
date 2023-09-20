package com.example.food.presentation.foodItemScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.food.presentation.FoodBottomMenuItem
import com.example.food.R
import com.example.food.presentation.ErrorItem
import com.example.food.presentation.ErrorScreen
import com.example.food.presentation.LoadingScreen
import com.example.food.presentation.commonComposables.Counter
import com.example.food.presentation.commonComposables.StandardButton
import com.example.food.utils.priceFormatter


@Composable
fun FoodItemScreen(
    navController: NavController,
    viewModel: FoodItemViewModel
) {
    val foodItemState = viewModel.foodItemStateFlow.collectAsState(FoodItemUiModel.Loading).value

    when (foodItemState) {
        FoodItemUiModel.Loading -> LoadingScreen()
        is FoodItemUiModel.Data ->
            FoodItem(
                imageURl = foodItemState.foodItem.thumbnailUrl,
                title = foodItemState.foodItem.name,
                description = foodItemState.foodItem.description,
                price = foodItemState.foodItem.price,
                onQuantityChanged = {viewModel.changeQuantityCounter(it)},
                quantity = foodItemState.cartItem.quantity,
                onClickAddToCart = {
                    viewModel.addFoodItemToShoppingCart()
                },
                onClickContinueShopping = {navController.navigate(FoodBottomMenuItem.Menu.route)},
                onClickGoToCart = {
                    viewModel.clearItem()
                    navController.navigate(FoodBottomMenuItem.ShoppingCart.route)
                                  },
                isAddedToCart = foodItemState.cartItem.isAddedToCart
            )

        FoodItemUiModel.Error -> ErrorScreen(
            onButtonClicked = { viewModel.loadFoodItem() }
        )
    }
}

@Composable
fun FoodItem(
    imageURl: String,
    title: String,
    description: String,
    price: Float,
    onQuantityChanged: (Boolean) -> Unit,
    quantity: Int,
    onClickAddToCart: () -> Unit,
    onClickContinueShopping: () -> Unit,
    onClickGoToCart: () -> Unit,
    isAddedToCart: Boolean
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
                if (price != 0F) {
                    PriceAndQuantityCounter(
                        modifier = Modifier.padding(vertical = 10.dp),
                        price = price,
                        onQuantityChanged = onQuantityChanged,
                        quantity = quantity
                    )
                } else
                    NotAvailableFood(modifier = Modifier.padding(vertical = 10.dp))
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
                        text = stringResource(id = R.string.about),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = description,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.descriptionText)
                    )
                }
                if (!isAddedToCart) {
                    StandardButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        enabled = price != 0F,
                        text = R.string.add_to_cart,
                        imageVector = Icons.Default.ArrowForward,
                        onClick = {
                            onClickAddToCart()
                        }
                    )
                }
                else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        StandardButton(
                            modifier = Modifier.weight(1f),
                            enabled = true,
                            text = R.string.continue_shopping,
                            imageVector = Icons.Default.ShoppingBasket,
                            onClick = { onClickContinueShopping() }
                        )
                        StandardButton(
                            modifier = Modifier.weight(1f),
                            enabled = true,
                            text = R.string.go_to_cart,
                            imageVector = Icons.Default.ArrowForward,
                            onClick = { onClickGoToCart() }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun PriceAndQuantityCounter(
    modifier: Modifier,
    price: Float,
    onQuantityChanged: (Boolean) -> Unit,
    quantity: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = priceFormatter(price*quantity),
            color = colorResource(id = R.color.outlineColor),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Counter(
            onQuantityChanged = onQuantityChanged,
            quantity = quantity,
            scale = 1F,
            enabledQuantity = quantity>1
        )
    }
}

@Composable
fun NotAvailableFood(
    modifier: Modifier,
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.not_available),
        color = colorResource(id = R.color.descriptionText),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
fun FoodItemPreview() {
    FoodItem(
        title = "Soup",
        onQuantityChanged = {},
        quantity = 1,
        description = "Description how to cook soup, Description how to cook soupDescription how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup Description how to cook soup, Description how to cook soupDescription how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup, Description how to cook soup",
        price = 10F,
        imageURl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4ehfDVe_Y5YuvJ7oc14SWbndJyWn5Ya49cQ&usqp=CAU",
        onClickAddToCart = {},
        onClickContinueShopping = {},
        onClickGoToCart = {},
        isAddedToCart = false
    )
}
