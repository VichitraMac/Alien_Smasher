package com.example.aliensmasher.activity.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aliensmasher.R
import com.example.aliensmasher.ui.theme.component.ThreeDimensionalLayout
import com.example.aliensmasher.utils.Screens

@Composable
fun HomeScreen(navController: NavController, onClickMusic: () -> Unit) = Surface(
    Modifier.fillMaxSize(),
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .width(50.dp)
        ) {
            ThreeDimensionalLayout(content = {
                Image(
                    painter = painterResource(id = R.drawable.icon_music),
                    contentDescription = " Music icon",
                    Modifier
                        .background(Color.LightGray)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }, onClick = {
                onClickMusic()

            }, edgeOffset = 5.dp)
        }
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.width(100.dp)
        ) {
            ThreeDimensionalLayout(content = {
                Text(
                    text = "Start game",
                    modifier = Modifier
                        .background(Color.LightGray, shape = RoundedCornerShape(2.dp))
                        .padding(10.dp)
                )
            }, onClick = {
                navController.navigate(Screens.Dashboard) {
                    popUpTo(Screens.Home) {
                        inclusive = true
                    }
                }
            })
        }

    }
}