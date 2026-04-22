package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.example.project.di.appModule
import org.example.project.presentation.habitlist.HabitListRoute
import org.koin.compose.KoinApplication


@Composable
fun App() {
    KoinApplication(
        application = {
            modules(appModule)
        }
    ) {
        MaterialTheme {
            HabitListRoute(
                onOpenDetails = {

                }
            )
        }
    }
}