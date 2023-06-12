package com.upaep.colegios.view.base.navigation

import com.upaep.colegios.model.entities.announcements.Announcements

sealed class Routes(val routes: String, val screenName: String = "") {
    object GradesScreen : Routes("gradesScreen")
    object SplashScreen : Routes("splashScreen")
    object LoginScreen : Routes("loginScreen")
    object LoginExtraScreen : Routes("loginExtraScreen/{toScreen}") {
        fun createRoute(toScreen: String) = "loginExtraScreen/$toScreen"
    }

    object OnBoardingScreen : Routes("onBoardScreen")
    object StudentSelectorScreen : Routes("studentSelectorScreen")
    object HomeScreen : Routes("homeScreen")
    object AnnouncementScreen : Routes("announcementScreen/{title}/{content}") {
        fun createRoute(announcement: Announcements) =
            "announcementScreen/${announcement.title}/${announcement.content}"
    }

    object MenuScreen : Routes("menuScreen")
    object CalendarScreen : Routes("calendarScreen")
    object ScheduleScreen : Routes("scheduleScreen")
    object PaymentScreen : Routes("paymentsScreen")
    object AdvanceTuition : Routes("advanceTuitionScreen")
    object PaymentMethodScreen: Routes("paymentMethodScreen")
}
