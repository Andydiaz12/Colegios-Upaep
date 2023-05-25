package com.upaep.colegios.view.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.R
import com.upaep.colegios.view.base.theme.Upaep_red
import com.upaep.colegios.viewmodel.features.splash.SplashViewModel

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    navigation: NavHostController
) {
    splashViewModel.navigation = navigation
    Image(
        modifier = Modifier
            .fillMaxSize()
            .background(Upaep_red),
        painter = painterResource(id = R.drawable.icono_splash),
        contentDescription = "upaep"
    )
}