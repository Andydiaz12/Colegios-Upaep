package com.upaep.colegios.view.base.navigation

import com.upaep.colegios.data.entities.announcements.Announcements
import com.upaep.colegios.data.entities.studentselector.StudentsSelector

sealed class Routes(val routes: String, val screenName: String = "") {
    object LoginScreen : Routes("loginScreen")
    object LoginExtraScreen : Routes("loginExtraScreen/{toScreen}") {
        fun createRoute(toScreen: String) = "loginExtraScreen/$toScreen"
    }

    object OnBoardScreen : Routes("onBoardScreen")
    object StudentSelectorScreen : Routes("studentSelectorScreen")
    object HomeScreen : Routes("homeScreen/{studentName}/{studentGrade}/{studentGroup}") {
        fun createRoute(student: StudentsSelector) =
            "homeScreen/${student.name}/${student.level}/${student.group}"
    }

    object AnnouncementScreen : Routes("announcementScreen/{title}/{content}") {
        fun createRoute(announcement: Announcements) =
            "announcementScreen/${announcement.title}/${announcement.content}"
    }
}
