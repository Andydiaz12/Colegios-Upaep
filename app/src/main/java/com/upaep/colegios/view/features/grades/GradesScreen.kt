package com.upaep.colegios.view.features.grades

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.model.base.UserPreferences
import com.upaep.colegios.model.entities.grades.GeneralGrades
import com.upaep.colegios.model.entities.grades.GradesTypes
import com.upaep.colegios.model.entities.grades.PartialGrade
import com.upaep.colegios.model.entities.grades.SingleClass
import com.upaep.colegios.view.base.defaultvalues.DefaultValues
import com.upaep.colegios.view.base.genericComponents.ChildSelectorModal
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.navigation.Routes
import com.upaep.colegios.view.base.theme.Text_base_color
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_medium
import com.upaep.colegios.view.base.theme.roboto_regular
import com.upaep.colegios.viewmodel.base.genericComponents.ChildDataViewModel
import com.upaep.colegios.viewmodel.features.grades.GradesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GradesScreen(
    childDataViewModel: ChildDataViewModel = hiltViewModel(),
    gradesViewModel: GradesViewModel = hiltViewModel(),
    navigation: NavHostController
) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = state,
        scrimColor = Color.Black.copy(alpha = 0.6f),
        sheetContent = {
            ChildSelectorModal(onClick = { student, color ->
                childDataViewModel.changeSelectedStudent(student, color)
                scope.launch { state.hide() }
            })
        }
    ) {
        GradesContent(gradesViewModel = gradesViewModel, navigation = navigation, onChangeChild = {
            scope.launch { state.show() }
        })
    }
}

@Composable
fun GradesContent(
    gradesViewModel: GradesViewModel,
    navigation: NavHostController,
    onChangeChild: () -> Unit
) {
    gradesViewModel.getGradesData()
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val childColor =
        userPreferences.getBaseColor.collectAsState(initial = DefaultValues.initialColor).value
    val gradesResponse by gradesViewModel.grades.observeAsState()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, teacher, grades) = createRefs()
        Header(modifier = Modifier.constrainAs(header) {
            top.linkTo(parent.top)
        }, screenName = "CALIFICACIONES", changeChildAction = onChangeChild)
        TeacherContainer(modifier = Modifier.constrainAs(teacher) {
            top.linkTo(header.bottom)
        })
        GradesContainer(modifier = Modifier.constrainAs(grades) {
            top.linkTo(teacher.bottom)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
        }, grades = gradesResponse, onCardClick = { selection ->
            Log.i("partialControl1", selection.toString())
            gradesViewModel.selectedPartial(selection)
            Log.i("partialControl2", gradesViewModel.getSelectedPartial().toString())
            navigation.navigate(Routes.AllGradesScreen.routes)
        }, color = childColor)
    }
}

@Composable
fun GradesContainer(
    modifier: Modifier,
    grades: GeneralGrades?,
    onCardClick: (Int) -> Unit,
    color: Color
) {
    LazyColumn(
        modifier = modifier.padding(top = 50.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(grades?.reportCard?.get(0)?.partials ?: emptyList()) { grade ->
            GradeCard(
                partial = grade.partial,
                average = grade.average.toString(),
                onCardClick = onCardClick,
                color = color
            )
        }
    }
}

@Composable
fun TeacherContainer(modifier: Modifier) {
    Column(modifier = modifier.padding(top = 25.dp, end = 32.dp, start = 32.dp)) {
        Row(modifier = Modifier.padding(bottom = 20.dp)) {
            Icon(
                imageVector = Icons.Default.VerifiedUser,
                contentDescription = "Titular",
                modifier = Modifier.width(65.dp)
            )
            Column(modifier = Modifier.padding(end = 40.dp)) {
                Text(
                    text = "María de Lourdes de los Santos Rosales",
                    fontFamily = roboto_black,
                    fontSize = 16.sp
                )
                Text(text = "Titular del grupo", fontFamily = roboto_medium, fontSize = 13.sp)
            }
        }
        Divider(color = Text_base_color)
    }
}

@Composable
fun GradeCard(partial: Int, average: String, onCardClick: (Int) -> Unit, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick(partial) }
            .padding(horizontal = 32.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${partial} Parcial",
                    modifier = Modifier.padding(start = 30.dp),
                    fontSize = 23.sp,
                    fontFamily = roboto_regular
                )
            }
            ScoreObtained(average = average, color = color)
        }
    }
}

@Composable
fun ScoreObtained(average: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(90.dp)
            .width(120.dp)
            .drawBehind {
                drawLine(
                    color = color,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 8f
                )
            }
            .padding(10.dp)
    ) {
        Text(text = average, fontFamily = roboto_black, fontSize = 30.sp, color = color)
        Text(
            text = "Promedio general",
            fontFamily = roboto_black,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}