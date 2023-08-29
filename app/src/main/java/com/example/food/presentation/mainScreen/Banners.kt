package com.example.food.presentation.mainScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.food.FoodBottomMenuItem

@Composable
fun BannerItem(
    currentPage: Int,
    listOfScreens: List<@Composable () -> Unit>
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        content = listOfScreens[currentPage]
    )
}

@Composable
fun BannerScreen(
    navController: NavController,
    bannersList: List<@Composable () -> Unit>,
    steps: Int,
    index: Int
) {
    val isPressed = remember { mutableStateOf(false) }
    val currentStep = rememberSaveable { mutableStateOf(0) }

    val goToNextScreen: () -> Unit = {
        if (index + 1 < bannersList.size) {
            navController.navigate("banners/$steps/${index + 1}")
            currentStep.value++
        } else {
            navController.popBackStack(FoodBottomMenuItem.Menu.route, false)
        }
    }
    val goToPreviousScreen = {
        if (index > 0 && index!=currentStep.value) {
            navController.navigate("banners/$steps/${index - 1}")
        }
        else if (index > 0) {
            navController.popBackStack()
        }
        currentStep.value--
    }

    BackHandler(
        enabled = true
    ) {
        navController.popBackStack(FoodBottomMenuItem.Menu.route, false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                val maxWidth = this.size.width
                detectTapGestures(
                    onPress = {
                        val pressStartTime = System.currentTimeMillis()
                        isPressed.value = true
                        this.tryAwaitRelease()
                        val pressEndTime = System.currentTimeMillis()
                        val totalPressTime = pressEndTime - pressStartTime
                        if (totalPressTime < 200) {
                            val isTapOnRightThreeQuarters = (it.x > (maxWidth / 4))
                            if (isTapOnRightThreeQuarters) {
                                goToNextScreen()
                            } else {
                                goToPreviousScreen()
                            }
                        }
                        isPressed.value = false
                    },
                )
            }
    ) {
        BannerItem(
                currentPage = index,
                listOfScreens = bannersList
            )
        BannerProgressBar(steps, index, isPressed.value, goToNextScreen)
    }
    }


@Preview
@Composable
fun BannerScreenPreview() {
    val navController = rememberNavController()
    BannerScreen(
        navController = navController,
        bannersList = emptyList(),
        steps = 2,
        index = 1
    )
}