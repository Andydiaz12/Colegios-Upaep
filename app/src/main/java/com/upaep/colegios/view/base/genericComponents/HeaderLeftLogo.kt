package com.upaep.colegios.view.base.genericComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.upaep.colegios.R
import com.upaep.colegios.view.base.theme.Messages_red

@Composable
fun HeaderLeftLogo(modifier: Modifier) {
    ConstraintLayout(modifier = modifier
        .fillMaxWidth()
        .height(75.dp)) {
        val (logo, icons) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.logo_educacion_basica),
            contentDescription = "home",
            modifier = Modifier
                .constrainAs(logo) {
                    start.linkTo(parent.start)
                }
                .size(150.dp)
        )
        IconsContainer(modifier = Modifier.constrainAs(icons) {
            end.linkTo(parent.end)
        })
    }
}

@Composable
fun IconsContainer(modifier: Modifier) {
    Row(modifier = modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "notificaciones"
        )
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "menu",
            tint = Messages_red
        )
    }
}