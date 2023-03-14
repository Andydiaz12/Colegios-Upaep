package com.upaep.colegios.view.base.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.upaep.colegios.R

// Set of Material typography styles to start with
val roboto_regular = FontFamily(Font(R.font.roboto_regular))
val roboto_medium = FontFamily(Font(R.font.roboto_medium))
val roboto_black = FontFamily(Font(R.font.roboto_black))
val firasans_bold = FontFamily(Font(R.font.firasans_bold))

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
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