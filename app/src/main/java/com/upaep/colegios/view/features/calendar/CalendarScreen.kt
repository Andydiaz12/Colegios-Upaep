package com.upaep.colegios.view.features.calendar

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.model.base.UserPreferences
import com.upaep.colegios.model.entities.calendar.GoogleEvents
import com.upaep.colegios.view.base.defaultvalues.DefaultValues
import com.upaep.colegios.view.base.genericComponents.ChildSelectorModal
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.theme.Background
import com.upaep.colegios.view.base.theme.Dark_grey
import com.upaep.colegios.view.base.theme.Tenue_gray
import com.upaep.colegios.view.base.theme.Upaep_grey
import com.upaep.colegios.view.base.theme.Upaep_red
import com.upaep.colegios.view.base.theme.firasans_bold
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_regular
import com.upaep.colegios.viewmodel.base.genericComponents.ChildDataViewModel
import com.upaep.colegios.viewmodel.features.calendar.CalendarViewModel
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalendarScreen(
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    navigation: NavHostController,
    childDataViewModel: ChildDataViewModel = hiltViewModel()
) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val levelColor =
        userPreferences.getBaseColor.collectAsState(initial = DefaultValues.initialColor).value

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
        CalendarScreenContent(
            calendarViewModel = calendarViewModel,
            navigation = navigation,
            levelColor = levelColor,
            changeChildIcon = {
                scope.launch {
                    state.show()
                }
            }
        )
    }
}

@Composable
fun CalendarScreenContent(
    calendarViewModel: CalendarViewModel,
    navigation: NavHostController,
    levelColor: Color,
    changeChildIcon: () -> Unit
) {
    val googleEvents by calendarViewModel.calendarEvents.observeAsState(emptyList())
    val daysWithEvents by calendarViewModel.daysWithEvents.observeAsState(emptyList())
    var selectedDay by rememberSaveable { mutableStateOf(calendarViewModel.getActualDay()) }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, card) = createRefs()
        Header(
            screenName = "CALENDARIO",
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            navigation = navigation,
            changeChildAction = changeChildIcon
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(card) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
                .padding(top = 20.dp)
        ) {
            item {
                CompleteCalendarContainer(
                    calendarViewModel = calendarViewModel,
                    events = googleEvents,
                    daysWithEvents = daysWithEvents,
                    clickedDay = { selectedDay = it },
                    selectedDay = selectedDay,
                    levelColor = levelColor
                )
            }
        }
    }
}

@Composable
fun CompleteCalendarContainer(
    calendarViewModel: CalendarViewModel,
    events: List<GoogleEvents>,
    daysWithEvents: List<Int>,
    clickedDay: (Int) -> Unit,
    selectedDay: Int,
    levelColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(vertical = 20.dp, horizontal = 15.dp)
        ) {
            CalendarContainer(
                calendarViewModel = calendarViewModel,
                daysWithEvents = daysWithEvents,
                clickedDay = clickedDay,
                selectedDay = selectedDay,
                levelColor = levelColor
            )
            Divider(modifier = Modifier.padding(vertical = 25.dp), color = Tenue_gray)
            EventDescriptionContainer(
                events = events,
                selectedDay = selectedDay,
                calendarViewModel = calendarViewModel
            )
        }
    }
}

@Composable
fun CalendarContainer(
    calendarViewModel: CalendarViewModel,
    daysWithEvents: List<Int>,
    clickedDay: (Int) -> Unit,
    selectedDay: Int,
    levelColor: Color
) {
    val calendarConfig by calendarViewModel.calendarConfiguration.observeAsState(calendarViewModel.getCalendarConfiguration())
    val actualMonth = calendarViewModel.getActualMonth()
    val actualDay = calendarViewModel.getActualDay()
    val actualYear = calendarViewModel.getActualYear()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CalendarMonthContainer(
            monthName = calendarConfig.monthName,
            calendarViewModel = calendarViewModel,
            year = calendarConfig.yearInInt.toString()
        )
        Spacer(modifier = Modifier.size(20.dp))
        CalendarUpaep(
            startDay = calendarConfig.startDay,
            numRows = calendarConfig.numRows,
            daysMatrix = calendarConfig.daysMatrix,
            monthSelected = calendarConfig.monthNumber,
            actualMonth = actualMonth,
            actualDay = actualDay,
            daysWithEvents = daysWithEvents,
            clickedDay = clickedDay,
            selectedDay = selectedDay,
            levelColor = levelColor,
            selectedYear = calendarConfig.yearInInt,
            actualYear = actualYear
        )
    }
}

@Composable
fun EventDescriptionContainer(
    events: List<GoogleEvents>,
    selectedDay: Int,
    calendarViewModel: CalendarViewModel
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        if (!calendarViewModel.getIfEventsInDay(selectedDay)) {
            Text(
                "EVENTOS",
                modifier = Modifier.padding(start = 8.dp),
                color = Upaep_red,
                fontFamily = roboto_black
            )
        }
        events.forEach() { event ->
            if (calendarViewModel.dayWithinRange(selectedDay = selectedDay, event = event)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    EventDot(dotSize = 6.dp, modifier = Modifier.padding(top = 5.dp))
                    Spacer(modifier = Modifier.size(8.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = event.summary ?: "",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "${event.startDateDesc} - ${event.endDateDesc}",
                            fontWeight = FontWeight.Light,
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
        if (calendarViewModel.getIfEventsInDay(selectedDay)) {
            NoneEvents()
        }
    }
}

@Composable
fun NoneEvents() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "No tienes eventos para este día",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(50.dp),
            color = Upaep_grey,
            fontFamily = roboto_regular,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun CalendarMonthContainer(monthName: String, calendarViewModel: CalendarViewModel, year: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(50))
            .background(Background)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MonthMovement(
            modifier = Modifier.weight(1f),
            calendarViewModel = calendarViewModel,
            movement = "prev",
            movementDesc = "anterior"
        )
        MonthName(monthName = monthName, modifier = Modifier.weight(5f), year = year)
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
            .size(30.dp)
            .rotate(degrees = if (movement == "prev") 180f else 0f)
            .clickable { calendarViewModel.getOtherMonth(movement = movement) },
        imageVector = Icons.Default.ArrowRight,
        contentDescription = "mes ${movementDesc}",
        tint = Upaep_red
    )
}

@Composable
fun MonthName(monthName: String, modifier: Modifier, year: String) {
    Text(
        text = "$monthName $year",
        modifier = modifier,
        textAlign = TextAlign.Center,
        fontFamily = firasans_bold,
        fontSize = 16.sp,
        color = Color.Black
    )
}

@Composable
fun CalendarUpaep(
    startDay: Int,
    numRows: Int,
    daysMatrix: Array<IntArray>,
    actualMonth: Int,
    actualDay: Int,
    monthSelected: Int,
    daysWithEvents: List<Int>,
    clickedDay: (Int) -> Unit,
    selectedDay: Int,
    levelColor: Color,
    selectedYear: Int,
    actualYear: Int
) {
    Column() {
        Row(Modifier.fillMaxWidth()) {
            for (i in 0 until 7) {
                val dayOfWeek = ((i + startDay - 1) % 7) + 1
                Text(
                    text = DateFormatSymbols().shortWeekdays[dayOfWeek].uppercase().substring(0, 1),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = Upaep_red,
                    fontFamily = firasans_bold
                )
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        for (row in 0 until numRows) {
            Row(Modifier.fillMaxWidth()) {
                for (col in 0 until 7) {
                    val dayOfMonth = daysMatrix[row][col]
                    if (dayOfMonth != 0) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(45.dp)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(
                                    if (selectedDay == dayOfMonth) levelColor
                                    else if (daysWithEvents.size > dayOfMonth && daysWithEvents[dayOfMonth] == 1) Background
                                    else Color.Transparent
                                )
                                .clickable { clickedDay(dayOfMonth) }
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = dayOfMonth.toString(),
                                    textAlign = TextAlign.Center,
                                    fontFamily = if ((actualDay == dayOfMonth && actualMonth == monthSelected && actualYear == selectedYear) || selectedDay == dayOfMonth) roboto_black else roboto_regular,
                                    color =
                                    if (selectedDay == dayOfMonth) Color.White
                                    else if (actualDay == dayOfMonth && actualMonth == monthSelected && actualYear == selectedYear) Color.Black
                                    else if (daysWithEvents.size > dayOfMonth && daysWithEvents[dayOfMonth] == 1) levelColor
                                    else Dark_grey
                                )
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