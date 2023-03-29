package com.upaep.colegios.viewmodel.features.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Replay
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.upaep.colegios.data.entities.announcements.Announcements
import com.upaep.colegios.view.base.genericComponents.HeaderLeftLogo
import com.upaep.colegios.view.base.genericComponents.HeaderRightTwoIcons
import com.upaep.colegios.view.base.genericComponents.StudentCardInfo
import com.upaep.colegios.view.base.navigation.Routes
import com.upaep.colegios.view.base.theme.*

@Composable
fun HomeScreen(
    theme: ThemeSchema,
    studentName: String,
    studentGroup: String,
    studentGrade: String,
    navigation: NavHostController
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (upperSection, lowerSection) = createRefs()
        ContainerHeaderAndStudent(
            navigation = navigation,
            studentName = studentName,
            studentGroup = studentGroup,
            studentGrade = studentGrade,
            modifier = Modifier
                .constrainAs(upperSection) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth())
        AnnouncementsAndFeatures(modifier = Modifier
            .constrainAs(lowerSection) {
                top.linkTo(upperSection.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
            }
            .padding(start = 32.dp, end = 32.dp), theme = theme, navigation = navigation)
    }

}

@Composable
fun AnnouncementsAndFeatures(
    theme: ThemeSchema,
    modifier: Modifier,
    navigation: NavHostController
) {
    ConstraintLayout(modifier = modifier) {
        val (announcements, features) = createRefs()
        AnnouncementsSection(modifier = Modifier.constrainAs(announcements) {
            top.linkTo(parent.top)
        }, theme = theme, navigation)
        Features(modifier = Modifier.constrainAs(features) {
            top.linkTo(announcements.bottom)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
        })
    }
}

@Composable
fun ContainerHeaderAndStudent(
    navigation: NavHostController,
    modifier: Modifier,
    studentName: String,
    studentGroup: String,
    studentGrade: String
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HeaderRightTwoIcons(navigation = navigation)
        StudentDescAndChange(
            studentName = studentName,
            studentGroup = studentGroup,
            studentGrade = studentGrade,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun StudentDescAndChange(
    modifier: Modifier,
    studentName: String,
    studentGroup: String,
    studentGrade: String
) {
    val color = getColor(studentGrade = studentGrade)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color)
            .padding(start = 30.dp, end = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StudentCardInfo(
            studentName = studentName,
            studentLevel = studentGrade,
            levelColor = Color.White,
            studentGroup = studentGroup,
            selectorScreen = false,
            backgroundColor = color,
            defaultTextColor = Color.White,
            imgSize = 60.dp,
            maxWidth = true
        )
        ChangeStudent(modifier = Modifier.padding(bottom = 27.dp))
    }
}

@Composable
fun ChangeStudent(modifier: Modifier) {
    Icon(
        imageVector = Icons.Default.Replay,
        contentDescription = "seleccionar hijo",
        modifier = modifier
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnnouncementsSection(modifier: Modifier, theme: ThemeSchema, navigation: NavHostController) {
    var announcements = getAnnouncements()
    val pagerState = rememberPagerState()
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size(30.dp))
        Card(
            modifier = modifier.clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) {
                //navigation to screen with news complete
                navigation.navigate(Routes.AnnouncementScreen.createRoute(announcements[pagerState.currentPage]))
            },
            shape = RoundedCornerShape(10.dp),
            elevation = 3.dp,
            backgroundColor = theme.backgroundColor
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HorizontalPager(
                    count = announcements.size,
                    state = pagerState
                ) { page ->
                    AnnouncementsContent(
                        title = announcements[page].title,
                        content = announcements[page].content
                    )
                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = Messages_red,
                    inactiveColor = Dark_grey,
                )
                Spacer(modifier = Modifier.size(30.dp))
            }
        }
    }
}

@Composable
fun Features(modifier: Modifier) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size(50.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            items(getFeatures()) { features ->
                IndividualFeature(featureName = features.featureName)
            }
        }
    }
}

@Composable
fun IndividualFeature(featureName: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = Icons.Default.Image, contentDescription = featureName)
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            color = Dark_grey,
            text = featureName,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(0.dp, 100.dp)
        )
    }
}

@Composable
fun AnnouncementsContent(title: String, content: String) {
    Column(
        modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp, bottom = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, color = Messages_red, fontFamily = roboto_black)
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            text = content,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.height(40.dp)
        )
    }
}

fun getAnnouncements(): List<Announcements> {
    return listOf(
        Announcements(title = "Aviso 2", content = "Contenido 2"),
        Announcements(
            title = "Avisos",
            content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
        )
    )
}

data class Feature(
    val featureName: String,
    val image: String? = null,
    val destination: String? = null
)

fun getFeatures(): List<Feature> {
    return listOf(
        Feature(featureName = "Pagos y colegiaturas"),
        Feature(featureName = "Calificaciones"),
        Feature(featureName = "Horario"),
        Feature(featureName = "Calendario"),
        Feature(featureName = "Boletín"),
        Feature(featureName = "Círculo familiar"),
    )
}

fun getColor(studentGrade: String): Color {
    return when (studentGrade) {
        "Preescolar" -> {
            Preschool_color
        }
        "Primaria" -> {
            Primary_color
        }
        "Secundaria" -> {
            Middleschool_color
        }
        else -> {
            Color.Transparent
        }
    }
}