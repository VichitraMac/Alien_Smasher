package com.example.aliensmasher.activity.HomeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.aliensmasher.R
import com.example.aliensmasher.ui.theme.AlienSmasherTheme
import com.example.aliensmasher.ui.theme.component.Perspective
import com.example.aliensmasher.ui.theme.component.ThreeDimensionalLayout
import com.example.aliensmasher.utils.Screens
import com.example.aliensmasher.viewModel.AlienViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(viewModel: AlienViewModel, navController: NavController, onClickMusic: () -> Unit) =
    Surface(
        Modifier.fillMaxSize(),
    ) {

        ConstraintLayout(
            Modifier.fillMaxSize()
        ) {
            val (box1, box2, box3, box4, box5, box6, box7, box8) = createRefs()

            Box(Modifier.constrainAs(box1) {}) {
                Image(
                    painter = painterResource(id = R.drawable.purple_paint_background),
                    contentDescription = ""
                )
            }

            Box(Modifier.constrainAs(box2) {
                    end.linkTo(parent.end)
                }) {
                Image(
                    painter = painterResource(id = R.drawable.purple_three_eye_alien),
                    contentDescription = ""
                )
            }
            val columnPadding = 30.dp
            Box(modifier = Modifier
                .fillMaxWidth()
                .offset(y = -columnPadding)
                .constrainAs(box3) {
                    top.linkTo(box1.bottom)
                }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Alien",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2
                )
            }
            val columnPadding2 = 80.dp
            Box(contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = -columnPadding2)
                    .constrainAs(box4) {
                        top.linkTo(box3.bottom)
                    }) {
                Text(
                    text = "Smasher",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.body2
                )
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(box5) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                Image(
                    painter = painterResource(id = R.drawable.red_grass_background),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Box(
                Modifier
                    .padding(top = 50.dp)
                    .constrainAs(box6) {
                        top.linkTo(box3.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                Image(
                    painter = painterResource(id = R.drawable.red_alien),
                    contentDescription = "",
                    modifier = Modifier.size(115.dp)
                )
            }

            Box(
                Modifier
                    .padding(end = 30.dp, bottom = 8.dp)
                    .constrainAs(box7) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }) {
                Image(
                    painter = painterResource(id = R.drawable.frogee),
                    contentDescription = "",
                    modifier = Modifier.size(120.dp)
                )
            }


            Box(
                modifier = Modifier.width(120.dp)
                    .constrainAs(box8) {
                    top.linkTo(box6.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                ThreeDimensionalLayout(
                    content = {
                    Text(
                        text = "Play now",
                        modifier = Modifier
                            .background(Color("#D14741".toColorInt()))
                            .padding(start = 30.dp, end = 22.dp, top = 8.dp, bottom = 8.dp),
                        style = MaterialTheme.typography.h2,
                        color = Color.White
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
//    var icon = viewModel.musicIconState
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.End
//    ) {
//        Box(
//            modifier = Modifier
//                .padding(top = 10.dp)
//                .width(50.dp)
//        ) {
//            ThreeDimensionalLayout(content = {
//                Image(
//                    painter = painterResource(id = icon.value),
//                    contentDescription = " Music icon",
//                    Modifier
//                        .background(Color.LightGray)
//                        .padding(6.dp)
//                        .clip(RoundedCornerShape(10.dp))
//                )
//            }, onClick = {
//                onClickMusic()
//
//            }, edgeOffset = 5.dp)
//        }
//    }

        /*Column(
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

        }*/
    }


@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    AlienSmasherTheme {
        val navController = rememberNavController()
        HomeScreen(AlienViewModel(), navController) {

        }
    }
}