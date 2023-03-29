package com.upaep.colegios.view.base.genericComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.upaep.colegios.R
import com.upaep.colegios.view.base.navigation.Routes
import com.upaep.colegios.view.base.theme.Messages_red
import okhttp3.Route

@Composable
fun Header(
    visibleImage: Boolean = true,
    visibleMenu: Boolean = true,
    modifier: Modifier,
    navigation: NavHostController? = null,
    navigationPrev: Boolean = true
) {
    Row(
        modifier = modifier
            .background(Color.White)
            .padding(18.dp)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIos,
                contentDescription = "atrás",
                tint = Messages_red,
                modifier = Modifier.clickable {
                    if (navigationPrev) {
                        navigation?.popBackStack()
                    }
                }
            )
        }
        if (visibleImage) {
            Icon(
                imageVector = Icons.Filled.Image,
                contentDescription = "colegios upaep",
                modifier = Modifier.weight(1f).clickable {
                    navigation?.navigate(Routes.MenuScreen.routes)
                }
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
        if (visibleMenu) {
            Box(modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "menú",
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun _Header() {
    Row(
        modifier = Modifier
            .height(100.dp)
            .background(Color.White)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIos,
                contentDescription = "atrás",
                tint = Messages_red
            )
        }
        Icon(
            imageVector = Icons.Filled.Image,
            contentDescription = "colegios upaep",
            modifier = Modifier.weight(1f)
        )
        Box(modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "menú",
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}