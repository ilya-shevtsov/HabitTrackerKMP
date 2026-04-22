package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.example.project.di.appModule
import org.example.project.presentation.habitdetails.HabitDetailsRoute
import org.example.project.presentation.habitlist.HabitListRoute
import org.example.project.presentation.navigation.AppRoute
import org.koin.compose.KoinApplication


@Composable
fun App() {
    KoinApplication(
        application = {
            modules(appModule)
        }
    ) {
        MaterialTheme {
            var currentRoute by remember { mutableStateOf<AppRoute>(AppRoute.HabitList) }

            when (val route = currentRoute) {
                AppRoute.HabitList -> {
                    HabitListRoute(
                        onOpenDetails = { id ->
                            currentRoute = AppRoute.HabitDetails(id)
                        }
                    )
                }

                is AppRoute.HabitDetails -> {
                    HabitDetailsRoute(
                        habitId = route.habitId,
                        onBack = {
                            currentRoute = AppRoute.HabitList
                        }
                    )
                }
            }
        }
    }
}