package com.upaep.colegios.view.base.genericComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upaep.colegios.view.base.theme.Background
import com.upaep.colegios.view.base.theme.Tenue_gray
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_medium

@Preview(showSystemUi = true)
@Composable
fun ChildSelectorModal() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(modifier = Modifier.padding(top = 6.dp, bottom = 20.dp)) {
                Spacer(modifier = Modifier.weight(0.44f))
                Divider(
                    modifier = Modifier
                        .weight(0.12f)
                        .clip(RoundedCornerShape(50.dp)),
                    color = Color(0xFFCFCFCF),
                    thickness = 3.dp
                )
                Spacer(modifier = Modifier.weight(0.44f))
            }
        }
        items(15) {
            StudentCardInfo(
                levelColor = Color.Magenta,
                studentGroup = "3a",
                studentName = "Cesar López Valeriano",
                studentLevel = "Preescolar",
                selectorScreen = false,
                imgSize = 65.dp,
                backgroundColor = Background,
                maxWidth = false,
                imageTextSeparation = 25.dp,
                blockedElement = true
            )
        }
    }
}