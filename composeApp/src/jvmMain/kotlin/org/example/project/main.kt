package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.platform.PlatformActions

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Habit Tracker"
    ) {
        App(
            platformActions = PlatformActions()
        )
    }
}