package org.example.project.presentation.navigation

sealed interface AppRoute {
    data object HabitList: AppRoute

    data class HabitDetails(val habitId:String): AppRoute
}