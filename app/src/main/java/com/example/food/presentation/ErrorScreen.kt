package com.example.food.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.food.R

@Composable
fun ErrorItem() {
    Image(
        modifier = Modifier.size(130.dp),
        painter = painterResource(id = R.drawable.ic_connection_error),
        contentDescription = null
    )
}

@Composable
fun ErrorScreen(
    onButtonClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            ErrorItem()
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .height(32.dp),
                onClick = onButtonClicked,
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.white)
                ),
                border = BorderStroke(1.dp, colorResource(id = R.color.descriptionText))
            ) {
                Text(
                    fontSize = 16.sp,
                    text = "Обновить",
                    color = colorResource(id = R.color.descriptionText)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen({})
}