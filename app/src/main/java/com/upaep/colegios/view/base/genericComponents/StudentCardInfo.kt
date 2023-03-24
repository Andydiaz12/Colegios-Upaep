package com.upaep.colegios.view.base.genericComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.upaep.colegios.R
import com.upaep.colegios.view.base.theme.Dark_grey
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_medium

@Composable
fun StudentCardInfo(
    backgroundColor: Color = Color.White,
    studentName: String,
    studentLevel: String,
    levelColor: Color,
    studentGroup: String,
    selectorScreen: Boolean = true,
    defaultTextColor: Color = Dark_grey,
    imgSize: Dp = 100.dp
) {
    Row(
        modifier = Modifier
            .background(backgroundColor)
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.icono_usuario_login),
            contentDescription = "alumno",
            modifier = Modifier
                .size(imgSize)
                .border(BorderStroke(4.dp, levelColor), CircleShape)
                .padding(4.dp)
                .clip(CircleShape)
        )
        Column() {
            Text(studentName, fontFamily = roboto_black, color = defaultTextColor)
            Spacer(modifier = Modifier.size(12.dp))
            Row() {
                if (selectorScreen) {
                    Text(text = studentLevel, fontFamily = roboto_black, color = levelColor)
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(text = studentGroup, fontFamily = roboto_medium, color = defaultTextColor)
                } else {
                    Text(text = studentGroup, fontFamily = roboto_black, color = defaultTextColor)
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(text = studentLevel, fontFamily = roboto_black, color = levelColor)
                }
            }
        }
    }
}