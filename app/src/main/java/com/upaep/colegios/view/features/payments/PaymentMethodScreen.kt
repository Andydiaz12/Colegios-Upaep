package com.upaep.colegios.view.features.payments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.colegios.R
import com.upaep.colegios.model.entities.payments.ButtomSheetDescription
import com.upaep.colegios.model.entities.payments.PaymentMethod
import com.upaep.colegios.model.entities.payments.PaymentOptionImageDescription
import com.upaep.colegios.model.entities.payments.PaymentOptions
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.theme.Background
import com.upaep.colegios.view.base.theme.Blue_primary
import com.upaep.colegios.view.base.theme.Dark_grey
import com.upaep.colegios.view.base.theme.Light_gray
import com.upaep.colegios.view.base.theme.Text_base_color
import com.upaep.colegios.view.base.theme.Upaep_grey
import com.upaep.colegios.view.base.theme.Upaep_red
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_medium
import com.upaep.colegios.view.base.theme.roboto_regular
import com.upaep.colegios.viewmodel.features.payments.PaymentMethodViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PaymentMethodScreen(
    paymentMethodViewModel: PaymentMethodViewModel = hiltViewModel(),
) {
    val methodList = paymentMethodViewModel.getPaymentMethods()
    val selectedMethod by paymentMethodViewModel.selectedMethod.observeAsState(PaymentMethod(id = 0))
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = state,
        scrimColor = Color.Black.copy(alpha = 0.6f),
        sheetContent = {
            ButtomSheetContent(selectedMethod = selectedMethod)
        }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (header, card) = createRefs()
            Header(modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, screenName = "PAGOS Y COLEGIATURAS", rightMenuOptions = false)
            CardWithDescription(
                modifier = Modifier.constrainAs(card) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
                onItemChanged = { selection -> paymentMethodViewModel.updateSelectedMethod(method = selection) },
                methodList = methodList,
                selectedMethod = selectedMethod,
                clickedPaymentOption = {
                    scope.launch { state.show() }
                }
            )
        }
    }
}

@Composable
fun ButtomSheetContent(selectedMethod: PaymentMethod) {
    val description = when (selectedMethod.id) {
        1 -> {
            ButtomSheetDescription(
                header = "Pago con tarjeta",
                description = "Al realizar el pago con tarjeta de débito o crédito, se abrirá una página del banco.\n" +
                        "Cuando termines, regresa y consulta el estado de cuenta.",
                buttonText = "ABRIR PÁGINA DEL BANCO",
                onButtonClick = {})
        }

        2 -> {
            ButtomSheetDescription(
                header = "Transferencia interbancaria",
                description = "Para realizar una transferencia, se abrirá Multipagos BBVA para generar la referencia de pago.",
                buttonText = "ABRIR PÁGINA DEL BANCO",
                onButtonClick = {})
        }

        3 -> {
            ButtomSheetDescription(
                header = "Depósito en sucursal o cajero automático",
                description = "Para realizar el depósito, se abrirá Multipagos BBVA para generar la referencia de pago.",
                buttonText = "ABRIR PÁGINA DEL BANCO",
                onButtonClick = {})
        }

        else -> {
            ButtomSheetDescription(
                header = "",
                description = "",
                buttonText = "",
                onButtonClick = {}
            )
        }
    }
    CardPaymentOption(description)
}

@Composable
fun CardPaymentOption(description: ButtomSheetDescription) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 60.dp, end = 50.dp, start = 50.dp, bottom = 30.dp)
    ) {
        Text(
            text = description.header,
            color = Upaep_red,
            fontFamily = roboto_black,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = description.description,
            textAlign = TextAlign.Center,
            color = Text_base_color,
            fontFamily = roboto_regular,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.size(120.dp))
        Button(
            onClick = { description.onButtonClick() },
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Upaep_red,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = description.buttonText,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
            )
        }
    }
}

@Composable
fun CardWithDescription(
    modifier: Modifier,
    onItemChanged: (PaymentMethod) -> Unit,
    methodList: List<PaymentMethod>,
    selectedMethod: PaymentMethod,
    clickedPaymentOption: () -> Unit
) {
    Box(modifier = modifier.padding(top = 20.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = Color.White
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 40.dp)) {
                PaymentAmount()
                Divider(color = Upaep_grey, modifier = Modifier.padding(vertical = 40.dp))
                PaymentSelection(
                    methodList = methodList,
                    onItemChanged = onItemChanged,
                    selectedMethod = selectedMethod,
                    clickedPaymentOption = clickedPaymentOption
                )
            }
        }
    }
}

@Composable
fun PaymentSelection(
    methodList: List<PaymentMethod>,
    onItemChanged: (PaymentMethod) -> Unit,
    selectedMethod: PaymentMethod,
    clickedPaymentOption: () -> Unit
) {
    Text(
        text = "*Elige el método de pago",
        fontFamily = roboto_black,
        color = Color.Black,
        fontSize = 16.sp
    )
    Spacer(modifier = Modifier.size(20.dp))
    for (method in methodList) {
        PaymentSection(
            method = method,
            selectedMethod = selectedMethod,
            onItemChanged = onItemChanged,
            clickedPaymentOption = clickedPaymentOption
        )
    }
}

@Composable
fun PaymentSection(
    method: PaymentMethod,
    selectedMethod: PaymentMethod,
    onItemChanged: (PaymentMethod) -> Unit,
    clickedPaymentOption: () -> Unit
) {
    val selected = (method.name == selectedMethod.name)
    val backgroundColor = if (selected) Background else Color.White
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor)
            .padding(bottom = 20.dp, top = 0.dp)
    ) {
        PaymentMethodDescription(
            method = method,
            onItemChanged = onItemChanged,
            selected = selected
        )
        if (selected) {
            for (option in method.options) {
                PaymentOptions(option = option,
                    clickedPaymentOption = if (option.action) clickedPaymentOption else {
                        {
                            //navigation to other screen
                        }
                    }, imageList = option.image)
            }
        }
    }
}

@Composable
fun PaymentOptions(
    option: PaymentOptions,
    clickedPaymentOption: () -> Unit,
    imageList: List<PaymentOptionImageDescription>
) {
    Spacer(modifier = Modifier.size(10.dp))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp).height(45.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { clickedPaymentOption() }
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (option.name != null) {
                Text(text = option.name, color = Text_base_color, fontFamily = roboto_regular)
                Spacer(modifier = Modifier.weight(1f))
            }
            for ((index, image) in imageList.withIndex()) {
                Image(
                    painterResource(id = image.image),
                    contentDescription = "",
                    modifier = Modifier
                        .size(image.imageSize)
                        .padding(vertical = 5.dp)
                )
                if(index + 1 < imageList.size) {
                    Spacer(modifier = Modifier.size(5.dp))
                }
            }
        }
    }
}

@Composable
fun PaymentMethodDescription(
    method: PaymentMethod,
    onItemChanged: (PaymentMethod) -> Unit,
    selected: Boolean
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected, onClick = {
                onItemChanged(method)
            }, colors = RadioButtonDefaults.colors(
                selectedColor = Light_gray,
                unselectedColor = Light_gray
            )
        )
        Column(modifier = Modifier.clickable {
            onItemChanged(method)
        }) {
            Text(text = method.name ?: "", fontFamily = roboto_black, color = Dark_grey)
            if (!method.description.isNullOrEmpty()) {
                Text(text = method.description, fontFamily = roboto_regular, color = Dark_grey)
            }
        }
    }
}

@Composable
fun PaymentAmount() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Estás pagando",
            color = Dark_grey,
            fontFamily = roboto_medium,
            fontSize = 20.sp
        )
        Text(text = "$2,200.00", color = Blue_primary, fontFamily = roboto_black, fontSize = 32.sp)
    }
}