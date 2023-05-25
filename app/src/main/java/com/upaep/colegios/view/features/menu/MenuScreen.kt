package com.upaep.colegios.view.features.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upaep.colegios.R
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.view.base.theme.Text_base_color
import com.upaep.colegios.view.base.theme.Messages_red
import com.upaep.colegios.view.base.theme.roboto_medium
import com.upaep.colegios.viewmodel.features.menu.MenuViewModel

@Composable
fun MenuScreen(navigation: NavHostController, menuViewModel: MenuViewModel = hiltViewModel()) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (back, logo, content) = createRefs()
        BackIcon(modifier = Modifier.constrainAs(back) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, navigation = navigation)
        LogoContainer(modifier = Modifier.constrainAs(logo) {
            top.linkTo(back.bottom)
            bottom.linkTo(content.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        MenuContent(modifier = Modifier
            .constrainAs(content) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            .fillMaxWidth(), menuViewModel = menuViewModel, navigation = navigation)
    }
}

@Composable
fun MenuContent(modifier: Modifier, menuViewModel: MenuViewModel, navigation: NavHostController) {
    Column(modifier = modifier.padding(start = 54.dp)) {
        MenuOptions(content = "Acerca de esta app")
        Spacer(modifier = Modifier.size(32.dp))
        MenuOptions(content = "Aviso de privacidad")
        Spacer(modifier = Modifier.size(32.dp))
        MenuOptions(content = "Reporta un problema")
        Spacer(modifier = Modifier.size(32.dp))
        MenuOptions(content = "Recomienda la app")
        Spacer(modifier = Modifier.size(32.dp))
        MenuOptions(content = "Cerrar sesión", onClick = {
            menuViewModel.closeSession(navigation = navigation)
        })
    }
}

@Composable
fun MenuOptions(content: String, onClick: () -> Unit = {}) {
    Text(
        modifier = Modifier.clickable { onClick() },
        text = content,
        fontFamily = roboto_medium,
        color = Text_base_color,
        fontSize = 18.sp
    )
}

@Composable
fun LogoContainer(modifier: Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.logo_educacion_basica),
        contentDescription = "Colegios upaep logo"
    )
}

@Composable
fun BackIcon(modifier: Modifier, navigation: NavHostController) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 24.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIos,
            contentDescription = "atras",
            tint = Messages_red,
            modifier = Modifier.clickable {
                navigation.popBackStack()
            }
        )
    }
}