package com.upaep.colegios.view.base.navigation

sealed class Routes(val routes: String, val screenName: String = "") {
    object LoginScreen: Routes("loginScreen")
    object LoginExtraScreen: Routes("LoginExtraScreen/{toScreen}") {
        fun createRoute(toScreen: String) = "LoginExtraScreen/$toScreen"
    }
}
