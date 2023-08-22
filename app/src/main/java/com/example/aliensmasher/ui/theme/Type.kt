package com.example.aliensmasher.ui.theme

import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.aliensmasher.R

// Set of Material typography styles to start with
val customFont = FontFamily(
    Font(R.font.gloria_hallelujah_regular)
)
val customFont2 = FontFamily(
    Font(R.font.digital_7)
)
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = customFont,
        fontWeight = FontWeight.Medium,
        fontSize = 55.sp,
    ),
    h1 = TextStyle(
        fontFamily = customFont,
        fontWeight = FontWeight.Medium,
        fontSize = 60.sp,
    ),
    h2 =  TextStyle(
        fontFamily = customFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    h3 = TextStyle(
        fontFamily = customFont2,
        fontWeight = FontWeight.Normal,
    ),
    h4 = TextStyle(
        fontFamily = customFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    h5 = TextStyle(
        fontFamily = customFont,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    )


    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

