package com.example.food.presentation.commonComposables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.food.R

@Composable
fun StandardButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    @StringRes text: Int,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.outlineColor)
        ),
        enabled = enabled,
        modifier = modifier
            .padding(16.dp)
            .height(60.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = stringResource(id = text),
            color = colorResource(id = R.color.white),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(start = 4.dp)
                .size(20.dp, 20.dp)
        )
    }
}