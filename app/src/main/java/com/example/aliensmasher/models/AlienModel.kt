package com.example.aliensmasher.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class AlienModel(
    val id: Int,
    val isVisible: MutableState<Boolean> = mutableStateOf(true),
    val image: Int,
    val point: Int
)
