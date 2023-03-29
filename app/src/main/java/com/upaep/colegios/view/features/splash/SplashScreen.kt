package com.upaep.colegios.view.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.upaep.colegios.R
import com.upaep.colegios.view.base.theme.ThemeSchema

@Preview
@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = ThemeSchema.LIGHT.backgroundImage),
                contentScale = ContentScale.FillWidth
            )
    ) {
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.logo_educacion_basica),
            contentDescription = "upaep"
        )
    }
}