package com.upaep.colegios.view.features.grades

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Comment
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upaep.colegios.data.entities.grades.Grade
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.theme.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.colegios.data.entities.grades.GeneralGrades
import com.upaep.colegios.viewmodel.features.grades.GradesViewModel

@Preview(showSystemUi = true)
@Composable
fun AllGradesScreen(gradesViewModel: GradesViewModel = hiltViewModel()) {
    val color = Color.Magenta
    Column(modifier = Modifier.fillMaxSize()) {
        Header(screenName = "CALIFICACIONES")
        CardContainer(modifier = Modifier.weight(1f), color = color)
    }
}

@Composable
fun CardContainer(modifier: Modifier, color: Color) {
    val getGradesType = emptyList<GeneralGrades>()
    val openedList = rememberSaveable { List(getGradesType.size) { mutableStateOf(false) } }
    var isAnimated by rememberSaveable { mutableStateOf(true) }
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        modifier = modifier
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        state = scrollState
    ) {
        itemsIndexed(getGradesType) { index, grade ->
            val opened = openedList[index]
            Card(shape = RoundedCornerShape(10.dp), modifier = Modifier) {
                Column() {
//                    CardHeader(
//                        onClick = {
//                            if (!opened.value) {
//                                isAnimated = false
//                                openedList.map { it.value = false }
//                            } else {
//                                isAnimated = true;
//                            }
//                            opened.value = !opened.value
//                        },
//                        opened = opened.value,
//                        color = color,
//                        gradeType = grade.,
//                        average = grade.average
//                    )
//                    CardContent(
//                        opened = opened.value,
//                        animation = isAnimated,
//                        grades = grade.grades
//                    )
                }
            }
        }
    }
}

@Composable
fun CardHeader(
    onClick: () -> Unit,
    opened: Boolean,
    color: Color,
    gradeType: String,
    average: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(90.dp)
        ) {
            this@Row.AnimatedVisibility(
                visible = opened,
                enter = slideInHorizontally(initialOffsetX = { it }),
                exit = slideOutHorizontally(targetOffsetX = { it })
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                )
            }
            Text(
                text = gradeType,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center),
                color = if (opened) Color.White else Dark_grey,
                fontFamily = roboto_medium,
                fontSize = 16.sp,
                lineHeight = 21.sp
            )
        }
        ScoreObtained(color = color)
    }
}

@Composable
fun CardContent(opened: Boolean, animation: Boolean, grades: List<Grade>) {
    AnimatedVisibility(
        visible = opened,
        enter = expandVertically(expandFrom = Alignment.Top),
        exit = if (animation) shrinkVertically(shrinkTowards = Alignment.Bottom) else ExitTransition.None
    ) {
        Column(modifier = Modifier.animateContentSize()) {
            for (grade in grades.withIndex()) {
//                Grade(
//                    grade.value.
//                    className = grade.value.name,
//                    score = grade.value.score,
//                    coment = grade.value.coment
//                )
                if (grade.index < grades.size) Divider()
            }
        }
    }
}

@Composable
fun ScoreObtained(color: Color) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(90.dp)
            .width(110.dp)
            .background(color = color)
            .padding(10.dp)
    ) {
        Text(text = "9.4", fontFamily = roboto_black, fontSize = 30.sp, color = Color.White)
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.rotate(degrees = -90F)
        )
    }
}

@Composable
fun Grade(className: String, coment: String?, score: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = className,
            modifier = Modifier.padding(end = 5.dp),
            color = Text_base_color,
            fontFamily = roboto_condensed
        )
        if (coment != null) {
            Icon(
                imageVector = Icons.Default.Comment,
                contentDescription = "Comentario",
                tint = Upaep_red
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = score, fontFamily = roboto_medium, color = Dark_grey)
    }
}