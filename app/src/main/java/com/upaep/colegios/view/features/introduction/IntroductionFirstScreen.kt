package com.upaep.colegios.view.features.introduction

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun IntroductionFirstScreen() {
    ConstraintLayout() {
        val (logoContainer, textContainer, imageContainer, navigationContainer) = createRefs()
        LogoContainer(modifier = Modifier.constrainAs(logoContainer) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        TextContainer(modifier = Modifier.constrainAs(textContainer) {
            top.linkTo(logoContainer.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        ImageContainer(modifier = Modifier.constrainAs(imageContainer) {
            top.linkTo(textContainer.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        NavigationContainer(modifier = Modifier.constrainAs(navigationContainer) {
            top.linkTo(imageContainer.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
    }
}

@Composable
fun LogoContainer(modifier: Modifier) {
    Icon(imageVector = Icons.Filled.Image, contentDescription = "colegios upaep", modifier = Modifier)
}

@Composable
fun TextContainer(modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = "[Nombre], te damos la bienvenida a la aplicación para padres de familia")
        Text(text = "[Nombre], descubre todo lo que puedes hacer con tu aplicación")
    }
}

@Composable
fun ImageContainer(modifier: Modifier) {

}

@Composable
fun NavigationContainer(modifier: Modifier) {

}