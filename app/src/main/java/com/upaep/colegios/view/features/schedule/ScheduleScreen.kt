package com.upaep.colegios.view.features.schedule

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.fasterxml.jackson.databind.ser.Serializers.Base
import com.upaep.colegios.model.base.UserPreferences
import com.upaep.colegios.model.entities.schedule.DayClass
import com.upaep.colegios.view.base.defaultvalues.DefaultValues
import com.upaep.colegios.view.base.genericComponents.BottomSheetLayout
import com.upaep.colegios.view.base.genericComponents.ChildSelectorModal
import com.upaep.colegios.view.base.genericComponents.Header
import com.upaep.colegios.view.base.theme.Background
import com.upaep.colegios.view.base.theme.Dark_grey
import com.upaep.colegios.view.base.theme.Light_gray
import com.upaep.colegios.view.base.theme.Tenue_gray
import com.upaep.colegios.view.base.theme.Text_base_color
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_regular
import com.upaep.colegios.viewmodel.base.genericComponents.ChildDataViewModel
import com.upaep.colegios.viewmodel.features.schedule.ScheduleViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true)
@Composable
fun ScheduleScreen(
    scheduleViewModel: ScheduleViewModel = hiltViewModel(),
    childDataViewModel: ChildDataViewModel = hiltViewModel()
) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
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
        LoadedData(changeChildAction = { scope.launch { state.show() } })
    }
}

@Composable
fun LoadedData(scheduleViewModel: ScheduleViewModel = hiltViewModel(), changeChildAction: () -> Unit) {
    val dailyClasses by scheduleViewModel.scheduleList.observeAsState(emptyList())
    var dayName by rememberSaveable { mutableStateOf("LUN") }
    var selectedDay by rememberSaveable { mutableStateOf("lunes") }
    val listEquivalent by rememberSaveable { mutableStateOf(scheduleViewModel.days) }
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val levelColor =
        userPreferences.getBaseColor.collectAsState(initial = DefaultValues.initialColor).value
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, scheduleContent) = createRefs()
        Header(modifier = Modifier.constrainAs(header) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, screenName = "HORARIO", changeChildAction = changeChildAction)
        LazyColumn(modifier = Modifier
            .constrainAs(scheduleContent) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }
            .padding(horizontal = 12.dp)) {
            items(1) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TeacherInfo()
                    ScheduleCard(
                        dailyClasses = dailyClasses,
                        dayName = dayName,
                        onDayChanged = { day, pickedDay ->
                            dayName = day
                            selectedDay = pickedDay
                        },
                        childColor = levelColor,
                        selectedDay = selectedDay,
                        scheduleViewModel = scheduleViewModel,
                        listEquivalent = listEquivalent
                    )
                }
            }
        }
    }
}

@Composable
fun TeacherInfo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Image,
            contentDescription = "",
            modifier = Modifier.padding(start = 10.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = "María de Lourdes de los Santos Rosales",
                color = Color.Black,
                fontFamily = roboto_black,
                fontSize = 17.sp
            )
            Text(
                text = "Titular del grupo",
                color = Dark_grey,
                fontFamily = roboto_regular,
                fontSize = 14.sp
            )
        }
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp), color = Text_base_color
    )
}

@Composable
fun ScheduleCard(
    dailyClasses: List<DayClass>,
    onDayChanged: (String, String) -> Unit,
    dayName: String,
    childColor: Color,
    selectedDay: String,
    scheduleViewModel: ScheduleViewModel,
    listEquivalent: List<String>
) {
    Card(backgroundColor = Color.White, shape = RoundedCornerShape(10.dp)) {
        Column(modifier = Modifier.padding(top = 15.dp)) {
            Days(
                onDayChanged = onDayChanged,
                dayName = dayName,
                childColor = childColor,
                listEquivalent = listEquivalent
            )
            Spacer(modifier = Modifier.size(15.dp))
            dailyClasses.forEach() { todayClass ->
                val value =
                    scheduleViewModel.dayHour(dayClass = todayClass, selectedDay = selectedDay)
                if (!value.isNullOrEmpty()) {
                    val (formattedHour, actualDay) = scheduleViewModel.getHour(
                        hourRange = value,
                        selectedDay = selectedDay
                    )
                    SingleDayClass(
                        clasName = todayClass.className.trimEnd(),
                        hourRange = formattedHour,
                        selected = actualDay,
                        teacherName = todayClass.teacherName,
                        childColor = childColor
                    )
                }
            }
        }
    }
}

@Composable
fun SingleDayClass(
    clasName: String,
    hourRange: String,
    selected: Boolean,
    teacherName: String? = null,
    childColor: Color
) {
    val (textColor, classBackgroundColor) = returnClassColors(selected, childColor)
    Column(
        modifier = Modifier
            .background(classBackgroundColor.copy(alpha = 0.2f))
            .padding(horizontal = 10.dp, vertical = 15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = clasName,
                color = textColor,
                fontFamily = roboto_black,
                modifier = Modifier.weight(0.7f),
                fontSize = 14.sp
            )
            Text(
                text = hourRange,
                color = textColor,
                fontFamily = roboto_black,
                modifier = Modifier.weight(0.3f),
                fontSize = 14.sp
            )
        }
        if (!teacherName.isNullOrEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(if (selected) Color.White else Background),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = "Profesor",
                    modifier = Modifier.padding(start = 3.dp, top = 3.dp, bottom = 3.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = teacherName,
                    fontFamily = roboto_regular,
                    color = Dark_grey,
                    fontSize = 12.sp
                )
            }
        }
    }
    Divider(modifier = Modifier.fillMaxWidth(), color = Tenue_gray)
}

fun returnClassColors(selected: Boolean, childColor: Color): Pair<Color, Color> {
    return if (selected) Pair(Color.Black, childColor) else Pair(Text_base_color, Color.White)
}

@Composable
fun Days(
    onDayChanged: (String, String) -> Unit,
    dayName: String,
    childColor: Color,
    listEquivalent: List<String>
) {
    val coroutineScope = rememberCoroutineScope()
    val listDays = listOf("LUN", "MAR", "MIE", "JUE", "VIE")
    val positionCoordinates = MutableList(5) { 0F }
    var boxSize by rememberSaveable { mutableStateOf(Pair(0, 0)) }
    var actualDay by rememberSaveable { mutableStateOf(0) }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            listDays.forEachIndexed { index, value ->
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(7.dp))
                        .background(Background)
                        .onGloballyPositioned { coordinates ->
                            boxSize = Pair(coordinates.size.width, coordinates.size.height)
                            positionCoordinates[index] = coordinates.positionInRoot().x
                        }
                        .clickable {
                            onDayChanged(value, listEquivalent[index + 1])
                            actualDay = index
                            coroutineScope.launch {
                                launch {
                                    offsetX.animateTo(
                                        targetValue = positionCoordinates[actualDay] - positionCoordinates[0],
                                        animationSpec = tween(
                                            durationMillis = 200,
                                            easing = LinearEasing
                                        )
                                    )
                                }
                            }
                        }
                ) {
                    Text(
                        text = value,
                        modifier = Modifier.align(Alignment.Center),
                        color = Light_gray,
                        fontFamily = roboto_regular
                    )
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxWidth(0.195f)
                    .offset {
                        IntOffset(
                            offsetX.value.toInt(),
                            offsetY.value.toInt()
                        )
                    }
                    .clip(RoundedCornerShape(7.dp))
                    .background(childColor)
            ) {
                Text(
                    text = dayName,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                    fontFamily = roboto_black
                )
            }
        }
    }
}

fun getData(): List<DayClass> {
    return listOf()
}