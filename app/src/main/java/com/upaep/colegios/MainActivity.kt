package com.upaep.colegios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.upaep.colegios.view.base.navigation.AppNavigation
import com.upaep.colegios.view.base.theme.ColegiosProductivoTheme
import com.upaep.colegios.viewmodel.Base.MainActivityViewModel
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = theme.backgroundColor
                ) {
                    AppNavigation(theme)
                }
            }
        }
    }
}