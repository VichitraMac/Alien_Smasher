package com.example.aliensmasher.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aliensmasher.ui.theme.component.ThreeDimensionalLayout
import com.example.aliensmasher.viewModel.AlienViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Text(text: String) {
    Text(
        fontSize = 18.sp,
        text = (text),
        modifier = Modifier
            .background(Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        style = MaterialTheme.typography.body2,
        color = Color.White
    )
}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TwoBottomButton(
    counter: Resource<String>,
    state: BottomSheetState,
    scope: CoroutineScope,
    result: (String?) -> Unit,
    viewModel: AlienViewModel
) {
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TwoBottomButton2D(
    sheetState: BottomSheetState,
    scope: CoroutineScope,
    viewModel: AlienViewModel,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxHeight(1f)
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            ThreeDimensionalLayout(content = {
                Text(
                    text = "Retry",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2,
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
                    text = "Home",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray, shape = RoundedCornerShape(2.dp))
                        .padding(10.dp)
                )
            }, onClick = {
                navController.navigate(Screens.Home) {
                    popUpTo(Screens.Dashboard) {
                        inclusive = true
                    }
                }
            })
        }
    }

}


