package com.upaep.colegios.view.features.onboarding

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import com.upaep.colegios.model.entities.onboard.OnBoardInfo
import com.upaep.colegios.view.base.navigation.Routes
import com.upaep.colegios.view.base.theme.*
import com.upaep.colegios.viewmodel.features.onboarding.OnBoardingViewModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    onBoardViewModel: OnBoardingViewModel = hiltViewModel(),
    navigation: NavHostController
) {

    val pageContent = onBoardViewModel.getScreensInfo()
    val pagerState = rememberPagerState()
    var controlScroll by rememberSaveable { mutableStateOf(false) }

    ConstraintLayout() {
        val (pager, footer) = createRefs()
        HorizontalPager(
            pageCount = pageContent.size,
            state = pagerState,
            modifier = Modifier
                .constrainAs(pager) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(footer.top)
                }
        ) { index ->
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val (content, image, background) = createRefs()
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
                }, color = pageContent[index].color)
                Content(content = pageContent[index], modifier = Modifier.constrainAs(content) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(contentRef)
                })
                ImageContainer(
                    image = pageContent[index].image,
                    modifier = Modifier.constrainAs(image) {
                        top.linkTo(imageRefTop)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(imageRefBtn)
                        height = Dimension.fillToConstraints
                    })
            }
        }
        Footer(
            modifier = Modifier.constrainAs(footer) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onSkip = { onBoardViewModel.navigateToStudentSelector(navigation) },
            pageContentSize = pageContent.size,
            pagerState = pagerState
        )
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.isScrollInProgress }.collect { scrolling ->
            if (!scrolling) {
                if (!controlScroll && (pagerState.currentPage == pageContent.size - 1)) {
                    onBoardViewModel.navigateToStudentSelector(navigation)
                    //navigation.navigate(Routes.StudentSelectorScreen.routes)
                }
                controlScroll = false
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPageOffsetFraction }.collect { page ->
            controlScroll = true
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Footer(modifier: Modifier, onSkip: () -> Unit, pageContentSize: Int, pagerState: PagerState) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp, start = 30.dp, end = 30.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            repeat(pageContentSize) { iteration ->
                Dot(pagerState.currentPage == iteration)
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
fun Dot(activeDot: Boolean) {
    Surface(
        modifier = Modifier.size(13.dp),
        shape = RoundedCornerShape(50.dp),
        color = if (activeDot) Upaep_red else Color.Transparent,
        border = BorderStroke(2.dp, if (activeDot) Upaep_red else Upaep_grey)
    ) {

    }
}