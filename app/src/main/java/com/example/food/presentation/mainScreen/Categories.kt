package com.example.food.presentation.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.food.R
import com.example.food.domain.models.Category


@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    text: String,
    onClickCategory: () -> Unit,
    selected: Boolean
) {
    Button(
        modifier = modifier
            .width(88.dp)
            .height(32.dp),
        elevation = ButtonDefaults.filledTonalButtonElevation(
            defaultElevation = 15.dp
        ),
        onClick = onClickCategory,
        shape = RoundedCornerShape(6.dp),
        contentPadding = PaddingValues(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected)
                colorResource(id = R.color.selectedCategory)
            else
                colorResource(id = R.color.white)
        )
    ) {
        Text(
            fontSize = 13.sp,
            text = text,
            color = if (selected)
                colorResource(id = R.color.outlineColor)
            else
                colorResource(id = R.color.inactiveTextButton)
        )
    }
}

@Composable
fun Categories(
    categoryList: List<Category>,
    selectedCategory: Category?,
    modifier: Modifier = Modifier,
    onCategorySelected: (Category) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    ) {
        items(categoryList) { item ->
            CategoryItem(
                text = item.displayName,
                onClickCategory = { onCategorySelected(item) },
                selected = selectedCategory == item
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesPreview() {
    val categoryList = listOf(
        Category(id = 1, name = "italian", displayName = "Italian"),
        Category(id = 2, name = "chinese", displayName = "Chinese"),
        Category(id = 3, name = "german", displayName = "German"),
        Category(id = 4, name = "japanese", displayName = "Japanese"),
        Category(id = 5, name = "mexican", displayName = "Mexican"),
        Category(id = 6, name = "american", displayName = "American")
    )
    Categories(
        categoryList = categoryList,
        selectedCategory = categoryList[1],
        onCategorySelected = {}
    )
}
