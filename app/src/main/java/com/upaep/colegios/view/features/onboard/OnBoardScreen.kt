package com.upaep.colegios.view.features.onboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.colegios.data.entities.onboard.OnBoardInfo
import com.upaep.colegios.view.base.theme.onBoard_footer
import com.upaep.colegios.view.base.theme.roboto_black
import com.upaep.colegios.view.base.theme.roboto_medium
import com.upaep.colegios.view.base.theme.roboto_regular
import com.upaep.colegios.viewmodel.features.onboard.OnBoardViewModel

@Composable
fun OnBoardScreen(onBoardViewModel: OnBoardViewModel = hiltViewModel()) {
    val actualScreen by onBoardViewModel.actualScreen.observeAsState(initial = 1)
    val screenContent = onBoardViewModel.screensContent
    ConstraintLayout() {
        val backgroundLimit = createGuidelineFromBottom(0.17F)
        val background = createRef()
        BackgroundImage(modifier = Modifier.constrainAs(background) {
            bottom.linkTo(backgroundLimit)
        })
        ScreenContent(
            onBoardViewModel = onBoardViewModel,
            actualScreen = actualScreen,
            screenContent = screenContent
        )
    }
}

@Composable
fun ScreenContent(
    onBoardViewModel: OnBoardViewModel,
    actualScreen: Int,
    screenContent: List<OnBoardInfo>
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .onGloballyPositioned { coordinates ->
                onBoardViewModel.updateConstraintWidth(coordinates.size.width.toFloat())
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val x = dragAmount.x
                        when {
                            x > 0 -> {
                                onBoardViewModel.updateRightSwipe(false)
                            } // right
                            x < 0 -> {
                                onBoardViewModel.updateRightSwipe(true)
                            } // left
                        }
                    },
                    onDragEnd = {
                        onBoardViewModel.navigateOnBoard()
                    }
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {},
                    onTap = { tapCoordenate ->
                        onBoardViewModel.tappingEvent(tapCoordenate.x)
                    }
                )
            }
    ) {
        val (textRef, imageRef, footerRef) = createRefs()
        val middleGuideline = createGuidelineFromBottom(0.5f)
        TextContainer(modifier = Modifier.constrainAs(textRef) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(middleGuideline)
        }, screenContent = screenContent[actualScreen - 1])
        ImageContainer(modifier = Modifier.constrainAs(imageRef) {

        })
        FooterContainer(actualScreen, modifier = Modifier.constrainAs(footerRef) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
    }
}

@Composable
fun BackgroundImage(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(0.dp, 0.dp, 250.dp, 250.dp))
            .background(Color(0xFF6BB714).copy(alpha = 1f))
    )
}

@Composable
fun ImageContainer(modifier: Modifier) {

}

@Composable
fun TextContainer(modifier: Modifier, screenContent: OnBoardInfo) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (!screenContent.title.isNullOrBlank()) {
            Text(
                text = screenContent.title,
                textAlign = TextAlign.Center,
                fontFamily = roboto_black,
                fontSize = 22.sp,
                letterSpacing = 0.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.size(12.dp))
        }
        Text(
            text = screenContent.text,
            textAlign = TextAlign.Center,
            fontFamily = roboto_medium,
            fontSize = 17.sp,
            letterSpacing = 0.sp,
            color = Color.White,
            lineHeight = 27.sp
        )
        Spacer(modifier = Modifier.size(40.dp))
        Divider(color = Color.White, thickness = 2.dp, modifier = Modifier.width(22.dp))
        Spacer(modifier = Modifier.size(80.dp))
    }
}

@Composable
fun FooterContainer(activeScreen: Int, modifier: Modifier) {
    Row(modifier = modifier) {
        Spacer(modifier = Modifier.weight(1f))
        DotsContainer(modifier = Modifier.weight(1f), active = activeScreen)
        Skip(modifier = Modifier.weight(1f))
    }
}

@Composable
fun Skip(modifier: Modifier) {
    Box(modifier = modifier) {
        Text(
            "OMITIR",
            modifier = Modifier.align(Alignment.BottomEnd),
            fontFamily = roboto_regular,
            color = onBoard_footer
        )
    }
}

@Composable
fun DotsContainer(modifier: Modifier, active: Int) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Dot(active = active, number = 1)
            Dot(active = active, number = 2)
            Dot(active = active, number = 3)
        }
    }
}

@Composable
fun Dot(number: Int, active: Int) {
    Card(
        shape = RoundedCornerShape(percent = 50),
        modifier = Modifier.size(15.dp),
        border = BorderStroke(
            width = 2.dp,
            if (number == active) Color(0xFFE30921) else onBoard_footer
        ),
        backgroundColor = if (number == active) Color(0xFFE30921) else Color.Transparent,
        content = {}
    )
}
