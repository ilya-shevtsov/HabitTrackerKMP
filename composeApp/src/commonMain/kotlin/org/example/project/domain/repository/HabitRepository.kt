package org.example.project.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.model.Habit

interface HabitRepository {

    fun observerHabits(): Flow<List<Habit>>

    suspend fun addHabit(title: String, description: String)

    suspend fun getHabitById(id: String): Habit?

    suspend fun toggleDoneToday(id: String)

}