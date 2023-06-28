package com.upaep.colegios.view.features.invoices

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.R
import com.upaep.colegios.model.entities.payments.PaymentDescription
import com.upaep.colegios.view.base.genericComponents.ChildSelectorModal
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.navigation.Routes
import com.upaep.colegios.view.base.theme.Blue_primary
import com.upaep.colegios.view.base.theme.Text_base_color
import com.upaep.colegios.view.base.theme.Upaep_red
import com.upaep.colegios.view.base.theme.firasans_bold
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_regular
import com.upaep.colegios.viewmodel.base.GeneralMethods
import com.upaep.colegios.viewmodel.base.genericComponents.ChildDataViewModel
import com.upaep.colegios.viewmodel.features.invoice.InvoiceViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InvoiceScreen(
    navigation: NavHostController,
    invoiceViewModel: InvoiceViewModel = hiltViewModel(),
    childDataViewModel: ChildDataViewModel = hiltViewModel()
) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = state,
        scrimColor = Color.Black.copy(alpha = 0.6f),
        sheetContent = {
            ButtomSheetContent(onChangeChild = {

            })
        }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val invoiceList by invoiceViewModel.invoiceList.observeAsState(emptyList())
            val invoiceData = true
            val screenName = if (invoiceData) "HISTORIAL DE FACTURAS" else "FACTURAS"
            val (header, content) = createRefs()
            Header(modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
            }, screenName = screenName)
            InvoiceScreenContent(modifier = Modifier.constrainAs(content) {
                top.linkTo(header.bottom)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }, InvoiceData = invoiceData, navigation = navigation, invoiceList = invoiceList)
        }
    }
}

@Composable
fun ButtomSheetContent(onChangeChild: () -> Unit) {
//    ChildSelectorModal(onClick = { student, color ->
//        childDataViewModel.changeSelectedStudent(student, color)
//        scope.launch { state.hide() }
//    })
    SentMailMessage()
}

@Preview
@Composable
fun SentMailMessage() {
    Column() {
        Text(text = "")
        Button(onClick = {}) {
            Text(text = "ENTENDIDO")
        }
    }
}


@Composable
fun InvoiceScreenContent(
    modifier: Modifier,
    InvoiceData: Boolean,
    navigation: NavHostController,
    invoiceList: List<PaymentDescription>
) {
    Box(modifier = modifier) {
        if (InvoiceData) {
            InvoiceDataRegistered(navigation = navigation, invoiceList = invoiceList)
        } else {
            NoneInvoiceData()
        }
    }
}

@Composable
fun InvoiceDataRegistered(navigation: NavHostController, invoiceList: List<PaymentDescription>) {
    LazyColumn(modifier = Modifier.padding(20.dp)) {
        item {
            InvoiceDataRegisteredHeader(navigation = navigation)
            Spacer(modifier = Modifier.size(20.dp))
        }
        items(invoiceList) { invoice ->
            IndividualInvoice(
                amount = invoice.movaImporte,
                description = invoice.fechamov,
                name = invoice.ctsrDescrAl,
                rfc = invoice.persRfc
            )
        }
    }
}

@Composable
fun IndividualInvoice(amount: String, description: String, name: String, rfc: String) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White,
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, bottom = 25.dp, end = 15.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icono_enviar_factura),
                contentDescription = "envia factura",
                modifier = Modifier
                    .weight(1f)
                    .size(25.dp)
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 5.dp)
            ) {
                Text(text = name, color = Color.Black, fontFamily = roboto_black, fontSize = 15.sp)
                Text(
                    text = "Factuado el $description",
                    color = Color.Black,
                    fontFamily = roboto_regular,
                    fontSize = 12.sp
                )
                Text(
                    text = rfc,
                    color = Color.Black,
                    fontFamily = roboto_regular,
                    fontSize = 12.sp
                )
            }
            Text(
                text = GeneralMethods.currencyParse(amount),
                color = Color.Black,
                fontFamily = roboto_regular,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun InvoiceDataRegisteredHeader(navigation: NavHostController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.weight(1f),
                onClick = { navigation.navigate(Routes.TaxDataScreen.routes) },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Blue_primary,
                    backgroundColor = Color.White
                ),
                elevation = ButtonDefaults.elevation(5.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = "DATOS FISCALES",
                    modifier = Modifier.padding(horizontal = 0.dp),
                    fontFamily = firasans_bold
                )
            }
        }
        Row() {
            Text(
                text = "Recibe tus facturas vía correo electrónico",
                fontFamily = roboto_black,
                color = Color.Black,
                modifier = Modifier.weight(0.6f),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(0.4f))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun NoneInvoiceData(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 20.dp)
    ) {
        val (image, content, footer) = createRefs()
        val middleLine = createGuidelineFromTop(fraction = 0.5f)
        Image(
            painter = painterResource(id = R.drawable.icono_brumildo_no_datos_fiscales),
            contentDescription = "",
            modifier = Modifier
                .size(250.dp)
                .constrainAs(image) {
                    bottom.linkTo(middleLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        ContentSection(modifier = Modifier.constrainAs(content) {
            top.linkTo(middleLine)
        })
        FooterButton(modifier = Modifier.constrainAs(footer) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
    }
}

@Composable
fun ContentSection(modifier: Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Sin datos fiscales",
            fontFamily = roboto_black,
            color = Upaep_red,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "Ingresa desde un ordenador para capturarlo y poder generar la factura.",
            textAlign = TextAlign.Center,
            color = Text_base_color,
            fontFamily = roboto_regular,
            fontSize = 18.sp
        )
    }
}

@Composable
fun FooterButton(modifier: Modifier) {
    Button(
        onClick = { }, modifier = modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
            backgroundColor = Upaep_red,
            contentColor = Color.White
        ), shape = RoundedCornerShape(50.dp)
    ) {
        Text(text = "REGRESAR", modifier = Modifier.padding(5.dp))
    }
}