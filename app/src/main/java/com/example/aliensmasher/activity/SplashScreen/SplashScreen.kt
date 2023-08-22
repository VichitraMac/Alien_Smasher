package com.example.aliensmasher.activity.SplashScreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aliensmasher.R
import com.example.aliensmasher.utils.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) = Column(
    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
) {

    val scale = remember {
        Animatable(0.0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f, animationSpec = tween(1200, easing = {
                OvershootInterpolator(15f).getInterpolation(it)
            })
        )
        delay(1000)
        navController.navigate(Screens.Home) {
            popUpTo(Screens.Splash) {
                inclusive = true
            }
        }
    }
    val columnPadding = 170.dp
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .offset(y = columnPadding)
    ) {
        Text(
            text = "Alien",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
        )
    }
    val columnPadding2 = 20.dp
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .offset(y = -columnPadding2)
    ) {
        Text(
            text = "Smasher",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.h1
        )
    }
    val columnPadding3 = 180.dp
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.green_alien),
            contentDescription = "Your Image Description",
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = columnPadding3)
                .height(400.dp)
        )
    }

//    Text(
//        text = "Alien/nSmasher",
//        textAlign = TextAlign.Center,
//        fontSize = 24.sp,
//        modifier = Modifier
//            .align(Alignment.BottomCenter)
//            .padding(16.dp)
//            .scale(scale.value)
//    )
}