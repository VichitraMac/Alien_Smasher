package com.example.aliensmasher.adapter.Aliens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.aliensmasher.R

import com.example.aliensmasher.ui.theme.component.ThreeDimensionalLayout
import com.example.aliensmasher.utils.Text
import com.example.aliensmasher.viewModel.AlienViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Aliens(
    modifier: Modifier = Modifier.padding(5.dp), viewModel: AlienViewModel, onClickMusic: () -> Unit
) {
    var count = remember {
        0
    }
    var image = remember {
        mutableStateOf(R.drawable.icon_music)
    }
    val aliens by viewModel.aliens.collectAsState()
    Column(
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
                            painter = painterResource(id = image.value),
                            contentDescription = " Music icon",
                            Modifier
                                .background(Color.LightGray)
                                .padding(6.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                    }, onClick = {
                        if (count == 2){
                            count = 0
                            image.value = R.drawable.icon_stop_music
                        }else{
                            count++
                            image.value = R.drawable.icon_music
                        }
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
        }
    }
}