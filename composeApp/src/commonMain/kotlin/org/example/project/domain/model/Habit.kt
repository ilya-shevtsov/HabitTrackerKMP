package org.example.project.domain.model

data class Habit(
    val id:String,
    val title:String,
    val description: String,
    val streak: Int,
    val doneToday: Boolean,
    val completedDays: Int
)