package org.example.project.presentation.habitdetails.entities

data class HabitDetailsModel(
    val id: String,
    val title: String,
    val description: String,
    val streak: Int,
    val doneToday: Boolean,
    val completedDays: Int
)
