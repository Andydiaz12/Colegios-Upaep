package com.upaep.colegios.view.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.R
import com.upaep.colegios.view.base.genericComponents.RedButton
import com.upaep.colegios.view.base.genericComponents.ShowPasswordIcon
import com.upaep.colegios.view.base.navigation.Routes
import com.upaep.colegios.view.base.theme.*
import com.upaep.colegios.viewmodel.features.login.LoginViewModel

@Composable
fun LoginScreen(
    theme: ThemeSchema,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigation: NavHostController
) {
    val visiblePassword: Boolean by loginViewModel.visiblePassword.observeAsState(false)
    val mailInput: String by loginViewModel.mailInput.observeAsState(initial = "")
    val passwordInput: String by loginViewModel.passwordInput.observeAsState(initial = "")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_educacion_basica),
            contentDescription = "colegios upaep",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(250.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputsContainer(
                visiblePassword,
                changePasswordVisibility = {
                    loginViewModel.changePasswordVisibility()
                },
                mailInput = mailInput,
                passwordInput = passwordInput,
                updateData = { data, input -> loginViewModel.updateDate(data, input) },
            )
            ForgotPassword(theme = theme, navigation = navigation)
        }
        ButtonContainer(
            modifier = Modifier.align(Alignment.BottomCenter),
            loginViewModel = loginViewModel,
            navigation = navigation
        )
    }
}

@Composable
fun ForgotPassword(theme: ThemeSchema, navigation: NavHostController) {
    Spacer(modifier = Modifier.size(20.dp))
    Text(
        "¿Olvidaste tu contraseña?",
        fontFamily = roboto_regular,
        fontSize = 12.sp,
        color = theme.textColor,
        modifier = Modifier.clickable { navigation.navigate(Routes.LoginExtraScreen.createRoute("forgotPassword")) }
    )
}

@Composable
fun ButtonContainer(
    modifier: Modifier,
    loginViewModel: LoginViewModel,
    navigation: NavHostController
) {
    Column(modifier = modifier) {
        RedButton("ENTRAR") {
            navigation.navigate(Routes.OnBoardScreen.routes)
        }
        LoginButtonsSeparation()
        GoogleButton(onButtonClicked = {
            navigation.navigate(Routes.LoginExtraScreen.createRoute("blockedAccount"))
        })
    }
}

@Composable
fun LoginButtonsSeparation() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        Divider(color = Color(0xFFE4E4E4), modifier = Modifier.weight(1f))
        Text(
            text = "Ó",
            color = Placeholders,
            modifier = Modifier.padding(horizontal = 10.dp),
            fontFamily = roboto_black
        )
        Divider(color = Color(0xFFE4E4E4), modifier = Modifier.weight(1f))
    }
}

@Composable
fun GoogleButton(onButtonClicked: () -> Unit) {
    Button(
        onClick = { onButtonClicked() },
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color(0xFFAEAEAE)
        ),
        shape = RoundedCornerShape(50)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_google),
                contentDescription = "google",
                modifier = Modifier
                    .padding(start = 20.dp, end = 16.dp)
                    .size(24.dp)
            )
            Text(
                text = "Entrar con mi cuenta de google",
                fontFamily = roboto_medium,
                fontSize = 13.sp,
                letterSpacing = 0.sp
            )
        }
    }
}

@Composable
fun InputsContainer(
    visiblePassword: Boolean,
    changePasswordVisibility: () -> Unit,
    mailInput: String,
    passwordInput: String,
    updateData: (String, String) -> Unit
) {
    Column {
        Input(
            placeHolder = "INGRESA TU CORREO ELECTRÓNICO",
            leadingIcon = painterResource(id = R.drawable.icono_usuario_login),
            contentDescritpion = "Usuario",
            value = mailInput,
            onValueChange = { updateData(it, "mail") }
        )
        Spacer(modifier = Modifier.size(26.dp))
        Input(
            placeHolder = "CONTRASEÑA",
            leadingIcon = painterResource(id = R.drawable.icono_candado),
            contentDescritpion = "Contraseña",
            value = passwordInput,
            onValueChange = { updateData(it, "password") },
            trailingIcon = {
                ShowPasswordIcon(passwordVisibility = visiblePassword) {
                    changePasswordVisibility()
                }
            },
            visualTransformation = if (visiblePassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        )
    }
}

@Composable
fun Input(
    placeHolder: String,
    leadingIcon: Painter,
    contentDescritpion: String,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        shape = RoundedCornerShape(5.dp),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        value = value,
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            placeholderColor = Color.Gray,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray
        ),
        placeholder = {
            Text(
                text = placeHolder,
                fontFamily = roboto_black,
                fontSize = 14.sp,
                color = Placeholders
            )
        },
        leadingIcon = {
            Image(
                painter = leadingIcon,
                contentDescription = contentDescritpion,
                modifier = Modifier.size(32.dp)
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation
    )
}