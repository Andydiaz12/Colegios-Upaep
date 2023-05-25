package com.upaep.colegios.view.base.genericComponents

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.upaep.colegios.R
import com.upaep.colegios.view.base.theme.Dark_grey
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_medium
import com.upaep.colegios.view.base.theme.roboto_regular

@Composable
fun StudentCardInfo(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    studentName: String = "",
    studentLevel: String = "",
    levelColor: Color,
    studentGroup: String,
    selectorScreen: Boolean = true,
    defaultTextColor: Color = Dark_grey,
    imgSize: Dp = 100.dp,
    maxWidth: Boolean = false,
    borderStroke: Dp = 4.dp,
    blockedElement: Boolean = false,
    onClick: () -> Unit = {},
    spacerSize: Dp = 12.dp,
    imageTextSeparation: Dp = 16.dp,
    alpha: Float = 0.5f
) {
    Row(
        modifier = if (blockedElement) {
            modifier
                .background(backgroundColor)
                .padding(15.dp)
                .alpha(alpha)
        } else {
            modifier
                .background(backgroundColor)
                .padding(15.dp)
                .clickable { onClick() }
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(imageTextSeparation)
    ) {
        Image(
            painter = painterResource(id = R.drawable.icono_usuario_login),
            contentDescription = "alumno",
            modifier = Modifier
                .size(imgSize)
                .border(BorderStroke(borderStroke, levelColor), CircleShape)
                .padding(4.dp)
                .clip(CircleShape)
        )
        Column() {
            Text(
                studentName,
                fontFamily = roboto_black,
                color = defaultTextColor,
                modifier = if (maxWidth) Modifier.widthIn(min = 0.dp, max = 200.dp) else Modifier,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.size(spacerSize))
            Row() {
                if (selectorScreen) {
                    Text(text = studentLevel, fontFamily = roboto_black, color = levelColor)
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(text = studentGroup, fontFamily = roboto_medium, color = defaultTextColor)
                } else {
                    Text(text = studentGroup, fontFamily = roboto_medium, color = defaultTextColor)
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = studentLevel, fontFamily = roboto_black, color = levelColor)
                }
            }
        }
    }
}