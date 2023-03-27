package com.upaep.colegios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.upaep.colegios.view.base.navigation.AppNavigation
import com.upaep.colegios.view.base.theme.ColegiosProductivoTheme
import com.upaep.colegios.view.base.theme.ThemeSchema
import com.upaep.colegios.viewmodel.base.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainActivityViewModel: MainActivityViewModel by viewModels()
            val theme by mainActivityViewModel.theme.observeAsState(mainActivityViewModel.getTheme())
            ColegiosProductivoTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(id = theme.backgroundImage),
                            contentScale = ContentScale.FillWidth
                        )
                ) {
                    AppNavigation(theme)
                }
            }
        }
    }
}