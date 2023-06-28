package com.upaep.colegios.view.base.genericComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.colegios.model.base.UserPreferences
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import com.upaep.colegios.view.base.defaultvalues.DefaultValues
import com.upaep.colegios.view.base.theme.Background
import com.upaep.colegios.view.base.theme.Middleschool_color
import com.upaep.colegios.view.base.theme.Preschool_color
import com.upaep.colegios.view.base.theme.Primary_color
import com.upaep.colegios.viewmodel.base.genericComponents.ChildSelectorModalViewModel

@Composable
fun ChildSelectorModal(
    childSelectorModalViewModel: ChildSelectorModalViewModel = hiltViewModel(),
    onClick: (StudentsSelector, Color) -> Unit
) {
    val context = LocalContext.current
    val dataStore = UserPreferences(context)
    val students by childSelectorModalViewModel.students.observeAsState(emptyList())
    val studentSelected by dataStore.getSelectedStudent.collectAsState(initial = DefaultValues.initialStudentSelected)
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(horizontal = 35.dp),
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
        items(students) { student ->
            StudentCardInfo(
                levelColor = when (student.school.lowercase()) {
                    "preescolar" -> {
                        Preschool_color
                    }

                    "primaria" -> {
                        Primary_color
                    }

                    else -> {
                        Middleschool_color
                    }
                },
                studentGroup = "${student.grade} ${student.group}",
                studentName = "${student.name} ${student.paternSurname} ${student.motherSurname}",
                studentLevel = student.school.replaceFirstChar { it.uppercase() },
                selectorScreen = false,
                imgSize = 65.dp,
                backgroundColor = Background,
                maxWidth = false,
                imageTextSeparation = 25.dp,
                blockedElement = (studentSelected!!.persclv == student.persclv),
                textSize = 13.sp,
                spacerSize = 1.dp,
                onClick = { newStudent, color ->
                    onClick(newStudent, color)
                    childSelectorModalViewModel.updateChildSelected(student)
                },
                selectedStudent = student
            )
        }
        item {
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}