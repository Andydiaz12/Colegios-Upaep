package com.upaep.colegios.view.base.genericComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upaep.colegios.view.base.theme.Messages_red

@Preview(showSystemUi = true)
@Composable
fun HeaderRightTwoIcons() {
    val visibleMenu = true;
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(18.dp)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIos,
                contentDescription = "atrás",
                tint = Messages_red,
                modifier = Modifier.clickable {
                    /*if (navigationPrev) {
                        navigation?.popBackStack()
                    }*/
                }
            )
        }

        if (visibleMenu) {
            Box(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                    if(false) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "notificaciones"
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                    }
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "menú",
                        tint = Messages_red
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}