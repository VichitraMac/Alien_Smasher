package com.example.aliensmasher.activity

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aliensmasher.R
import com.example.aliensmasher.activity.GameScreen.GameScreen
import com.example.aliensmasher.activity.HomeScreen.HomeScreen
import com.example.aliensmasher.activity.SplashScreen.SplashScreen
import com.example.aliensmasher.ui.theme.AlienSmasherTheme
import com.example.aliensmasher.ui.theme.component.Perspective
import com.example.aliensmasher.ui.theme.component.ThreeDimensionalLayout
import com.example.aliensmasher.utils.Screens
import com.example.aliensmasher.viewModel.AlienViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<AlienViewModel>()
    var mMediaPlayer: MediaPlayer? = null
    private var audioResourceId = arrayListOf(R.raw.sound_killer, R.raw.sound_killer3, 0)
    var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlienSmasherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    initAudio(LocalContext.current)
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screens.Splash) {
                        composable(route = Screens.Splash) {
                            // 1 first screen
                            SplashScreen(navController = navController)
                        }
                        composable(route = Screens.Home) {
                            HomeScreen(viewModel, navController = navController) {
                                handleNextSong()
                            }
                        }
                        composable(route = Screens.Dashboard) {
                            BottomDialog(viewModel, navController = navController) {
                                handleNextSong()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //handleMediaPlayerStateChange()
    }

    override fun onPause() {
        super.onPause()
        //handleMediaPlayerStateChange()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer?.release()
        mMediaPlayer?.stop()
    }

    private fun handleNextSong() {
        mMediaPlayer?.release()
        if (index < audioResourceId.size - 1) index++;
        else index = 0;

        if (index == audioResourceId.size - 1) {
            mMediaPlayer?.release()
            viewModel.changeMusicIcon(R.drawable.icon_stop_music)
        } else {
            mMediaPlayer = MediaPlayer.create(this@MainActivity, audioResourceId[index])
            mMediaPlayer?.start()
            viewModel.changeMusicIcon(R.drawable.icon_music)
        }
    }

    private fun initAudio(context: Context) {
        if (index < audioResourceId.size) index++;
        else index = 0;
        mMediaPlayer = MediaPlayer.create(context, audioResourceId[index])
        //mMediaPlayer?.start()
        mMediaPlayer?.setOnCompletionListener {
            mMediaPlayer = MediaPlayer.create(context, audioResourceId[index])
            mMediaPlayer?.start()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    AlienSmasherTheme {
        val navController = rememberNavController()
        BottomDialog(viewModel = AlienViewModel(), navController) {

        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomDialog(
    viewModel: AlienViewModel, navController: NavController, onClickMusic: () -> Unit
) {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val sheetStateUp = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )

    val scaffoldStateUp = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetStateUp
    )

    var result = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetGesturesEnabled = false,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White)
                ) {
                    Text(
                        text = "AD",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        style = MaterialTheme.typography.h4,
                        color = Color.Black
                    )

                }
                Column(modifier = Modifier.weight(1.2f)) {
                    ConstraintLayout(Modifier.fillMaxSize()) {
                        val (image, btnRetry, btnHome, text1, text2, bottomParent) = createRefs()
                        val (ivForgee) = createRefs()
                        Image(
                            painter = painterResource(R.drawable.bottom_sheet_daily_bachground),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize(1f)
                                .constrainAs(image) {},
                            contentScale = ContentScale.FillBounds
                        )
                        val columnPadding = 10.dp
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = columnPadding)
                            .constrainAs(text1) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(btnRetry.top)
                            }) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Round",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h5,
                                color = Color.White
                            )
                        }

                        val columnPadding2 = 30.dp
                        Box(contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = -columnPadding2)
                                .constrainAs(text2) {
                                    top.linkTo(text1.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }) {
                            Text(
                                text = "Lost!",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body2,
                                color = Color.White
                            )
                        }

                        Box(
                            Modifier
                                .padding(bottom = 20.dp)
                                .constrainAs(ivForgee)
                                {
                                    start.linkTo(image.start)
                                    bottom.linkTo(image.bottom)
                                }) {
                            Image(
                                painter = painterResource(id = R.drawable.forgee_action2),
                                contentDescription = "",
                                modifier = Modifier.size(160.dp)
                            )
                        }

                        Box(
                            Modifier
                                .width(240.dp)
                                .padding(bottom = 70.dp)
                                .constrainAs(btnRetry) {
                                    end.linkTo(image.end)
                                    bottom.linkTo(image.bottom)
                                }){
                            Row{
                                ThreeDimensionalLayout(Perspective.Left(Color("#8D6D05".toColorInt()), Color("#FEC005".toColorInt())),content = {
                                    Text(
                                        text = "Retry",
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.h5,
                                        color = Color.White,
                                        modifier = Modifier
                                            .background(
                                                Color("#FEC005".toColorInt()),
                                                shape = RoundedCornerShape(2.dp)
                                            )
                                            .padding(
                                                start = 45.dp,
                                                end = 45.dp,
                                                top = 6.dp,
                                                bottom = 6.dp
                                            ),
                                    )
                                }, onClick = {
                                    scope.launch {
                                        sheetState.collapse()
                                        viewModel.restart()
                                    }
                                })
                                ThreeDimensionalLayout(Perspective.Left(Color("#DAC387".toColorInt()), Color("#A29566".toColorInt())), content = {
                                    Image(
                                        painter = painterResource(id = R.drawable.house_icon),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(52.dp)
                                            .background(Color("#F5DD94".toColorInt()))
                                            .padding(
                                                start = 14.dp,
                                                end = 14.dp,
                                                bottom = 5.dp,
                                                top = 5.dp
                                            ))
                                }, onClick = {
                                    navController.navigate(Screens.Home) {
                                        popUpTo(Screens.Dashboard) {
                                            inclusive = true
                                        }
                                    }
                                })
                            }
                        }
//                        Box(Modifier.width(50.dp).padding(bottom = 30.dp, end = 15.dp).constrainAs(btnHome){
//                            end.linkTo(image.end)
//                            bottom.linkTo(image.bottom)
//                        }){
//
//                        }

                    }


//                    Column(modifier = Modifier.weight(1f)) {
//
//                    }

//                    Row(
//                        Modifier
//                            .weight(1f)
//                            .fillMaxSize(), horizontalArrangement = Arrangement.End,
//                        verticalAlignment = Alignment.CenterVertically) {
//                        Column(Modifier.weight(1f)) {
//
//                        }
//                        Row(Modifier
//                                .weight(1.7f)
//                                .fillMaxSize(), horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically) {
//
//                        }
//
//                    }


//                Text(
//                    text = "You ${result.value}", style = MaterialTheme.typography.h2
//                )
                    /*TwoBottomButton2D(
                        sheetState = sheetState,
                        viewModel = viewModel,
                        scope = scope,
                        navController = navController
                    )*/
                }
            }
        },
        sheetBackgroundColor = Color.Green,
        sheetPeekHeight = 0.dp,
    ) {
        GameScreen(viewModel, sheetState, scope, result = { result.value = it ?: "" }) {
            onClickMusic()
        }
    }
}










