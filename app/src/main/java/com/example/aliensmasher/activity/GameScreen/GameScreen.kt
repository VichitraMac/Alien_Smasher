package com.example.aliensmasher.activity.GameScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.aliensmasher.adapter.Aliens.Aliens
import com.example.aliensmasher.utils.Resource
import com.example.aliensmasher.utils.Status
import com.example.aliensmasher.utils.TwoBottomButton
import com.example.aliensmasher.viewModel.AlienViewModel
import kotlinx.coroutines.CoroutineScope

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameScreen(
    viewModel: AlienViewModel,
    state: BottomSheetState,
    scope: CoroutineScope,
    result: (String?) -> Unit,
    onClickMusic: () -> Unit
) {
    val counter by viewModel.timerMutableLiveDate.observeAsState(Resource(Status.LOADING, "", ""))

    Column {
        Aliens(viewModel = viewModel) {
            onClickMusic()
        }
        TwoBottomButton(counter, state, scope, result, viewModel)
    }
}