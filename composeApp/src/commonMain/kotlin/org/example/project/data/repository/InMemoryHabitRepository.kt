package org.example.project.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.domain.model.Habit
import org.example.project.domain.repository.HabitRepository

class InMemoryHabitRepository : HabitRepository {

    private val inMemoryHabits = MutableStateFlow(
        listOf(
            Habit(
                id = "1",
                title = "Reading",
                description = "30 minutes of Working Effectively with Legacy Code by Michael Feathers",
                streak = 4,
                doneToday = false,
                completedDays = 12
            ),
            Habit(
                id = "2",
                title = "CodeWars",
                description = "60 minutes of CodeWars Katas",
                streak = 7,
                doneToday = true,
                completedDays = 19
            ),
            Habit(
                id = "3",
                title = "Kotlin Theory Interview Prep",
                description = "30 minutes of reading IP&QD",
                streak = 2,
                doneToday = false,
                completedDays = 8
            ),
            Habit(
                id = "4",
                title = "Mock interview with GPT",
                description = "30 minutes of Mock interview with GPT",
                streak = 2,
                doneToday = false,
                completedDays = 8
            ),
            Habit(
                id = "5",
                title = "Brag Document",
                description = "30 minutes of refactoring & adding to Drag doc",
                streak = 2,
                doneToday = false,
                completedDays = 8
            )
        )
    )

    override fun observerHabits(): Flow<List<Habit>> = inMemoryHabits

    override suspend fun addHabit(title: String, description: String) {
        val nextId = (inMemoryHabits.value.maxOfOrNull { it.id.toInt() } ?: 0) + 1

        val newHabit = Habit(
            id = nextId.toString(),
            title = title,
            description = description,
            streak = 0,
            doneToday = false,
            completedDays = 0
        )

        inMemoryHabits.update { habits ->
            habits + newHabit
        }
    }

    override suspend fun getHabitById(id: String): Habit? {
        return inMemoryHabits.value.firstOrNull() { it.id == id }
    }

    override suspend fun toggleDoneToday(id: String) {
        inMemoryHabits.update { habits ->
            habits.map { habit ->
                if (habit.id == id) habit.toggleDoneToday() else habit
            }
        }
    }

    private fun Habit.toggleDoneToday(): Habit {
        val isDoneToday = !doneToday
        val updatedStreak = if (isDoneToday) {
            streak + 1
        } else {
            maxOf(0, streak - 1)
        }
        val updatedCompletedDays = if (isDoneToday) {
            completedDays + 1
        } else {
            maxOf(0, completedDays - 1)
        }
        return copy(
            doneToday = isDoneToday,
            streak = updatedStreak,
            completedDays = updatedCompletedDays
        )
    }
}