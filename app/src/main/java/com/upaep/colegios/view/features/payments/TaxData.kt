package com.upaep.colegios.view.features.payments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.colegios.R
import com.upaep.colegios.model.entities.payments.CompanyName
import com.upaep.colegios.view.base.genericComponents.FooterWithButton
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.theme.Background
import com.upaep.colegios.view.base.theme.Dark_grey
import com.upaep.colegios.view.base.theme.Text_base_color
import com.upaep.colegios.view.base.theme.Upaep_red
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_regular
import com.upaep.colegios.viewmodel.features.payments.TaxDataViewModel

@Preview(showSystemUi = true)
@Composable
fun TaxDataScreen(taxDataViewModel: TaxDataViewModel = hiltViewModel()) {
    val companyNames by taxDataViewModel.companyNames.observeAsState(emptyList())
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, content, footer) = createRefs()
        Header(modifier = Modifier.constrainAs(header) {
            top.linkTo(parent.top)
        }, screenName = "DATOS FISCALES", changeChild = false)
        ContentDecision(modifier = Modifier.constrainAs(content) {
            top.linkTo(header.bottom)
            if (companyNames.size > 1) {
                bottom.linkTo(footer.top)
            } else {
                bottom.linkTo(parent.bottom)
            }
            height = Dimension.fillToConstraints
        }, companyNames = companyNames)
        if (companyNames.size > 1) {
            FooterWithButton(modifier = Modifier.constrainAs(footer) {
                bottom.linkTo(parent.bottom)
            }, onClick = {

            }, buttonText = "GUARDAR CAMBIOS")
        }
    }
}

@Composable
fun ContentDecision(modifier: Modifier, companyNames: List<CompanyName>) {
    Box(modifier = modifier.padding(horizontal = 20.dp)) {
        if (companyNames.size > 1) {
            MultipleCompanyNames(companyNames = companyNames)
        } else if(companyNames.size == 1) {
            SingleCompanyName(companyNames = companyNames)
        }
    }
}

@Composable
fun MultipleCompanyNames(companyNames: List<CompanyName>) {
    LazyColumn {
        item {
            MultiCompanyNamesWarning()
            Spacer(modifier = Modifier.size(20.dp))
        }
        items(companyNames) { cards ->
            CardCompanyName(company = cards)
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun SingleCompanyName(companyNames: List<CompanyName>) {
    CardCompanyName(multipleCompanyName = false, companyNames[0])
}

@Composable
fun MultiCompanyNamesWarning() {
    Column() {
        Spacer(modifier = Modifier.size(25.dp))
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Confirmar datos fiscales",
                modifier = Modifier.weight(4f),
                color = Upaep_red,
                fontFamily = roboto_black,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.size(15.dp))
        Row {
            Image(
                painter = painterResource(id = R.drawable.icono_informacion_rojo),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 15.dp)
                    .size(35.dp)
                    .weight(1f)
            )
            Text(
                text = "Selecciona la razón social con la que quieres que se emita tu factura automáticamente. Esta se te hará llegar por correo electrónico.",
                modifier = Modifier.weight(4f),
                color = Text_base_color,
                fontFamily = roboto_regular,
                fontSize = 17.sp
            )
        }
    }
}

@Composable
fun CardCompanyName(multipleCompanyName: Boolean = true, company: CompanyName) {
    val paddingButtomRow = if (!multipleCompanyName) 15.dp else 35.dp
    val paddingStartRow = if(!multipleCompanyName) 15.dp else 10.dp
    Card(shape = RoundedCornerShape(10.dp), backgroundColor = Color.White, elevation = 5.dp) {
        Row(
            modifier = Modifier.padding(start = paddingStartRow, end = 15.dp, top = 20.dp, bottom = paddingButtomRow)
        ) {
            if (multipleCompanyName) {
                RadioButton(selected = true, onClick = { })
            }
            Column {
                Text(
                    text = company.name,
                    fontFamily = roboto_black,
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = "${company.rfc} \n${company.taxRegime}\n${company.mail}",
                    color = Dark_grey,
                    fontFamily = roboto_regular,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Domicilio fiscal",
                    color = Color.Black,
                    fontFamily = roboto_black,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = "${company.address}\n${company.zipCode}\n${company.state}",
                    color = Dark_grey,
                    fontFamily = roboto_regular,
                    fontSize = 16.sp
                )
                if (!multipleCompanyName) {
                    Spacer(modifier = Modifier.size(30.dp))
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Background)
                            .padding(horizontal = 20.dp, vertical = 25.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icono_informacion_rojo),
                            contentDescription = "",
                            modifier = Modifier.size(35.dp)
                        )
                        Text(
                            text = "Para cambiar tus datos fixcales ingresa a la aplicación web",
                            fontFamily = roboto_regular,
                            color = Dark_grey,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}