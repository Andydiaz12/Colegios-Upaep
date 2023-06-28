package com.upaep.colegios.view.features.payments

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.model.entities.payments.PaymentDescription
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.navigation.Routes
import com.upaep.colegios.view.base.theme.Background
import com.upaep.colegios.view.base.theme.Blocked_elements
import com.upaep.colegios.view.base.theme.Blue_primary
import com.upaep.colegios.view.base.theme.Dark_grey
import com.upaep.colegios.view.base.theme.Upaep_grey
import com.upaep.colegios.view.base.theme.Upaep_red
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_medium
import com.upaep.colegios.view.base.theme.roboto_regular
import com.upaep.colegios.viewmodel.features.payments.PaymentsViewModel

@Preview
@Composable
fun AdvanceTuition(paymentsViewModel: PaymentsViewModel = hiltViewModel()) {
    val lastCheckedElement by paymentsViewModel.lastCheckedElement.observeAsState()
    val paymentList = paymentsViewModel.paymentList
    val totalSum by paymentsViewModel.totalPaymentSum.observeAsState("$0.00")
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, static, recycler, footer) = createRefs()
        Header(modifier = Modifier.constrainAs(header) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, screenName = "ANTICIPA COLEGIATURAS")
        TotalPayment(modifier = Modifier.constrainAs(static) {
            top.linkTo(header.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, totalSum = totalSum)
        RecyclerSection(
            modifier = Modifier.constrainAs(recycler) {
                top.linkTo(static.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(footer.top)
                height = Dimension.fillToConstraints
            },
            paymentList = paymentList,
            lastCheckedElement = lastCheckedElement ?: 0,
            checkElement = { partial ->
                //paymentsViewModel.updateLastCheckedElement(partial, amount = partial.amount)
                //paymentsViewModel.adjustTotalSum(amount = partial.amount)
//                paymentsViewModel.updateLastCheckedElement(index = index, partial = partial)
            },
            selectAllAction = {}
        )
        FooterSection(modifier = Modifier.constrainAs(footer) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }, onButtonPressed = { }, enabled = false)
    }
}

@Composable
fun FooterSection(modifier: Modifier, onButtonPressed: () -> Unit = {}, enabled: Boolean) {
    Card(modifier = modifier, shape = RectangleShape, backgroundColor = Background) {
        Button(
            enabled = enabled,
            onClick = { onButtonPressed() },
            modifier = Modifier.padding(end = 30.dp, start = 30.dp, top = 10.dp, bottom = 25.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Upaep_red,
                contentColor = Color.White,
                disabledBackgroundColor = Upaep_red.copy(alpha = 0.7f),
                disabledContentColor = Color.White.copy(alpha = 0.7f)
            )
        ) {
            Text(
                text = "ELIGE EL MÉTODO DE PAGO",
                fontFamily = roboto_medium,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
    }
}

@Composable
fun TotalPayment(modifier: Modifier, totalSum: String) {
    Column(modifier = modifier.padding(end = 10.dp, start = 10.dp, top = 30.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Total a pagar:",
                color = Color.Black,
                fontSize = 26.sp,
                fontFamily = roboto_black
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = totalSum, color = Blue_primary, fontSize = 26.sp, fontFamily = roboto_black)
        }
        Divider(color = Color.Black, modifier = Modifier.padding(top = 20.dp))
    }
}

@Composable
fun RecyclerSection(
    modifier: Modifier,
    paymentList: List<PaymentDescription>,
    lastCheckedElement: Int,
    checkElement: (PaymentDescription) -> Unit,
    selectAllAction: () -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            InstructionsSection()
        }
        itemsIndexed(paymentList) { index, partials ->
            IndividualPartial(
                payment = partials,
                lastCheckedElement = lastCheckedElement,
                index = index,
                checkElement = checkElement
            )
        }
    }
}

@Composable
fun InstructionsSection() {
    Row(
        modifier = Modifier.padding(top = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Selecciona las colegiaturas que quieras pagar",
            modifier = Modifier.weight(0.50f),
            color = Dark_grey
        )
        Spacer(modifier = Modifier.weight(0.05f))
        Button(
            onClick = { }, colors = ButtonDefaults.buttonColors(
                backgroundColor = Upaep_red,
                contentColor = Color.White
            ), shape = RoundedCornerShape(50.dp), modifier = Modifier.weight(0.45f)
        ) {
            Text(text = "Seleccionar todo", fontFamily = roboto_regular)
        }
    }
}

@Composable
fun IndividualPartial(
    payment: PaymentDescription,
    lastCheckedElement: Int,
    index: Int,
    checkElement: (PaymentDescription) -> Unit
) {
    val blocked = (index != lastCheckedElement)
    val textColor =
        if (blocked) Blocked_elements else if (true) Color.Black else Upaep_grey
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = if (blocked) Modifier else Modifier.clickable {
            checkElement(payment)
        },
        elevation = 5.dp,
        backgroundColor = Color.White
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 25.dp, horizontal = 20.dp),
        ) {
            Box(modifier = Modifier.weight(0.1f)) {
                Checkbox(
                    checked = true,
                    onCheckedChange = {
                        checkElement(payment)
                    },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Dark_grey,
                        checkmarkColor = Upaep_red,
                        checkedColor = Color.Black,
                    )
                )
            }
            Column(
                modifier = Modifier.weight(0.6f)
            ) {
                Text(
                    text = "payment.name",
                    color = textColor,
                    fontFamily = roboto_black,
                    fontSize = 17.sp
                )
                Text(
                    text = "payment.description",
                    color = textColor,
                    fontFamily = roboto_regular,
                    fontSize = 12.sp
                )
            }
            Text(
                text = "payment.amount",
                modifier = Modifier.weight(0.3f),
                color = textColor,
                fontFamily = roboto_regular,
                fontSize = 17.sp,
                textAlign = TextAlign.End
            )
        }
    }
}