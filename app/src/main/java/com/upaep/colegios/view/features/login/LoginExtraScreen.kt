package com.upaep.colegios.view.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.R
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.genericComponents.RedButton
import com.upaep.colegios.view.base.theme.*
import com.upaep.colegios.viewmodel.features.login.LoginExtraViewModel

@Composable
fun LoginExtraScreen(
    theme: ThemeSchema,
    navigation: NavHostController,
    LoginExtraViewModel: LoginExtraViewModel = hiltViewModel(),
    toScreen: String
) {
    val blockedScreen: Boolean = LoginExtraViewModel.getScreen(toScreen)
    val titleText: Int by LoginExtraViewModel.titleText.observeAsState(LoginExtraViewModel.getTitleText())
    val contentText: Int by LoginExtraViewModel.contentText.observeAsState(LoginExtraViewModel.getContentText())
    val mail: String by LoginExtraViewModel.email.observeAsState("")
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (header, imageContainer, informationContainer, mailInput, footer) = createRefs()
        val middleGuideLine = createGuidelineFromTop(if (blockedScreen) 0.75f else 0.5f)
        Header(modifier = Modifier.constrainAs(header) {
            top.linkTo(parent.top)
        }, visibleMenu = false, visibleImage = false, navigation = navigation)
        if (blockedScreen) {
            ImageContainer(modifier = Modifier.constrainAs(imageContainer) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(informationContainer.top)
            })
        }
        InformationContainer(modifier = Modifier.constrainAs(informationContainer) {
            if (blockedScreen) {
                top.linkTo(imageContainer.bottom)
            }
            bottom.linkTo(mailInput.top)
        }, titleText = titleText, contentText = contentText)
        if (!blockedScreen) {
            MailInput(modifier = Modifier.constrainAs(mailInput) {
                bottom.linkTo(middleGuideLine)
            }, theme = theme, onValueChange = {
                LoginExtraViewModel.updateMail(it)
            }, mail = mail)
        }
        FooterContainer(modifier = Modifier.constrainAs(footer) {
            bottom.linkTo(parent.bottom)
        })
    }
}

@Composable
fun ImageContainer(modifier: Modifier) {
    Image(
        modifier = modifier.size(150.dp),
        painter = painterResource(id = R.drawable.icono_cuenta_bloqueada),
        contentDescription = "cuenta bloqueada"
    )
}

@Composable
fun MailInput(
    modifier: Modifier,
    theme: ThemeSchema,
    onValueChange: (String) -> Unit,
    mail: String
) {
    OutlinedTextField(
        value = mail,
        onValueChange = { onValueChange(it) },
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "INGRESA TU CORREO ELECTRÓNICO",
                fontSize = 15.sp,
                color = theme.placeholderColor
            )
        },
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color(0xFF52565A),
            focusedBorderColor = Input_border,
            unfocusedBorderColor = Input_border
        ),
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.icono_correo),
                contentDescription = "correo",
                Modifier
                    .padding(start = 8.dp)
                    .size(28.dp)
            )
        },
    )
}

@Composable
fun FooterContainer(modifier: Modifier) {
    Box(modifier = modifier) {
        RedButton(text = "RECUPERAR CONTRASEÑA") {

        }
    }
}

@Composable
fun InformationContainer(modifier: Modifier, titleText: Int, contentText: Int) {
    Column(modifier = modifier) {
        TextContent(
            stringResource(id = titleText),
            bold = true,
            textColor = Messages_red,
            fontFamily = firasans_bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(12.dp))
        TextContent(
            stringResource(id = contentText),
            textColor = Color(0xFF707070),
            fontFamily = roboto_regular
        )
        Spacer(modifier = Modifier.size(28.dp))
    }
}


@Composable
fun TextContent(
    content: String,
    bold: Boolean = false,
    textColor: Color,
    fontFamily: FontFamily,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = content,
        textAlign = TextAlign.Center,
        fontWeight = if (bold) FontWeight.Bold else null,
        color = textColor,
        fontFamily = fontFamily,
        fontSize = fontSize
    )
}