package com.upaep.colegios.view.base.genericComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoadingSpinner() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Asset("Spinner_loader.json"))
    LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
}