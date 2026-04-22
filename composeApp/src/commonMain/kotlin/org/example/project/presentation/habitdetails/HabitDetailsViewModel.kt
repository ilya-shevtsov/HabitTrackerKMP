package org.example.project.presentation.habitdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.Habit
import org.example.project.domain.usecase.GetHabitByIdUseCase
import org.example.project.domain.usecase.ToggleHabitDoneUseCase
import org.example.project.presentation.habitdetails.entities.HabitDetailsModel

class HabitDetailsViewModel(
    private val habitId: String,
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val toggleHabitDoneUseCase: ToggleHabitDoneUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HabitDetailsViewState>(HabitDetailsViewState.Loading)
    val uiState: StateFlow<HabitDetailsViewState> = _uiState.asStateFlow()

    private val _uiEvents = MutableSharedFlow<HabitDetailsEvent>()
    val uiEvents: SharedFlow<HabitDetailsEvent> = _uiEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            loadHabitState()
        }
    }

    fun handleAction(action: HabitDetailsAction) {
        when (action) {
            HabitDetailsAction.OnBackClick -> handleOnBackClick()
            HabitDetailsAction.OnToggleDone -> handleOnToggleDone()
        }
    }

    private fun handleOnBackClick() {
        viewModelScope.launch {
            _uiEvents.emit(HabitDetailsEvent.NavigateBack)
        }
    }

    private fun handleOnToggleDone() {
        viewModelScope.launch {
            toggleHabitDoneUseCase(habitId)
            loadHabitState()
        }
    }

    private suspend fun loadHabitState() {
        _uiState.value = HabitDetailsViewState.Loading

        runCatching {
            getHabitByIdUseCase(habitId)
        }.onSuccess { habit ->
            _uiState.value = if (habit != null) {
                HabitDetailsViewState.Content(
                    habit = habit.toDetailsModel()
                )
            } else {
                HabitDetailsViewState.Error
            }
        }.onFailure {
            _uiState.value = HabitDetailsViewState.Error
        }
    }
}

private fun Habit.toDetailsModel(): HabitDetailsModel {
    return HabitDetailsModel(
        id = id,
        title = title,
        description = description,
        streak = streak,
        doneToday = doneToday,
        completedDays = completedDays
    )
}