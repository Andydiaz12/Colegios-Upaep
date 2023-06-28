package com.upaep.colegios.view.base.genericComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.upaep.colegios.R
import com.upaep.colegios.model.base.UserPreferences
import com.upaep.colegios.view.base.defaultvalues.DefaultValues
import com.upaep.colegios.view.base.navigation.Routes
import com.upaep.colegios.view.base.theme.Messages_red
import com.upaep.colegios.view.base.theme.Upaep_red
import com.upaep.colegios.view.base.theme.roboto_black

@Composable
fun Header(
    modifier: Modifier = Modifier,
    screenName: String = "",
    visibleName: Boolean = true,
    rightMenuOptions: Boolean = true,
    changeChild: Boolean = true,
    navigation: NavHostController? = null,
    navigationPrev: Boolean = true,
    visibleNameDesc: Boolean = true,
    backScreen: Boolean = true,
    changeChildAction: () -> Unit = {},
    onBackClick: () -> Unit = {},
    optionalClickBack: Boolean = false,
    menuHamburger: Boolean = false
) {
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val childName =
        userPreferences.getSelectedStudent.collectAsState(initial = DefaultValues.initialStudentSelected).value
    val nameBackgroundColor =
        userPreferences.getBaseColor.collectAsState(initial = DefaultValues.initialColor).value

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                if (backScreen) {
                    Image(
                        painter = painterResource(id = R.drawable.icono_atras),
                        contentDescription = "atrás",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                if (optionalClickBack) {
                                    onBackClick()
                                }
                                if (navigationPrev) {
                                    navigation?.popBackStack()
                                }
                            }
                    )
                }
            }
            if (visibleName) {
                Text(
                    screenName,
                    color = Upaep_red,
                    fontFamily = roboto_black,
                    modifier = Modifier.weight(3f),
                    textAlign = TextAlign.Center
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            if (rightMenuOptions) {
                Box(modifier = Modifier.weight(1f)) {
                    if (changeChild) {
                        Image(
                            painter = painterResource(id = R.drawable.icono_cambiar_tutorado),
                            contentDescription = "seleccionar hijo",
                            modifier = Modifier
                                .size(35.dp)
                                .align(Alignment.CenterEnd)
                                .clickable {
                                    changeChildAction()
                                }
                        )
                    } else if (menuHamburger) {
                        Image(painter = painterResource(id = R.drawable.icono_menu),
                            contentDescription = "menú",
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.CenterEnd)
                                .clickable {
                                    navigation?.navigate(Routes.MenuScreen.routes)
                                })
                    }
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        if (visibleNameDesc) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(nameBackgroundColor)
            ) {
                Text(
                    text = "${childName.name} ${childName.paternSurname} ${childName.motherSurname}",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 8.dp),
                    fontFamily = roboto_black,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun _Header() {
    Column() {
        Row(
            modifier = Modifier
                .height(100.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIos,
                    contentDescription = "atrás",
                    tint = Messages_red
                )
            }
            Text(
                "CALIFICACIONES",
                color = Upaep_red,
                fontFamily = roboto_black,
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.Center
            )
            Box(modifier = Modifier.weight(1f)) {
                Icon(
                    painter = painterResource(id = R.drawable.icono_cambiar_tutorado),
                    contentDescription = "seleccionar hijo",
                    modifier = Modifier
                        .size(35.dp)
                        .align(Alignment.CenterEnd),
                    tint = Color.Magenta
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Magenta)
        ) {
            Text(
                text = "RICARDO IVAN SANCHEZ LOPEZ",
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 8.dp),
                fontFamily = roboto_black,
                fontSize = 11.sp
            )
        }
    }
}