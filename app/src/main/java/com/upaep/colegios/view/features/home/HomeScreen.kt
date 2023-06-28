package com.upaep.colegios.view.features.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.colegios.model.entities.announcements.Announcements
import com.upaep.colegios.view.base.genericComponents.*
import com.upaep.colegios.view.base.navigation.Routes
import com.upaep.colegios.view.base.theme.*
import com.upaep.colegios.R
import com.upaep.colegios.model.base.UserPreferences
import com.upaep.colegios.model.entities.studentselector.StudentsSelector
import com.upaep.colegios.view.base.defaultvalues.DefaultValues
import com.upaep.colegios.viewmodel.base.genericComponents.ChildDataViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    theme: ThemeSchema,
    navigation: NavHostController,
    childDataViewModel: ChildDataViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val dataStore = UserPreferences(context)
    val studentData =
        dataStore.getSelectedStudent.collectAsState(DefaultValues.initialStudentSelected)
    val levelColor = dataStore.getBaseColor.collectAsState(initial = Color.Transparent)
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
        HomeScreenContent(
            levelColor = levelColor.value,
            navigation = navigation,
            theme = theme,
            studentData = studentData.value,
            changeStudentEvent = {
                scope.launch {
                    state.show()
                }
            }
        )
    }
}

@Composable
fun HomeScreenContent(
    levelColor: Color?,
    navigation: NavHostController,
    theme: ThemeSchema,
    changeStudentEvent: () -> Unit,
    studentData: StudentsSelector?,
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (upperSection, lowerSection) = createRefs()
        ContainerHeaderAndStudent(
            studentData = studentData,
            levelColor = levelColor ?: Color.Transparent,
            modifier = Modifier
                .constrainAs(upperSection) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            changeStudentEvent = changeStudentEvent, navigation = navigation)
        AnnouncementsAndFeatures(modifier = Modifier
            .constrainAs(lowerSection) {
                top.linkTo(upperSection.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
            }
            .padding(horizontal = 20.dp), theme = theme, navigation = navigation)
    }
}

@Composable
fun AnnouncementsAndFeatures(
    theme: ThemeSchema,
    modifier: Modifier,
    navigation: NavHostController
) {
    val features = getFeatures()
    LazyColumn(modifier = modifier) {
        items(count = 1) {
            AnnouncementsSection(modifier = Modifier, theme = theme, navigation)
            Spacer(modifier = Modifier.size(30.dp))
            for (i in features.indices step 2) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (i < features.size) IndividualFeature(
                        modifier = Modifier.weight(1f),
                        feature = features[i],
                        navigation = navigation
                    )
                    if (i + 1 < features.size) {
                        IndividualFeature(
                            feature = features[i + 1],
                            modifier = Modifier.weight(1f),
                            navigation = navigation
                        )
                    } else {
                        Box(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.size(50.dp))
            }
        }
    }
}


@Composable
fun ContainerHeaderAndStudent(
    levelColor: Color,
    modifier: Modifier,
    studentData: StudentsSelector?,
    changeStudentEvent: () -> Unit,
    navigation: NavHostController
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Header(
            visibleNameDesc = false,
            backScreen = false,
            menuHamburger = true,
            changeChild = false,
            navigation = navigation
        )
        StudentDescAndChange(
            studentData = studentData,
            modifier = Modifier.fillMaxWidth(),
            changeStudentEvent = changeStudentEvent,
            levelColor = levelColor
        )
    }
}

@Composable
fun StudentDescAndChange(
    modifier: Modifier,
    studentData: StudentsSelector?,
    changeStudentEvent: () -> Unit,
    levelColor: Color
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(levelColor)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StudentCardInfo(
            studentName = "${studentData?.name} ${studentData?.paternSurname} ${studentData?.motherSurname}",
            studentLevel = studentData?.school ?: "Preescolar",
            levelColor = Color.White,
            studentGroup = "${studentData?.grade}${studentData?.group}",
            selectorScreen = false,
            backgroundColor = levelColor,
            defaultTextColor = Color.White,
            imgSize = 60.dp,
            maxWidth = true,
            modifier = Modifier.weight(0.8f),
            spacerSize = 2.dp,
            textSize = 13.sp,
        )
        ChangeStudent(modifier = Modifier.weight(0.2f), changeStudentEvent = changeStudentEvent)
    }
}

@Composable
fun ChangeStudent(modifier: Modifier, changeStudentEvent: () -> Unit) {
    Column(
        modifier = modifier.clickable { changeStudentEvent() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icono_cambiar_tutorado),
            contentDescription = "seleccionar hijo",
            modifier = Modifier.size(40.dp),
            tint = Color.White
        )
        Text(
            text = "Cambiar\nestudiante",
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnnouncementsSection(modifier: Modifier, theme: ThemeSchema, navigation: NavHostController) {
    val announcements = getAnnouncements()
    val pagerState = rememberPagerState()
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size(20.dp))
        Card(
            modifier = modifier.clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) {
                navigation.navigate(Routes.AnnouncementScreen.createRoute(announcements[pagerState.currentPage]))
            },
            shape = RoundedCornerShape(10.dp),
            elevation = 3.dp,
            backgroundColor = theme.backgroundColor
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HorizontalPager(
                    pageCount = announcements.size,
                    state = pagerState
                ) { page ->
                    AnnouncementsContent(
                        title = announcements[page].title,
                        content = announcements[page].content
                    )
                }
                /*HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = Messages_red,
                    inactiveColor = Dark_grey,
                )*/
                Spacer(modifier = Modifier.size(30.dp))
            }
        }
    }
}

@Composable
fun IndividualFeature(
    modifier: Modifier = Modifier,
    feature: Feature,
    navigation: NavHostController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable {
            if (feature.destination != null) navigation.navigate(feature.destination)
        }
    ) {
        Image(
            painter = painterResource(id = feature.image),
            contentDescription = feature.featureName,
            modifier = Modifier.size(70.dp)
        )
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            color = Dark_grey,
            text = feature.featureName,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(0.dp, 120.dp),
            fontSize = 17.sp,
            fontFamily = roboto_medium
        )
    }
}

@Composable
fun AnnouncementsContent(title: String, content: String) {
    Column(
        modifier = Modifier.padding(top = 15.dp, start = 30.dp, end = 30.dp, bottom = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, color = Messages_red, fontFamily = roboto_black, fontSize = 17.sp)
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            text = content,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 20.sp,
            fontFamily = roboto_regular,
            fontSize = 15.sp
//            modifier = Modifier.height(40.dp)
        )
    }
}

fun getAnnouncements(): List<Announcements> {
    return listOf(
        Announcements(title = "Aviso 2", content = "Contenido 2"),
        Announcements(
            title = "Avisos",
            content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
        )
    )
}

data class Feature(
    val featureId: Int? = null,
    val featureName: String,
    val image: Int,
    val destination: String? = null
)

fun getFeatures(): List<Feature> {
    return listOf(
        Feature(
            featureName = "Calificaciones",
            image = R.drawable.icono_calificaciones,
            destination = Routes.GradesScreen.routes
        ),
        Feature(
            featureName = "Pagos y colegiaturas",
            image = R.drawable.icono_pagos_y_colegiaturas,
            destination = Routes.PaymentScreen.routes
        ),
        Feature(
            featureName = "Estado de cuenta",
            image = R.drawable.icono_estado_cuenta,
            destination = Routes.AccountBalanceScreen.routes
        ),
        Feature(featureName = "Boletín", image = R.drawable.icono_boletin),
        Feature(
            featureName = "Facturas",
            image = R.drawable.icono_facturas,
            destination = Routes.InvoiceScreen.routes
        ),
        Feature(
            featureName = "Horario",
            image = R.drawable.icono_horario,
            destination = Routes.ScheduleScreen.routes
        ),
        Feature(
            featureName = "Calendario",
            image = R.drawable.icono_calendario,
            destination = Routes.CalendarScreen.routes
        )

    )
}