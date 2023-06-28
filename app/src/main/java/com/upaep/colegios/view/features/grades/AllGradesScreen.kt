package com.upaep.colegios.view.features.grades

import android.util.Log
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.theme.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.colegios.model.base.UserPreferences
import com.upaep.colegios.model.entities.grades.GeneralGrades
import com.upaep.colegios.model.entities.grades.GradesTypes
import com.upaep.colegios.model.entities.grades.PartialGrade
import com.upaep.colegios.model.entities.grades.SingleClass
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import com.upaep.colegios.view.base.defaultvalues.DefaultValues
import com.upaep.colegios.view.base.genericComponents.ChildSelectorModal
import com.upaep.colegios.viewmodel.base.genericComponents.ChildDataViewModel
import com.upaep.colegios.viewmodel.features.grades.GradesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllGradesScreen(
    childDataViewModel: ChildDataViewModel = hiltViewModel(),
    gradesViewModel: GradesViewModel = hiltViewModel()
) {
    gradesViewModel.getSavedGrades()
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = state,
        scrimColor = Color.Black.copy(alpha = 0.6f),
        sheetContent = {
            ContentInfo()
        }
    ) {
        AllGradesScreenContent(gradesViewModel = gradesViewModel, onClickInfo = {
            scope.launch { state.show() }
        })
    }
}

@Preview
@Composable
fun ContentInfo() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
    ) {
        val startLine = createGuidelineFromStart(.15f)
        val endLine = createGuidelineFromEnd(.15f)
        val (title, content) = createRefs()
        Column(modifier = Modifier
            .constrainAs(title) {
                start.linkTo(startLine)
                end.linkTo(endLine)
            }
            .padding(top = 50.dp)) {
            Text(
                text = "Nomenclatura de logros",
                fontFamily = roboto_black,
                color = Blue_primary,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Divider(modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp), color = Light_gray)
        }

        Column(modifier = Modifier
            .constrainAs(content) {
                start.linkTo(startLine)
                end.linkTo(endLine)
                top.linkTo(title.bottom)
            }
            .padding(bottom = 80.dp)) {
            ModalSheetInfo(letter = "A", description = "Aprendizaje logrado")
            ModalSheetInfo(letter = "B", description = "En proceso")
            ModalSheetInfo(letter = "C", description = "Necesita apoyo")
        }
    }
}

@Composable
fun ModalSheetInfo(letter: String, description: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = letter, color = Dark_grey, fontFamily = roboto_black, fontSize = 30.sp)
        Spacer(modifier = Modifier.size(30.dp))
        Text(
            text = description,
            color = Dark_grey,
            fontFamily = roboto_regular,
            fontSize = 20.sp
        )
    }
    Spacer(modifier = Modifier.size(10.dp))
}

@Composable
fun AllGradesScreenContent(gradesViewModel: GradesViewModel, onClickInfo: () -> Unit) {
    val partial = gradesViewModel.getSelectedPartial()
    val grades by gradesViewModel.grades.observeAsState()
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val color =
        userPreferences.getBaseColor.collectAsState(initial = DefaultValues.initialColor).value
    Column(modifier = Modifier.fillMaxSize()) {
        Header(screenName = "CALIFICACIONES", changeChild = false)
        CardContainer(
            modifier = Modifier.weight(1f),
            color = color,
            grades = grades!!,
            partial = partial,
            onClickInfo = onClickInfo
        )
    }
}

@Composable
fun CardContainer(
    modifier: Modifier,
    color: Color,
    grades: GeneralGrades,
    partial: Int,
    onClickInfo: () -> Unit
) {
    val openedList =
        rememberSaveable { List(grades.reportCard.size) { mutableStateOf(false) } }
    var isAnimated by rememberSaveable { mutableStateOf(true) }
    val scrollState = rememberLazyListState()
    LazyColumn(
        modifier = modifier
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        state = scrollState
    ) {
        itemsIndexed(grades.reportCard) { index, grade ->
            val opened = openedList[index]
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier,
                backgroundColor = Color.White
            ) {
                Column() {
                    CardHeader(
                        onClick = {
                            if (!opened.value) {
                                isAnimated = false
                                openedList.map { it.value = false }
                            } else {
                                isAnimated = true;
                            }
                            opened.value = !opened.value
                        },
                        opened = opened.value,
                        color = color,
                        gradeType = grade.name,
                        average = "10",
                        onClickInfo = onClickInfo
                    )
                    CardContent(
                        opened = opened.value,
                        animation = isAnimated,
                        grades = grade.partials,
                        partial = partial
                    )
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
    average: String,
    onClickInfo: () -> Unit
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
        ScoreObtained(color = color, score = "10", onClickInfo = onClickInfo)
    }
}

@Composable
fun CardContent(opened: Boolean, animation: Boolean, grades: List<PartialGrade>, partial: Int) {
    AnimatedVisibility(
        visible = opened,
        enter = expandVertically(expandFrom = Alignment.Top),
        exit = if (animation) shrinkVertically(shrinkTowards = Alignment.Bottom) else ExitTransition.None
    ) {
        Column(modifier = Modifier.animateContentSize()) {
            for (grade in grades[partial].classes.withIndex()) {
                Grade(
                    className = grade.value.className,
                    score = grade.value.score.toString(),
                    coment = ""
                )
                if (grade.index < grades.size) Divider()
            }
        }
    }
}

@Composable
fun ScoreObtained(color: Color, score: String, onClickInfo: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(90.dp)
            .width(110.dp)
            .background(color = color)
            .padding(10.dp)
    ) {
        if (true) {
            Surface(color = color) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        onClickInfo()
                    })
            }
        } else {
            Text(text = score, fontFamily = roboto_black, fontSize = 30.sp, color = Color.White)
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.rotate(degrees = -90F)
            )
        }
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