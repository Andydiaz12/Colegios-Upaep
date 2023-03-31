package com.upaep.colegios.view.features.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.upaep.colegios.viewmodel.features.calendar.CalendarViewModel
import java.text.DateFormatSymbols

@Preview(showSystemUi = true)
@Composable
fun CalendarScreen() {
    val calendarViewModel = CalendarViewModel()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        CompleteCalendarContainer(
            calendarViewModel = calendarViewModel,
            modifier = Modifier.padding(32.dp)
        )
    }
}

@Composable
fun CompleteCalendarContainer(calendarViewModel: CalendarViewModel, modifier: Modifier) {
    Column(modifier = modifier) {
        ContainerLevelName()
        CalendarContainer(calendarViewModel = calendarViewModel)
        Divider(modifier = Modifier.padding(vertical = 25.dp))
        EventDescriptionContainer()
    }
}

@Composable
fun ContainerLevelName() {
    Text(
        text = "PRIMARIA PRESENCIAL",
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)
            .padding(vertical = 15.dp, horizontal = 10.dp),
        color = Color.White
    )
}

@Composable
fun CalendarContainer(calendarViewModel: CalendarViewModel) {
    val calendarConfig by calendarViewModel.calendarConfiguration.observeAsState(calendarViewModel.getCalendarConfiguration())
    val actualMonth = calendarViewModel.getActualMonth()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CalendarMonthContainer(
            monthName = calendarConfig.monthName,
            calendarViewModel = calendarViewModel
        )
        CalendarUpaep(
            startDay = calendarConfig.startDay,
            numRows = calendarConfig.numRows,
            daysMatrix = calendarConfig.daysMatrix,
            actualMonth = actualMonth
        )
    }
}

@Composable
fun EventDescriptionContainer() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(4) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                EventDot(dotSize = 10.dp, modifier = Modifier.padding(top = 5.dp))
                Spacer(modifier = Modifier.size(8.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Consejo Técnico Escolar / Suspensión de clase así que no vengan",
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "16 mar 2023 - 17 mar 2023", fontWeight = FontWeight.Light)
                }
            }
        }

    }
}


@Composable
fun CalendarMonthContainer(monthName: String, calendarViewModel: CalendarViewModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MonthMovement(
            modifier = Modifier.weight(1f),
            calendarViewModel = calendarViewModel,
            movement = "prev",
            movementDesc = "anterior"
        )
        MonthName(monthName = monthName, modifier = Modifier.weight(5f))
        MonthMovement(
            modifier = Modifier.weight(1f),
            calendarViewModel = calendarViewModel,
            movement = "next",
            movementDesc = "siguiente"
        )
    }
}

@Composable
fun MonthMovement(
    modifier: Modifier,
    calendarViewModel:
    CalendarViewModel, movement: String,
    movementDesc: String
) {
    Icon(
        modifier = modifier
            .rotate(degrees = if (movement == "prev") 180f else 0f)
            .clickable { calendarViewModel.getOtherMonth(movement = movement) },
        imageVector = Icons.Default.ArrowRight,
        contentDescription = "mes ${movementDesc}"
    )
}

@Composable
fun MonthName(monthName: String, modifier: Modifier) {
    Text(text = monthName, modifier = modifier, textAlign = TextAlign.Center)
}

@Composable
fun CalendarUpaep(startDay: Int, numRows: Int, daysMatrix: Array<IntArray>, actualMonth: Int) {
    Column() {
        Row(Modifier.fillMaxWidth()) {
            for (i in 0 until 7) {
                val dayOfWeek = ((i + startDay - 1) % 7) + 1
                Text(
                    text = DateFormatSymbols().shortWeekdays[dayOfWeek].uppercase().substring(0, 1),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
        for (row in 0 until numRows) {
            Row(Modifier.fillMaxWidth()) {
                for (col in 0 until 7) {
                    val dayOfMonth = daysMatrix[row][col]
                    if (dayOfMonth != 0) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(45.dp)
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = dayOfMonth.toString(),
                                    textAlign = TextAlign.Center
                                )
                                if (true) {
                                    Spacer(modifier = Modifier.size(3.dp))
                                    EventDot(dotSize = 5.dp)
                                }
                            }
                        }
                    } else {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun EventDot(dotSize: Dp, modifier: Modifier = Modifier) {
    Surface(shape = RoundedCornerShape(50), modifier = modifier.size(dotSize), color = Color.Red) {}
}