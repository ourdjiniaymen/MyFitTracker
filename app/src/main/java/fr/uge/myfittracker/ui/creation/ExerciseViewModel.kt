package fr.uge.myfittracker.ui.training

import Step
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.SeriesWithExercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SeriesWithExerciseViewModel : ViewModel() {
    // Liste des exercices enregistrés
    private val _currentExercise = MutableStateFlow<Exercise?>(null)
    val currentExercise: StateFlow<Exercise?> = _currentExercise.asStateFlow()

    // Current series being edited
    private val _currentSeries = MutableStateFlow<Series?>(null)
    val currentSeries: StateFlow<Series?> = _currentSeries.asStateFlow()

    // List of all SeriesWithExercise
    private val _exerciseSeries = MutableStateFlow<List<SeriesWithExercise>>(emptyList())
    val exerciseSeries: StateFlow<List<SeriesWithExercise>> = _exerciseSeries.asStateFlow()

    // Set current exercise
    fun setCurrentExercise(title: String, description: String) {
        _currentExercise.value =  Exercise(name = title, description = description)
    }

    // Set current series parameters
    fun setCurrentSeries(repetition: Int?, duration: Int?) {
        _currentSeries.value = Series(
            repetition = repetition,
            duration = duration
        )
    }

    // Add a new SeriesWithExercise to the list
    fun addSeriesWithExercise() {
        viewModelScope.launch {
            val currentEx = _currentExercise.value
            val currentSer = _currentSeries.value

            if (currentEx != null && currentSer != null) {
                val newItem = SeriesWithExercise(
                    series = currentSer,
                    exercise = currentEx
                )
                _exerciseSeries.value = _exerciseSeries.value + newItem
            }
        }
    }

    // Save a new exercise
    fun saveExercise(title: String, description: String) {
        viewModelScope.launch {
            _currentExercise.value = Exercise(
                name = title,
                description = description
            )
        }
    }

    // Clear current inputs
    fun clearCurrent() {
        _currentExercise.value = null
        _currentSeries.value = null
    }
}