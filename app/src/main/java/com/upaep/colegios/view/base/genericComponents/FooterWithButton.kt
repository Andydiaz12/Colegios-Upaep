package com.upaep.colegios.view.base.genericComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upaep.colegios.view.base.theme.Upaep_red

@Composable
fun FooterWithButton(modifier: Modifier = Modifier, onClick: () -> Unit, buttonText: String) {
    Card(modifier = modifier.fillMaxWidth(), shape = RectangleShape) {
        Button(
            shape = RoundedCornerShape(50.dp),
            onClick = { onClick() },
            modifier = Modifier.padding(horizontal = 40.dp, vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = Upaep_red
            )
        ) {
            Text(text = buttonText, modifier = Modifier.padding(vertical = 5.dp))
        }
    }
}