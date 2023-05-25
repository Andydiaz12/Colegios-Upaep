package com.upaep.colegios.view.features.grades

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.upaep.colegios.data.entities.grades.GeneralGrades
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.theme.Text_base_color
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_medium
import com.upaep.colegios.view.base.theme.roboto_regular

@Preview(showSystemUi = true)
@Composable
fun GradesScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, teacher, grades) = createRefs()
        Header(modifier = Modifier.constrainAs(header) {
            top.linkTo(parent.top)
        }, screenName = "CALIFICACIONES")
        TeacherContainer(modifier = Modifier.constrainAs(teacher) {
            top.linkTo(header.bottom)
        })
        GradesContainer(modifier = Modifier.constrainAs(grades) {
            top.linkTo(teacher.bottom)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints
        })
    }
}

@Composable
fun GradesContainer(modifier: Modifier) {
    LazyColumn(
        modifier = modifier.padding(top = 50.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(getData()) { grade ->
//            GradeCard(description = grade.description, average = grade.average)
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
fun GradeCard(description: String, average: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = description,
                    modifier = Modifier.padding(start = 30.dp),
                    fontSize = 23.sp,
                    fontFamily = roboto_regular
                )
            }
            ScoreObtained(average = average)
        }
    }
}

@Composable
fun ScoreObtained(average: String) {
    val color = Color.Magenta
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

fun getData(): List<GeneralGrades> {
    return listOf(
//        GeneralGrades(description = "3º Trimestre", average = "9.2"),
//        GeneralGrades(description = "2º Trimestre", average = "8.1"),
//        GeneralGrades(description = "1º Trimestre", average = "9"),
    )
}