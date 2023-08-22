package com.example.aliensmasher.adapter.Aliens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.rememberNavController
import com.example.aliensmasher.R
import com.example.aliensmasher.ui.theme.AlienSmasherTheme
import com.example.aliensmasher.utils.CustomText
import com.example.aliensmasher.utils.Status
import com.example.aliensmasher.viewModel.AlienViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Aliens(
    modifier: Modifier = Modifier.padding(5.dp), viewModel: AlienViewModel, onClickMusic: () -> Unit
) {
    val aliens by viewModel.aliens.collectAsState()
    val score by viewModel.score.collectAsState()
    val timer by viewModel.timerMutableLiveDate.observeAsState()

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (imageView, textView, textView2, adapter, ivGrassRed, ivForgee) = createRefs()
        val (tvLeftTime, tvLeftTimeText, tvScore, tvScoreText) = createRefs()
        Image(
            painter = painterResource(R.drawable.pruple_paint_top),
            contentDescription = "Your Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .constrainAs(imageView) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentScale = ContentScale.FillBounds
        )
        val columnPadding2 = 20.dp
        Box(modifier = Modifier.constrainAs(textView) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            CustomText(
                text = "Start",
                modifier = Modifier.background(Color("#A03933".toColorInt())),
                fontSize = 33.sp
            )
        }
        Box(modifier = Modifier
            .offset(y = -columnPadding2)
            .constrainAs(textView2) {
                top.linkTo(textView.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            CustomText(
                text = "Smashing!!!",
                modifier = Modifier.background(Color("#A03933".toColorInt())),
                fontSize = 33.sp
            )
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(ivGrassRed) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }) {
            Image(
                painter = painterResource(R.drawable.red_grass_big),
                contentDescription = "Your Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                contentScale = ContentScale.FillBounds
            )
        }

        LazyVerticalGrid(columns = GridCells.Fixed(viewModel.columnSize),
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 20.dp)
                .constrainAs(adapter) {
                    top.linkTo(imageView.bottom)
                    bottom.linkTo(ivGrassRed.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

        ) {
            items(aliens.size, key = { index -> aliens[index].id }) { index ->
                val alien = aliens[index]

                Image(painter = painterResource(id = if (alien.isVisible.value) alien.image else R.drawable.green_paint_flash),
                    contentDescription = "",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(top = 5.dp)
                        //.alpha(if (alien.isVisible.value) 1f else 0f)
                        .clickable {
                            if (alien.isVisible.value) {
                                viewModel.onAlienClicked(index)
                            }
                        })
                /////
                /*GlideImage(
                    imageOptions = ImageOptions(requestSize = IntSize(width = 40, height = 40)),
                    modifier = Modifier
                        .padding(10.dp)
                        .alpha(if (alien.isVisible.value) 1f else 0f)
                        .clickable {
                            if (alien.isVisible.value) {
                                viewModel.onAlienClicked(index)
                            }
                        },
                    imageModel = { alien.image }

                )*/
            }
        }

        Row(
            Modifier.fillMaxWidth()
                //.padding(start = 10.dp, bottom = 8.dp)
                .constrainAs(ivForgee) {
                    bottom.linkTo(ivGrassRed.bottom)
                    start.linkTo(ivGrassRed.start)
                    end.linkTo(ivGrassRed.end)
                    top.linkTo(ivGrassRed.top)
                }, verticalAlignment = Alignment.Bottom
        ){
            Column(modifier = modifier.weight(0.4f)) {
                Image(
                    painter = painterResource(id = R.drawable.forgee_rightside),
                    contentDescription = "",
                    modifier = Modifier.size(110.dp)
                )
            }

            val padding2 = 10.dp
            Column(modifier = modifier.weight(0.4f)) {
                CustomText(
                    text = "Score",
                    modifier = Modifier.background(Color("#A03933".toColorInt())).offset(y = padding2),
                    fontSize = 12.sp,
                    MaterialTheme.typography.h2
                )
                CustomText(
                    text = if(score != 0) score.toString() else "0000",
                    modifier = Modifier.background(Color("#A03933".toColorInt())),
                    fontSize = 70.sp,
                    MaterialTheme.typography.h3
                )
            }

            Column(modifier = modifier.weight(0.3f).padding(bottom = 5.dp, start = 1.dp)) {

                CustomText(
                    text = "Time left",
                    modifier = Modifier.background(Color("#A03933".toColorInt())),
                    fontSize = 13.sp,
                    MaterialTheme.typography.h2
                )

                var timerLocal: String = when(timer?.status){

                    Status.SUCCESS -> {
                        "00:00:00"
                    }

                    Status.LOADING -> {
                        timer?.data!!
                    }

                    else -> {
                        "00:00:00"
                    }
                }
                CustomText(
                    text = timerLocal,
                    modifier = Modifier.background(Color("#A03933".toColorInt())),
                    fontSize = 22.sp,
                    MaterialTheme.typography.h3
                )
            }
        }

//        Box(
//            Modifier
//                .padding(start = 30.dp, bottom = 8.dp)
//                .constrainAs(ivForgee) {
//                    bottom.linkTo(parent.bottom)
//                    start.linkTo(parent.start)
//                }) {
//
//        }
//
//        Box(modifier = Modifier
//            .padding(end = 20.dp, bottom = 10.dp)
//            .constrainAs(tvLeftTime) {
//                end.linkTo(parent.end)
//                bottom.linkTo(parent.bottom)
//            }) {
//
//        }
//
//        val padding = 5.dp
//        Box(
//            modifier = Modifier
//                .padding(end = 22.dp)
//                .offset(y = padding)
//                .constrainAs(tvLeftTimeText) {
//                    end.linkTo(tvLeftTime.end)
//                    start.linkTo(tvLeftTime.start)
//                    bottom.linkTo(tvLeftTime.top)
//                }
//        ){
//
//        }
//
//        Box(modifier = Modifier
//            .padding(end = 25.dp, bottom = 5.dp)
//            .constrainAs(tvScore) {
//                end.linkTo(tvLeftTime.start)
//                bottom.linkTo(parent.bottom)
//            }) {
//
//        }
//
//        val padding2 = 5.dp
//        Box(modifier = Modifier
//            .offset(y = padding2)
//            .constrainAs(tvScoreText) {
//                start.linkTo(tvScore.start)
//                bottom.linkTo(tvScore.top)
//            }) {
//
//        }

    }


    /* Column(
         modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
     ) {
         Row(
             modifier = Modifier.fillMaxWidth()
         ) {
             Row(horizontalArrangement = Arrangement.End, modifier = Modifier.weight(2.5f)){
                 Text("Alien Smasher")
             }
             Row(horizontalArrangement = Arrangement.End, modifier = Modifier.weight(1f)) {

                 Box(
                     modifier = Modifier
                         .padding(top = 8.dp)
                         .width(50.dp)
                 ) {
                     ThreeDimensionalLayout(content = {
                         Image(
                             painter = painterResource(id = icon.value),
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
         }

         LazyVerticalGrid(
             columns = GridCells.Fixed(viewModel.columnSize),
         ) {
             items(aliens.size, key = { index -> aliens[index].id }) { index ->
                 val alien = aliens[index]

                 GlideImage(
                     modifier = Modifier
                         .padding(10.dp)
                         .alpha(if (alien.isVisible.value) 1f else 0f)
                         .clickable {
                             if (alien.isVisible.value) {
                                 viewModel.onAlienClicked(index)
                             }
                         },
                     imageModel = { R.drawable.alien_gif },
                     imageOptions = ImageOptions(requestSize = IntSize(width = 140, height = 140))
                 )
             }
         }*/
    //}
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    AlienSmasherTheme {
        val navController = rememberNavController()
        Aliens(viewModel = AlienViewModel()) {

        }
    }
}

