package com.example.food.presentation.checkoutScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.food.R
import com.example.food.presentation.ErrorItem
import com.example.food.presentation.ErrorScreen
import com.example.food.presentation.LoadingScreen
import com.example.food.presentation.commonComposables.Counter
import com.example.food.presentation.commonComposables.StandardButton
import com.example.food.presentation.commonComposables.TopAppBarWithBackButton
import com.example.food.utils.priceFormatter

@Composable
fun CheckoutScreen(
    checkoutViewModel: CheckoutViewModel
) {
    val cartItemsState = checkoutViewModel.cartItemsState.observeAsState().value

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                StandardButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    enabled = true, //TODO
                    text = R.string.checkout,
                    imageVector = Icons.Default.ArrowForward,
                    onClick = {}
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            TopAppBarWithBackButton(
                onBackButtonClick = { /*TODO*/ },
                text = stringResource(id = R.string.cart_items)
            )
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-40).dp)
                    .verticalScroll(rememberScrollState()),
                shape = RoundedCornerShape(40.dp)
                    .copy(
                        bottomStart = ZeroCornerSize,
                        bottomEnd = ZeroCornerSize
                    ),
                color = colorResource(id = R.color.background)
            ) {
                Column {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        when (cartItemsState) {
                            CartItemsUiModel.Loading -> item { LoadingScreen() }
                            is CartItemsUiModel.Data -> {
                                items(
                                    cartItemsState.cartItems
                                ) { cartItem ->
                                    if (cartItemsState.cartItems.isNotEmpty()) {
                                        ShoppingCartItem(
                                            imageUrl = cartItem.thumbnailUrl,
                                            title = cartItem.name,
                                            quantity = cartItem.quantity,
                                            price = cartItem.price,
                                            onItemClicked = {},
                                            onQuantityChanged = {
                                                checkoutViewModel.changeQuantity(
                                                    it,
                                                    cartItem
                                                )
                                            }
                                        )
                                    } else {
                                    } //TODO()
                                }
                                item {
                                    ApplyCoupon()
                                    CheckoutDetails(price = cartItemsState.totalPriceItems, cartItemsState.totalQuantityOfItems)
                                }
                            }

                            CartItemsUiModel.Error -> item {
                                ErrorScreen(
                                    onButtonClicked = {} //TODO
                                )
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ShoppingCartItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    quantity: Int,
    price: Int,
    onItemClicked: () -> Unit,
    onQuantityChanged: (Boolean) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .clickable {
                onItemClicked()
            },
        shape = RoundedCornerShape(16.dp)
    ) {
        Row {
            SubcomposeAsyncImage(
                model = imageUrl,
                loading = { LoadingScreen() },
                error = { ErrorItem() },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(90.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colorResource(id = R.color.titleText),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                )
                Text(
                    text = priceFormatter(price * quantity),
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.outlineColor),
                    maxLines = 1,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                Counter(
                    modifier = Modifier
                        .align(Alignment.End),
                    quantity = quantity,
                    onQuantityChanged = onQuantityChanged,
                    scale = 0.6F,
                    enabledQuantity = quantity >= 1
                )
            }
        }
    }
}

@Composable
fun ApplyCoupon() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp),
            text = stringResource(id = R.string.apply_coupon),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            var applycode by remember { mutableStateOf("") }

            TextField(
                value = applycode,

                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(end = 10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = {
                    Text(
                        text = stringResource(id = R.string.enter_code),
                        color = Color.Gray
                    )
                },
                shape = RoundedCornerShape(8.dp),
                onValueChange = {
                    applycode = it
                }
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    //TODO
                },
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = colorResource(id = R.color.outlineColor)
                ),
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = stringResource(id = R.string.apply),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CheckoutDetails(
    price: Int,
    totalQuantityOfItems: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Text(
                text = "Price Details",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(id = R.string.items, totalQuantityOfItems),
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Text(
                    text = priceFormatter(price),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(id = R.string.delivery_charges),
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Text(
                    text = "$50.00",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(id = R.string.coupon_discount),
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Text(
                    text = "$184.00",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Divider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = Color.Gray,
                thickness = 1.dp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.total_amount_payable),
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "$1000.00",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.outlineColor)
                )
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ShoppingCartItemPreview() {
    ShoppingCartItem(
        title = "Soup",
        onQuantityChanged = {},
        onItemClicked = {},
        quantity = 1,
        price = 10,
        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4ehfDVe_Y5YuvJ7oc14SWbndJyWn5Ya49cQ&usqp=CAU"
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CheckoutDetailsPreview() {
    CheckoutDetails(price = 10, 3)
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun CheckoutScreenPreview() {
//    CheckoutScreen()
//}