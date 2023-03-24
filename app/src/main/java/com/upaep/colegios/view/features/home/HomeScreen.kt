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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Replay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.upaep.colegios.view.base.genericComponents.HeaderLeftLogo
import com.upaep.colegios.view.base.genericComponents.StudentCardInfo
import com.upaep.colegios.view.base.theme.Dark_grey
import com.upaep.colegios.view.base.theme.Messages_red
import com.upaep.colegios.view.base.theme.Preschool_color
import com.upaep.colegios.view.base.theme.roboto_black
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Preview(showSystemUi = true)
@Composable
fun HomeScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (upperSection, lowerSection) = createRefs()
        ContainerHeaderAndStudent(modifier = Modifier
            .constrainAs(upperSection) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .fillMaxWidth())
        NewsAndFeatures(modifier = Modifier
            .constrainAs(lowerSection) {
                top.linkTo(upperSection.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
            }
            .padding(start = 32.dp, end = 32.dp))
    }

}

@Composable
fun NewsAndFeatures(modifier: Modifier) {
    ConstraintLayout(modifier = modifier) {
        val (news, features) = createRefs()
        News(modifier = Modifier.constrainAs(news) {
            top.linkTo(parent.top)
        })
        Features(modifier = Modifier.constrainAs(features) {
            top.linkTo(news.bottom)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
        })
    }
}

@Composable
fun ContainerHeaderAndStudent(modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        HeaderLeftLogo(modifier = modifier.padding(start = 32.dp, end = 32.dp))
        StudentDescAndChange(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun StudentDescAndChange(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Preschool_color)
            .padding(end = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StudentCardInfo(
            studentName = "Cesar López Valeriano",
            studentLevel = "Preescolar",
            levelColor = Color.White,
            studentGroup = "3ºB",
            selectorScreen = false,
            backgroundColor = Preschool_color,
            defaultTextColor = Color.White,
            imgSize = 80.dp
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
fun News(modifier: Modifier) {
    var news = getNews()
    val pagerState = rememberPagerState()
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size(30.dp))
        Card(
            modifier = modifier.clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) {
                //navigation to screen with news complete
              Log.i("clickingEvt", pagerState.currentPage.toString())
              Log.i("clickingEvt", news[pagerState.currentPage].toString())
            }, shape = RoundedCornerShape(10.dp), elevation = 3.dp
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HorizontalPager(
                    count = news.size,
                    state = pagerState
                ) { page ->
                    NewsContent(title = news[page].title, content = news[page].content)
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
            verticalArrangement = Arrangement.spacedBy(70.dp)
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
fun NewsContent(title: String, content: String) {
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

data class News(
    val title: String,
    val content: String
)

fun getNews(): List<News> {
    return listOf(
        News(title = "Aviso 2", content = "Contenido 2"),
        News(
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