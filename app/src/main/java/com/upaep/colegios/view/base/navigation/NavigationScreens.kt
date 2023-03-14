package com.upaep.colegios.view.base.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.upaep.colegios.view.base.theme.ThemeSchema
import com.upaep.colegios.view.features.login.LoginExtraScreen
import com.upaep.colegios.view.features.login.LoginScreen

@Composable
fun AppNavigation(theme: ThemeSchema) {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = Routes.LoginScreen.routes) {
        composable(Routes.LoginScreen.routes) {
            LoginScreen(theme = theme, navigation = navigationController)
        }
        composable(
            Routes.LoginExtraScreen.routes,
            arguments = listOf(navArgument("toScreen") { type = NavType.StringType })
        ) { backStackEntry ->
            LoginExtraScreen(
                theme = theme,
                navigation = navigationController,
                toScreen = backStackEntry.arguments!!.getString("toScreen").toString()
            )
            //LoginExtraScreen()
        }
    }
}