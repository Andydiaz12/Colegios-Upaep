package com.upaep.colegios.view.base.theme

import androidx.compose.ui.graphics.Color
import com.upaep.colegios.R

sealed class ThemeSchema(
    val backgroundColor: Color,
    val textColor: Color,
    val placeholderColor: Color = Color(0xFF52565A),
    val backgroundImage: Int = R.drawable.white_background
) {
    object LIGHT : ThemeSchema(
        backgroundColor = Color.White,
        textColor = Color.Black
    )

    object DARK : ThemeSchema(
        backgroundColor = Color.Black,
        textColor = Color.White
    )
}
