package com.example.food.presentation.commonComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.food.R

@Composable
fun Counter (
    modifier: Modifier = Modifier,
    onQuantityChanged: (Boolean) -> Unit,
    quantity: Int,
    scale: Float,
    enabledQuantity: Boolean
){
    Box(
        modifier = modifier
            .scale(scale)
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
            IconButton(
                enabled = enabledQuantity,
                onClick = { onQuantityChanged(false) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = "",
                    tint = Color.White
                )
            }

            Text(
                text = "$quantity",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            IconButton(
                enabled = quantity < 10,
                onClick = { onQuantityChanged(true) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "",
                    tint = Color.White,
                )
            }
        }
    }
}