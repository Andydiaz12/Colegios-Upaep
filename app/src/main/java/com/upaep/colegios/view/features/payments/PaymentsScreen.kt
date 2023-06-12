package com.upaep.colegios.view.features.payments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.R
import com.upaep.colegios.model.entities.payments.PaymentDescription
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import com.upaep.colegios.view.base.genericComponents.ChildSelectorModal
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.navigation.Routes
import com.upaep.colegios.view.base.theme.Background
import com.upaep.colegios.view.base.theme.Dark_grey
import com.upaep.colegios.view.base.theme.Tenue_gray
import com.upaep.colegios.view.base.theme.Text_base_color
import com.upaep.colegios.view.base.theme.Upaep_red
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_medium
import com.upaep.colegios.view.base.theme.roboto_regular
import com.upaep.colegios.viewmodel.base.genericComponents.ChildDataViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PaymentsScreen(
    childDataViewModel: ChildDataViewModel = hiltViewModel(),
    navigation: NavHostController
) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var controlDisplayButtonModal by rememberSaveable { mutableStateOf(1) }
    var visibleScreen by rememberSaveable { mutableStateOf("OutstandingBalance") }
    ModalBottomSheetLayout(
        sheetState = state,
        scrimColor = Color.Black.copy(alpha = 0.6f),
        sheetContent = {
            ModalBottomSheetContent(
                controlDisplayButtonModal = controlDisplayButtonModal,
                childSelectorClick = { student, color ->
                    childDataViewModel.changeSelectedStudent(student, color)
                    scope.launch { state.hide() }
                }
            )
        }
    ) {
        ConstraintLayout() {
            val (header, content) = createRefs()
            Header(modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
            }, screenName = "PAGOS Y COLEGIATURAS")
            ContentOptions(
                onClick = { selection ->
                    scope.launch {
                        controlDisplayButtonModal = selection
                        state.show()
                    }
                },
                visibleScreen = visibleScreen,
                modifier = Modifier.constrainAs(content) {
                    top.linkTo(header.bottom)
                    bottom.linkTo(parent.bottom)
                },
                navigation = navigation
            )
        }
    }
}

@Composable
fun ContentOptions(
    onClick: (Int) -> Unit,
    visibleScreen: String,
    modifier: Modifier,
    navigation: NavHostController
) {
    Box(modifier = modifier.padding(20.dp)) {
        when (visibleScreen) {
            "NoDebts" -> NoDebts(onClick = onClick, navigation = navigation)
            "OutstandingBalance" -> OutstandingBalance(navigation = navigation)
        }
    }
}

@Composable
fun ModalBottomSheetContent(
    controlDisplayButtonModal: Int,
    childSelectorClick: (StudentsSelector, Color) -> Unit = { _, _ -> },
    inscriptionClick: () -> Unit = {}
) {
    if (controlDisplayButtonModal == 1) {
        ChildSelectorModal(onClick = childSelectorClick)
    } else if (controlDisplayButtonModal == 2) {
        PayInscription(onClick = inscriptionClick)
    }
}

@Composable
fun PayInscription(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .background(Color.White)
            .padding(start = 30.dp, end = 30.dp, bottom = 30.dp)
    ) {
        Row(modifier = Modifier.padding(top = 6.dp, bottom = 20.dp)) {
            Spacer(modifier = Modifier.weight(0.44f))
            Divider(
                modifier = Modifier
                    .weight(0.12f)
                    .clip(RoundedCornerShape(50.dp)),
                color = Color(0xFFCFCFCF),
                thickness = 3.dp
            )
            Spacer(modifier = Modifier.weight(0.44f))
        }
        Text(
            text = "¿Quieres abonar o realizar el pago total de la inscripción a 1° de secundaria?",
            color = Upaep_red,
            fontFamily = roboto_black,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 10.dp, start = 40.dp, end = 40.dp)
        )
        Text(
            text = "\$7,000.00",
            color = Dark_grey,
            fontFamily = roboto_black,
            fontSize = 40.sp,
            modifier = Modifier.padding(vertical = 25.dp)
        )
        Button(
            onClick = { /* Pasar a sección de selección de método de pago */ },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Upaep_red,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = "PAGAR INSCRIPCIÓN",
                fontFamily = roboto_medium,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
        Button(
            onClick = { /* Pasar a pantalla de pagar otra cantidad  */ },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Dark_grey,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = "ABONAR OTRA CANTIDAD",
                fontFamily = roboto_medium,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
    }
}

@Composable
fun NoDebts(onClick: (Int) -> Unit, navigation: NavHostController) {
    Column(verticalArrangement = Arrangement.spacedBy(25.dp)) {
        TuitionCard(navigation = navigation)
        AdvanceTuitionCard(navigation = navigation)
        AdvanceInscription(onClick = onClick)
    }
}

@Composable
fun TuitionCard(navigation: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigation.navigate(Routes.PaymentMethodScreen.routes) },
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Colegiatura abril",
                    modifier = Modifier.weight(0.6f),
                    color = Upaep_red,
                    fontFamily = roboto_black,
                    fontSize = 18.sp
                )
                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = roboto_regular,
                            fontSize = 15.sp,
                            color = Dark_grey
                        )
                    ) {
                        append("Paga antes del ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontFamily = roboto_black,
                            fontSize = 15.sp,
                            color = Dark_grey
                        )
                    ) {
                        append("20/04/2023")
                    }
                }, modifier = Modifier.weight(0.4f), textAlign = TextAlign.End)
            }
            Text(
                text = "$2,000.00",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 60.dp),
                textAlign = TextAlign.Center,
                color = Dark_grey,
                fontFamily = roboto_black,
                fontSize = 40.sp
            )

            Surface(color = Upaep_red, shape = RoundedCornerShape(50.dp)) {
                Text(
                    text = "PAGAR",
                    fontFamily = roboto_regular,
                    modifier = Modifier.padding(horizontal = 25.dp, vertical = 8.dp),
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
fun AdvanceTuitionCard(navigation: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigation.navigate(Routes.AdvanceTuition.routes)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        backgroundColor = Color.White
    ) {
        Box(modifier = Modifier.padding(horizontal = 30.dp)) {
            Icon(
                imageVector = Icons.Default.Payment,
                contentDescription = "",
                modifier = Modifier.align(
                    Alignment.CenterStart
                )
            )
            Text(
                text = "Anticipa colegiaturas",
                color = Color.Black,
                fontFamily = roboto_black,
                modifier = Modifier
                    .align(
                        Alignment.Center
                    )
                    .padding(vertical = 30.dp),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun AdvanceInscription(onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(2)
            }, shape = RoundedCornerShape(10.dp), elevation = 5.dp, backgroundColor = Color.White
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            Text(text = "Adelanta la inscripción a")
            Text(text = "1 de Secundaria")
        }
    }
}

@Composable
fun OutstandingBalance(navigation: NavHostController) {
    val currentRotation by remember { mutableStateOf(90f) }
    val rotation = remember { Animatable(currentRotation) }
    var opened by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            OutstandingBalanceHeader()
            OutstandingBalanceBody(rotation = rotation.value, onClick = {
                opened = !opened
            })
            OutstandingBalanceDescription(opened = opened)
            OutstandingBalanceFooter(navigation = navigation)
        }
    }
    LaunchedEffect(opened) {
        rotation.animateTo(
            targetValue = if (opened) 270f else 90f,
            animationSpec = tween(
                durationMillis = 150,
                easing = LinearEasing
            )
        )
    }
}

@Composable
fun OutstandingBalanceFooter(navigation: NavHostController) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = { navigation.navigate(Routes.AdvanceTuition.routes) }, colors = ButtonDefaults.buttonColors(
                backgroundColor = Upaep_red,
                contentColor = Color.White
            ), shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = "PAGAR",
                modifier = Modifier.padding(horizontal = 10.dp),
                fontFamily = roboto_regular
            )
        }
    }
}

@Composable
fun OutstandingBalanceHeader() {
    Text(
        text = "Colegiatura marzo",
        color = Upaep_red,
        fontFamily = roboto_black,
        fontSize = 18.sp
    )
    Divider(modifier = Modifier.padding(vertical = 12.dp), color = Tenue_gray)
}

@Composable
fun OutstandingBalanceBody(rotation: Float, onClick: () -> Unit) {
    Row() {
        Text(text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontFamily = roboto_regular,
                    fontSize = 16.sp,
                    color = Dark_grey
                )
            ) {
                append("Para no generar recargos, paga antes del ")
            }
            withStyle(
                style = SpanStyle(
                    fontFamily = roboto_black,
                    fontSize = 16.sp,
                    color = Dark_grey
                )
            ) {
                append("20/03/2023")
            }
        })
    }
    Spacer(modifier = Modifier.size(30.dp))
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("$4,300.00", fontFamily = roboto_black, color = DarkGray, fontSize = 50.sp)
            Spacer(modifier = Modifier.size(5.dp))
            Surface(
                color = Background,
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.clickable { onClick() }) {
                Text(
                    text = "Incluye $300.00 por recargo",
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 7.dp),
                    color = Text_base_color,
                    fontFamily = roboto_medium
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Image(
                painter = painterResource(id = R.drawable.icono_atras),
                contentDescription = "ver descripción retardos",
                modifier = Modifier
                    .size(20.dp)
                    .rotate(rotation)
                    .clickable { onClick() }
            )
            Spacer(modifier = Modifier.size(35.dp))
        }
    }
}

@Composable
fun OutstandingBalanceDescription(opened: Boolean) {
    AnimatedVisibility(
        visible = opened,
        enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
        exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(30.dp)) {
            items(getDescription()) { description ->
                IndividualOutstandingRegister(description)
            }
        }
    }
}

@Composable
fun IndividualOutstandingRegister(description: PaymentDescription) {
    Column() {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = description.name, color = Text_base_color, fontFamily = roboto_black)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = description.amount, color = Text_base_color, fontFamily = roboto_regular)
        }
        Text(text = description.description, color = Text_base_color, fontFamily = roboto_regular)
    }
}

fun getDescription(): List<PaymentDescription> {
    return listOf(
        PaymentDescription(
            name = "Colegiatura abril",
            description = "Venció el 20/06/2023",
            amount = "\$2,240.00"
        ),
        PaymentDescription(
            name = "Colegiatura marzo",
            description = "Venció el 20/06/2023",
            amount = "\$2,300.00"
        ),
        PaymentDescription(
            name = "Colegiatura febrero",
            description = "Venció el 20/06/2023",
            amount = "\$2,200.00"
        )
    )
}