package org.example.project.presentation.habitlist.entities

data class HabitModel(
    val id: String,
    val title: String,
    val description: String,
    val streak: Int,
    val doneToday: Boolean,
    val completedDays: Int
)