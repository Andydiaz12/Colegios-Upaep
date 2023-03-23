package com.upaep.colegios.view.features.studentselector

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.upaep.colegios.R
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_medium

@Preview(showSystemUi = true)
@Composable
fun StudentSelectorScreen() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        //Header()
        MessageContainer()
        StudentsContainer()
    }
}

@Composable
fun StudentsContainer() {
    StudentCard()
}

@Composable
fun MessageContainer() {

}

@Preview
@Composable
fun StudentCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icono_usuario_login),
                contentDescription = "estudiante",
                modifier = Modifier
                    .size(100.dp)
                    .border(BorderStroke(4.dp, Color.Green), CircleShape)
                    .padding(4.dp)
                    .clip(CircleShape)
            )
            Column() {
                Text("Cesar López Valeriano", fontFamily = roboto_black)
                Spacer(modifier = Modifier.size(12.dp))
                Row() {
                    Text(text = "Preescolar", fontFamily = roboto_black)
                    Text(text = "3ºB", fontFamily = roboto_medium)
                }
            }
        }
    }
}
