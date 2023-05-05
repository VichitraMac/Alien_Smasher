package com.example.aliensmasher.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aliensmasher.R
import com.example.aliensmasher.activity.GameScreen.GameScreen
import com.example.aliensmasher.activity.HomeScreen.HomeScreen
import com.example.aliensmasher.activity.SplashScreen.SplashScreen
import com.example.aliensmasher.ui.theme.AlienSmasherTheme
import com.example.aliensmasher.ui.theme.component.ThreeDimensionalLayout
import com.example.aliensmasher.utils.Resource
import com.example.aliensmasher.utils.Screens
import com.example.aliensmasher.utils.Status
import com.example.aliensmasher.utils.TwoBottomButton2D
import com.example.aliensmasher.viewModel.AlienViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<AlienViewModel>()
    var mMediaPlayer: MediaPlayer? = null
    var mediaState = false
    private var isFirstTime = true
    private var audioResourceId = arrayListOf(R.raw.sound_killer, R.raw.sound_killer3, 0)
    var isCalledAlready: Boolean = false
    var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlienSmasherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    if(!isCalledAlready){
                        isCalledAlready = true
                        initAudio(LocalContext.current)
                    }
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screens.Splash) {
                        composable(route = Screens.Splash) {
                            SplashScreen(navController = navController)
                        }

                        composable(route = Screens.Dashboard) {
                            BottomDialog(viewModel, navController = navController){
                                handleNextSong()
                            }
                        }

                        composable(route = Screens.Home) {
                            HomeScreen(navController = navController){
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
        if(index<audioResourceId.size-1)
            index++;
        else
            index=0;

        if (index == audioResourceId.size-1){
            mMediaPlayer?.release()
        }else{
            mMediaPlayer = MediaPlayer.create(this@MainActivity, audioResourceId[index])
            mMediaPlayer?.start()
        }
    }

    private fun initAudio(context: Context){
        if(index<audioResourceId.size)
            index++;
        else
            index=0;
        mMediaPlayer = MediaPlayer.create(context, audioResourceId[index])
        mMediaPlayer?.start()
        mMediaPlayer?.setOnCompletionListener {
            mMediaPlayer = MediaPlayer.create(context, audioResourceId[index])
            mMediaPlayer?.start()
        }
    }
}

@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    AlienSmasherTheme {
        val navController = rememberNavController()
        HomeScreen(navController = navController){
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
    var result = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetGesturesEnabled = false,
        sheetContent = {
            Column(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxSize()
                    .background(color = Color.Gray)
                    .padding(start = 10.dp, end = 20.dp, bottom = 12.dp, top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You ${result.value}", style = MaterialTheme.typography.body2
                )
                TwoBottomButton2D(
                    sheetState = sheetState,
                    viewModel = viewModel,
                    scope = scope,
                    navController = navController)
            }
        },
        sheetBackgroundColor = Color.Green,
        sheetPeekHeight = 0.dp,
    ) {
        GameScreen(viewModel, sheetState, scope, result = {result.value = it ?: ""}) {
            onClickMusic()
        }
    }
}










