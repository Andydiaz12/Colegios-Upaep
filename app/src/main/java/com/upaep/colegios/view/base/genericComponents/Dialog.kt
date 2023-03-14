package com.upaep.colegios.view.base.genericComponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun DialogGeneric(
    visible: Boolean = false,
    extraContent: @Composable() () -> Unit = {},
    visibleExtraContent: Boolean = false
) {
    if (visible) {
        Dialog(onDismissRequest = { }) {
            Card() {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "CONTRASEÑA INCORRECTA",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "La contraseña que ingresaste es incorrecta.\nInténtalo de nuevo.",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (visibleExtraContent) {
                        Spacer(modifier = Modifier.size(16.dp))
                        extraContent()
                    }
                }
            }
        }
    }
}

@Composable
fun TestExtraData() {
    Button1()
    Button1()
}

@Composable
fun Button1() {
    OutlinedButton(onClick = { }) {
        Text("OLVIDÉ MI CONTRASEÑA")
    }
}