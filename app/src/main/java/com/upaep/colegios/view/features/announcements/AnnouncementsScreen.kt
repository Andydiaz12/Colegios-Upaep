package com.upaep.colegios.view.features.announcements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.theme.*

@Composable
fun AnnouncementsScreen(
    title: String,
    content: String,
    navigation: NavHostController,
    theme: ThemeSchema
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, card) = createRefs()
        Header(modifier = Modifier.constrainAs(header) {
            top.linkTo(parent.top)
        }, visibleName = false, rightMenuOptions = false, navigation = navigation)
        AnnouncementsCard(modifier = Modifier
            .constrainAs(card) {
                top.linkTo(header.bottom)
                height = Dimension.fillToConstraints
            }
            .padding(top = 32.dp, bottom = 80.dp),
            title = title,
            content = content,
            theme = theme)
    }
}

@Composable
fun AnnouncementsCard(content: String, title: String, modifier: Modifier, theme: ThemeSchema) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        elevation = 5.dp
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(theme.backgroundColor).padding(22.dp)
        ) {
            items(1) {
                Text(text = title, color = Messages_red, fontFamily = roboto_black)
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = content,
                    color = Dark_grey,
                    fontFamily = roboto_regular,
                    lineHeight = 19.sp
                )
            }
        }
    }
}