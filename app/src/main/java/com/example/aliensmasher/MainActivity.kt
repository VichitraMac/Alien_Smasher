package com.example.aliensmasher

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aliensmasher.ui.theme.AlienSmasherTheme
import com.example.aliensmasher.ui.theme.component.ThreeDimensionalLayout
import com.example.aliensmasher.utils.Resource
import com.example.aliensmasher.utils.Status
import com.example.aliensmasher.viewModel.AlienViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<AlienViewModel>()
    var mMediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlienSmasherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    mMediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.sound_killer)
                    mMediaPlayer?.start()
                    BottomDialog(viewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer?.release()
        mMediaPlayer?.stop()
    }
}

@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    AlienSmasherTheme {
        BottomDialog(viewModel = AlienViewModel())
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage(
    viewModel: AlienViewModel,
    state: BottomSheetState,
    scope: CoroutineScope,
    result: (String?) -> Unit
) {
    val counter by viewModel.timerMutableLiveDate.observeAsState(Resource(Status.LOADING, "", ""))

    Column {
        Aliens(viewModel = viewModel)
        Row(
            verticalAlignment = Alignment.Bottom, modifier = Modifier.fillMaxSize()
        ) {
            val timer = if (counter.status == Status.LOADING) {
                counter.data
            } else {
                result(counter.message)
                scope.launch { state.expand() }
                "0"
            }
            Text(
                text = "Timer $timer",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .background(Color.Black, shape = RoundedCornerShape(2.dp))
                    .padding(10.dp),
                style = MaterialTheme.typography.body2,
                color = Color.White
            )

            Text(text = "Score ${
                if (viewModel.score == viewModel.itemCount) {
                    result("Won")
                    scope.launch { state.expand() }
                    viewModel.score
                } else {
                    viewModel.score
                }
            }",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .background(Color.Black, shape = RoundedCornerShape(2.dp))
                    .padding(10.dp),
                style = MaterialTheme.typography.body2,
                color = Color.White)
        }

    }
}


@Composable
fun Aliens(
    modifier: Modifier = Modifier.padding(5.dp), viewModel: AlienViewModel
) {
    val aliens by viewModel.aliens.collectAsState()
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(
//            text = "Alien Smasher",
//            modifier = Modifier
//                .background(Color.Green, shape = RoundedCornerShape(8.dp))
//                .padding(16.dp)
//        )
        GradientText("Alien Smasher")

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
        }
    }
}

@Composable
fun GradientText(text: String) {
    Text(
        fontSize = 18.sp, text = (text),

//        style = TextStyle(
//            brush = Brush.linearGradient(
//                colors = listOf(Color.Magenta, Color.Cyan)
//            ),
//        ),
        modifier = Modifier
            .background(Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(16.dp), style = MaterialTheme.typography.body2, color = Color.White
    )
}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomDialog(viewModel: AlienViewModel) {
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

//                Text(
//                    modifier = Modifier.padding(top = 20.dp),
//                    text = "You ${viewModel.score}",
//                    style = MaterialTheme.typography.body2,
//                    color = Color.White
//                )

                Row(
                    modifier = Modifier
                        .fillMaxHeight(1f)
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    /*Button(
                        {

                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                            .background(Color.Green, shape = RoundedCornerShape(2.dp))
                            .padding(10.dp)
                    ) {

                    }*/

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        ThreeDimensionalLayout(content = {
                            Text(
                                text = "Retry",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body2,
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.LightGray, shape = RoundedCornerShape(2.dp))
                                    .padding(10.dp)
                            )
                        }, onClick = {
                            scope.launch {
                                sheetState.collapse()
                                viewModel.restart()
                            }
                        })
                    }


                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        ThreeDimensionalLayout(content = {
                            Text(
                                text = "Done",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body2,
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.LightGray, shape = RoundedCornerShape(2.dp))
                                    .padding(10.dp)
                            )
                        }, onClick = {})
                    }


//                    Button(
//                        {},
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(16.dp)
//                            .background(Color.Green, shape = RoundedCornerShape(2.dp))
//                            .padding(10.dp)
//                    ) {
//                        Text(
//                            text = "Done",
//                            textAlign = TextAlign.Center,
//                            style = MaterialTheme.typography.body2,
//                            color = Color.White
//                        )
//                    }

                }
            }
        },
        sheetBackgroundColor = Color.Green,
        sheetPeekHeight = 0.dp,
    ) {
        HomePage(viewModel, sheetState, scope) {
            result.value = it ?: ""
        }


    }
}









