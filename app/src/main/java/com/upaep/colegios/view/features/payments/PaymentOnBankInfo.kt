package com.upaep.colegios.view.features.payments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.colegios.model.entities.payments.InBankPayment
import com.upaep.colegios.model.entities.payments.PaymentBankInstructions
import com.upaep.colegios.view.base.genericComponents.FooterWithButton
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.theme.Dark_grey
import com.upaep.colegios.view.base.theme.Text_base_color
import com.upaep.colegios.view.base.theme.Upaep_red
import com.upaep.colegios.view.base.theme.firasans_bold
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_regular
import com.upaep.colegios.viewmodel.features.payments.PaymentMethodViewModel

@Preview
@Composable
fun PaymentOnBankInfo(paymentMethodViewModel: PaymentMethodViewModel = hiltViewModel()) {
    val inBankPayment = false
    val steps =
        if (inBankPayment) paymentMethodViewModel.getInstructionsStepsInBankPayment() else paymentMethodViewModel.getInstructionsSteps()
    val tableData = if (inBankPayment) paymentMethodViewModel.getTableInformation() else emptyList()
    val sectionName = if(inBankPayment) "FICHA DE PAGO" else "PAGOS Y COLEGIATURAS"
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, content, footer) = createRefs()
        Header(modifier = Modifier.constrainAs(header) {
            top.linkTo(parent.top)
        }, screenName = sectionName, rightMenuOptions = false)
        Content(modifier = Modifier.constrainAs(content) {
            top.linkTo(header.bottom)
            bottom.linkTo(footer.top)
            height = Dimension.fillToConstraints
        }, steps = steps, inBankPayment = inBankPayment, tableData = tableData)
        FooterWithButton(modifier = Modifier.constrainAs(footer) {
            bottom.linkTo(parent.bottom)
        }, buttonText = "DESCARGAR", onClick = {})
    }
}

@Composable
fun TableSurfaceWithInformation(tableData: List<InBankPayment>) {
    Spacer(modifier = Modifier.size(15.dp))
    Surface(shape = RoundedCornerShape(10.dp)) {
        Column(
            modifier = Modifier.padding(
                top = 20.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 8.dp
            )
        ) {
            for (data in tableData) {
                Row() {
                    Text(
                        text = data.name,
                        fontFamily = roboto_black,
                        modifier = Modifier.weight(0.4f),
                        color = Text_base_color
                    )
                    Text(
                        text = data.description,
                        fontFamily = roboto_regular,
                        modifier = Modifier.weight(0.6f),
                        color = Text_base_color
                    )
                }
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
    Spacer(modifier = Modifier.size(30.dp))
}

@Composable
fun Content(
    modifier: Modifier,
    steps: List<PaymentBankInstructions>,
    inBankPayment: Boolean = false,
    tableData: List<InBankPayment>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        item {
            if (inBankPayment) {
                TableSurfaceWithInformation(tableData)
            } else {
                Text(
                    text = "FICHA DE PAGO",
                    color = Upaep_red,
                    fontFamily = firasans_bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            }

            for (step in steps) {
                IndividualInstruction(instruction = step)
            }
        }
    }
}

@Composable
fun IndividualInstruction(instruction: PaymentBankInstructions) {
    Column {
        Text(text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontFamily = roboto_black,
                    fontSize = 16.sp,
                    color = Dark_grey
                )
            ) {
                append(instruction.step ?: "")
            }
            withStyle(
                style = SpanStyle(
                    fontFamily = roboto_regular,
                    fontSize = 16.sp,
                    color = Dark_grey
                )
            ) {
                append(instruction.description)
            }
        })
        if (instruction.extraContent != null) {
            Text(
                text = instruction.extraContent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontFamily = roboto_black,
                fontSize = 16.sp
            )
        } else {
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}

