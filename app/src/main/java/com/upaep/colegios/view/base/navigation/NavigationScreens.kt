package com.upaep.colegios.view.base.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.upaep.colegios.view.base.theme.ThemeSchema
import com.upaep.colegios.view.features.announcements.AnnouncementsScreen
import com.upaep.colegios.view.features.login.LoginExtraScreen
import com.upaep.colegios.view.features.login.LoginScreen
import com.upaep.colegios.view.features.onboard.OnBoardScreen
import com.upaep.colegios.view.features.studentselector.StudentSelectorScreen
import com.upaep.colegios.viewmodel.features.home.HomeScreen

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
        }
        composable(Routes.OnBoardScreen.routes) {
            OnBoardScreen(navigation = navigationController)
        }
        composable(Routes.StudentSelectorScreen.routes) {
            StudentSelectorScreen(navigation = navigationController)
        }
        composable(Routes.HomeScreen.routes, arguments = listOf(
            navArgument("studentName") { type = NavType.StringType },
            navArgument("studentGrade") { type = NavType.StringType },
            navArgument("studentGroup") { type = NavType.StringType }
        )) { backStackEntry ->
            HomeScreen(
                studentGrade = backStackEntry.arguments!!.getString("studentGrade").toString(),
                studentGroup = backStackEntry.arguments!!.getString("studentGroup").toString(),
                studentName = backStackEntry.arguments!!.getString("studentName").toString(),
                theme = theme,
                navigation = navigationController
            )
        }
        composable(Routes.AnnouncementScreen.routes, arguments = listOf(
            navArgument("title") { type = NavType.StringType },
            navArgument("content") { type = NavType.StringType }
        )) { backStackEntry ->
            AnnouncementsScreen(
                title = backStackEntry.arguments?.getString("title").toString(),
                content = backStackEntry.arguments?.getString("content").toString(),
                navigation = navigationController,
                theme = theme
            )
        }
    }
}