package com.upaep.colegios.view.features.onboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.upaep.colegios.data.entities.onboard.OnBoardInfo
import com.upaep.colegios.view.base.theme.*
import com.upaep.colegios.viewmodel.features.onboard.OnBoardViewModel

@Preview(showSystemUi = true)
@Composable
fun TestOnBoarding() {
    OnBoardScreen(navigation = rememberNavController())
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnBoardScreen(
    navigation: NavHostController,
    onBoardViewModel: OnBoardViewModel = hiltViewModel()
) {
    val pageContent = onBoardViewModel.getScreensInfo()
    val actualPage by onBoardViewModel.actualPage.observeAsState(0)
    val scrollLeft by onBoardViewModel.scrollDirectionLeft.observeAsState(false)
    var isDragging by remember { mutableStateOf(false) }
    var posibleUpdate by rememberSaveable { mutableStateOf(true) }
    AnimatedContent(
        targetState = actualPage,
        modifier = Modifier.fillMaxSize(),
        transitionSpec = {
            slideIntoContainer(
                animationSpec = tween(durationMillis = 1000),
                towards = if(scrollLeft) AnimatedContentScope.SlideDirection.End else AnimatedContentScope.SlideDirection.Start
            ).with(
                slideOutOfContainer(
                    animationSpec = tween(durationMillis = 1000),
                    towards = if(scrollLeft) AnimatedContentScope.SlideDirection.End else AnimatedContentScope.SlideDirection.Start
                )
            )
        }
    ) { targetState ->
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                onBoardViewModel.setMiddleofComponent(coordinates.size.width / 2)
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        posibleUpdate = true
                        isDragging = true
                    },
                    onDragEnd = {
                        isDragging = false
                    }, onDrag = { change, dragAmount ->
                        val x = dragAmount.x
                        change.consume()
                        if (posibleUpdate) {
                            when {
                                x > 0 -> {
                                    onBoardViewModel.changeOnBoardingScreen(
                                        value = -1,
                                        navigation = navigation
                                    )
                                }
                                x < 0 -> {
                                    onBoardViewModel.changeOnBoardingScreen(
                                        value = 1,
                                        navigation = navigation
                                    )
                                }
                            }
                            posibleUpdate = false
                        }
                    }
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(onTap = { tap ->
                    if (!isDragging) {
                        onBoardViewModel.tapOnBoardingScreen(
                            tapX = tap.x,
                            navigation = navigation
                        )
                    }
                })
            }
        ) {
            val (content, image, footer, background) = createRefs()
            val backgroundBtnRef = createGuidelineFromTop(0.82f)
            val contentRef = createGuidelineFromTop(0.34f)
            val imageRefTop = createGuidelineFromTop(0.42f)
            val imageRefBtn = createGuidelineFromTop(0.68f)

            BackGround(modifier = Modifier.constrainAs(background) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(backgroundBtnRef)
                height = Dimension.fillToConstraints
            }, color = pageContent[actualPage].color)
            Content(content = pageContent[actualPage], modifier = Modifier.constrainAs(content) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(contentRef)
            })
            ImageContainer(
                image = pageContent[actualPage].image,
                modifier = Modifier.constrainAs(image) {
                    top.linkTo(imageRefTop)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(imageRefBtn)
                    height = Dimension.fillToConstraints
                })
            Footer(
                pages = pageContent.size,
                actualPage = actualPage,
                modifier = Modifier.constrainAs(footer) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                onSkip = {
                    onBoardViewModel.navigateToStudentSelector(navigation = navigation)
                }
            )
        }
    }
}

@Composable
fun BackGround(modifier: Modifier, color: Color) {
    Surface(
        color = color,
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(bottomStart = 300.dp, bottomEnd = 300.dp)
    ) {}
}

@Composable
fun ImageContainer(modifier: Modifier, image: Int) {
    Image(
        painter = painterResource(id = image),
        modifier = modifier.fillMaxSize(),
        contentDescription = "onboarding"
    )
}

@Composable
fun Content(content: OnBoardInfo, modifier: Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!content.title.isNullOrBlank()) {
            Text(
                text = content.title,
                color = Color.White,
                fontFamily = roboto_black,
                fontSize = 27.sp
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
        Text(
            text = content.text,
            color = Color.White,
            fontFamily = roboto_medium,
            textAlign = TextAlign.Center,
            lineHeight = 25.sp,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.size(25.dp))
        Divider(modifier = Modifier.width(18.dp), thickness = 2.dp, color = Color.White)
    }
}

@Composable
fun Footer(pages: Int, actualPage: Int, modifier: Modifier, onSkip: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp, start = 30.dp, end = 30.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            for (i in 0 until pages) {
                Dot(dotNumber = i, actualPage = actualPage)
            }
        }
        Text(
            text = "OMITIR",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { onSkip() },
            color = Upaep_grey
        )
    }
}

@Composable
fun Dot(dotNumber: Int, actualPage: Int) {
    Surface(
        modifier = Modifier.size(13.dp),
        shape = RoundedCornerShape(50.dp),
        color = if (dotNumber == actualPage) Upaep_red else Color.Transparent,
        border = BorderStroke(2.dp, if (dotNumber == actualPage) Upaep_red else Upaep_grey)
    ) {

    }
}