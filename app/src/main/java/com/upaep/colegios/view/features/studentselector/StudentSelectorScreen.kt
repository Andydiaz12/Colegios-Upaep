package com.upaep.colegios.view.features.studentselector

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.data.entities.studentselector.StudentsSelector
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.genericComponents.StudentCardInfo
import com.upaep.colegios.view.base.theme.*
import com.upaep.colegios.viewmodel.features.studentselector.StudentSelectorViewModel

@Composable
fun StudentSelectorScreen(
    navigation: NavHostController,
    studentSelectorViewModel: StudentSelectorViewModel = hiltViewModel()
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (header, message, students) = createRefs()
        Header(modifier = Modifier.constrainAs(header) {
            top.linkTo(parent.top)
        }, visibleImage = false, visibleMenu = false)
        MessageContainer(modifier = Modifier.constrainAs(message) {
            top.linkTo(header.bottom)
            bottom.linkTo(students.top)
        })
        StudentsContainer(modifier = Modifier.constrainAs(students) {
            top.linkTo(message.bottom)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, studentSelectorViewModel = studentSelectorViewModel, navigation = navigation)
    }
}

@Composable
fun StudentsContainer(
    modifier: Modifier,
    studentSelectorViewModel: StudentSelectorViewModel,
    navigation: NavHostController?
) {
    LazyColumn(
        modifier = modifier.padding(start = 32.dp, end = 32.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(studentSelectorViewModel.getStudents()) { student ->
            var levelColor: Color = Color.Transparent
            when (student.level) {
                "Preescolar" -> {
                    levelColor = Preschool_color
                }
                "Primaria" -> {
                    levelColor = Primary_color
                }
                "Secundaria" -> {
                    levelColor = Middleschool_color
                }
            }
            StudentCard(
                student,
                levelColor = levelColor,
                studentSelectorViewModel = studentSelectorViewModel,
                navigation = navigation
            )
        }
    }
}

@Composable
fun MessageContainer(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(50.dp))
        Text(
            "Hola Juan",
            fontFamily = roboto_black,
            color = Messages_red,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            "Elige al estudiante para consultar su información acadmémica y financiera",
            fontFamily = roboto_regular,
            color = Dark_grey,
            textAlign = TextAlign.Center,
            fontSize = 17.sp,
            letterSpacing = 0.sp,
            lineHeight = 23.sp
        )
        Spacer(modifier = Modifier.size(40.dp))
    }
}

@Composable
fun StudentCard(
    student: StudentsSelector,
    levelColor: Color,
    studentSelectorViewModel: StudentSelectorViewModel,
    navigation: NavHostController?
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
        .fillMaxWidth()
        .clickable {
            studentSelectorViewModel.navigateToHomeScreen(
                navigation = navigation,
                student = student
            )
        }, elevation = 5.dp
    ) {
        StudentCardInfo(
            studentName = student.name,
            studentLevel = student.level,
            levelColor = levelColor,
            studentGroup = student.group
        )
    }
}