package com.upaep.colegios.view.base.genericComponents

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ShowPasswordIcon(
    passwordVisibility: Boolean,
    changePasswordVisibility: () -> Unit
) {
    val imagen = if (passwordVisibility) {
        Icons.Filled.VisibilityOff
    } else {
        Icons.Filled.Visibility
    }
    IconButton(onClick = { changePasswordVisibility() }) {
        Icon(
            imageVector = imagen,
            contentDescription = "Mostrar contraseña",
            tint = Color.Gray
        )
    }
}