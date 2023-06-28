package com.upaep.colegios.view.features.accountbalance

import android.graphics.fonts.Font
import android.graphics.fonts.FontFamily
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.colegios.R
import com.upaep.colegios.model.entities.accountbalance.AccountBalance
import com.upaep.colegios.view.base.genericComponents.ChildSelectorModal
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.theme.Blue_primary
import com.upaep.colegios.view.base.theme.Dark_grey
import com.upaep.colegios.view.base.theme.Gray_unnamed
import com.upaep.colegios.view.base.theme.Preschool_color
import com.upaep.colegios.view.base.theme.Text_base_color
import com.upaep.colegios.view.base.theme.firasans_medium
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_regular
import com.upaep.colegios.view.features.calendar.CalendarScreenContent
import com.upaep.colegios.viewmodel.base.genericComponents.ChildDataViewModel
import com.upaep.colegios.viewmodel.features.accountbalance.AccountBalanceViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountBalanceScreen(
    childDataViewModel: ChildDataViewModel = hiltViewModel(),
    accountBalanceViewModel: AccountBalanceViewModel = hiltViewModel()
) {
    val listPayments by accountBalanceViewModel.listPayments.observeAsState(emptyList())
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var tabSelected by remember { mutableStateOf(1) }
    ModalBottomSheetLayout(
        sheetState = state,
        scrimColor = Color.Black.copy(alpha = 0.6f),
        sheetContent = {
            ChildSelectorModal(onClick = { student, color ->
                childDataViewModel.changeSelectedStudent(student, color)
                scope.launch { state.hide() }
            })
        }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (header, content) = createRefs()
            Header(modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
            }, changeChildAction = {
                scope.launch { state.show() }
            }, screenName = "ESTADO DE CUENTA")
            AccountBalanceData(modifier = Modifier.constrainAs(content) {
                top.linkTo(header.bottom)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }, tabSelected = tabSelected, onChangeTab = { selection ->
                tabSelected = selection
            }, listPayments = listPayments)
        }
    }
}

@Composable
fun AccountBalanceData(
    modifier: Modifier,
    tabSelected: Int,
    onChangeTab: (Int) -> Unit,
    listPayments: List<AccountBalance>
) {
    LazyColumn(
        modifier = modifier.padding(20.dp)
    ) {
        item {
            AccountBalanceHeader()
            AccountBalanceContent(
                tabSelected = tabSelected,
                onChangeTab = onChangeTab,
                listPayments = listPayments
            )
        }
    }
}

@Composable
fun AccountBalanceHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
    ) {
        Column() {
            Text(text = "50% Beca ordinaria", color = Color.Black, fontFamily = roboto_black)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = Preschool_color,
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier.size(5.dp)
                ) {}
                Spacer(modifier = Modifier.size(3.dp))
                Text(text = "Activa", color = Text_base_color, fontFamily = roboto_black)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { }, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Blue_primary
            ), shape = RoundedCornerShape(50.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.icono_ayuda_edo_cta),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "Ayuda", fontFamily = firasans_medium)
            }
        }
    }
    Spacer(modifier = Modifier.size(20.dp))
}

@Composable
fun AccountBalanceTabs(onChangeTab: (Int) -> Unit, tabSelected: Int) {
    val selectedConfig = Pair(first = Color.White, second = roboto_black)
    val unselectedConfig = Pair(first = Gray_unnamed, second = roboto_regular)
    Row(modifier = Modifier.padding(horizontal = 20.dp)) {
        Surface(
            color = if (tabSelected == 1) selectedConfig.first else unselectedConfig.first,
            shape = RoundedCornerShape(topStart = 10.dp),
            modifier = Modifier
                .weight(1f)
                .clickable { onChangeTab(1) }
        ) {
            Text(
                text = "Historial de pagos",
                color = Text_base_color,
                fontFamily = if (tabSelected == 1) selectedConfig.second else unselectedConfig.second,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp),
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
        Surface(
            color = if (tabSelected == 2) selectedConfig.first else unselectedConfig.first,
            shape = RoundedCornerShape(topEnd = 10.dp),
            modifier = Modifier
                .weight(1f)
                .clickable { onChangeTab(2) }
        ) {
            Text(
                text = "Próximos pagos", color = Text_base_color,
                fontFamily = if (tabSelected == 2) selectedConfig.second else unselectedConfig.second,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp),
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun AccountBalanceContent(
    tabSelected: Int,
    onChangeTab: (Int) -> Unit,
    listPayments: List<AccountBalance>
) {
    Column() {
        AccountBalanceTabs(onChangeTab = onChangeTab, tabSelected = tabSelected)
        Card(backgroundColor = Color.White, shape = RoundedCornerShape(10.dp)) {
            Column(modifier = Modifier.padding(bottom = 30.dp)) {
                for (element in listPayments) {
                    AccountBalanceDesc(
                        name = element.edrsConcepto,
                        amount = element.edrsPago,
                        description = "${element.edrsDescEstatus} el ${element.edrsFchPag}",
                        tabSelected = tabSelected,
                        activeTab = if (element.edrsEstatus == "3") 1 else 2
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun Test() {
    AccountBalanceDesc("INSCRIPCION", "2580", "PAGADO", tabSelected = 1, activeTab = 1)
}

@Composable
fun AccountBalanceDesc(
    name: String,
    amount: String,
    description: String,
    tabSelected: Int,
    activeTab: Int
) {
    if (tabSelected == activeTab) {
        val horizontalPaddings =
            if (activeTab == 1) 10.dp else 25.dp
        Column(modifier = Modifier.padding(top = 20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = horizontalPaddings, end = 15.dp)
            ) {
                if (activeTab == 1) {
                    Image(
                        painter = painterResource(id = R.drawable.icono_checked_verde),
                        contentDescription = "",
                        modifier = Modifier
                            .size(25.dp)
                            .weight(1f)
                    )
                }
                Column(modifier = Modifier.weight(4f)) {
                    Text(
                        text = name.lowercase().replaceFirstChar { char -> char.uppercase() },
                        color = Dark_grey,
                        fontFamily = roboto_black,
                        fontSize = 13.sp
                    )
                    Text(
                        text = description.lowercase()
                            .replaceFirstChar { char -> char.uppercase() },
                        color = Dark_grey,
                        fontFamily = roboto_regular,
                        fontSize = 12.sp
                    )
                    if (activeTab == 2) {
                        Text(
                            text = "Incluye $100 por recargo",
                            color = Dark_grey,
                            fontSize = 12.sp,
                            fontFamily = roboto_regular,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        text = amount,
                        color = Dark_grey,
                        fontFamily = roboto_black,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 13.sp
                    )
                    if (activeTab == 2) {
                        Text(
                            text = "Vencido",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right,
                            fontSize = 12.sp
                        )
                    }
                }

            }
            Divider(modifier = Modifier.padding(top = 15.dp))
        }
    }
}