package fr.uge.myfittracker.ui.training

import Step
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.SeriesWithExercise
import fr.uge.myfittracker.data.model.Session
import fr.uge.myfittracker.data.model.SessionType
import fr.uge.myfittracker.data.model.SessionWithSeries
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

    // Current series being edited
    private val _currentSession = MutableStateFlow<Session?>(null)
    val currentSession: StateFlow<Session?> = _currentSession.asStateFlow()

    private val _currentPlan = MutableStateFlow<Plan?>(null)
    val currentPlan: StateFlow<Plan?> = _currentPlan.asStateFlow()

    // List of all SeriesWithExercise
    private val _exerciseSeries = MutableStateFlow<List<SeriesWithExercise>>(emptyList())
    val exerciseSeries: StateFlow<List<SeriesWithExercise>> = _exerciseSeries.asStateFlow()

    // List of all SessionWithSeries
    private val _sessionSeries = MutableStateFlow<List<SessionWithSeries>>(emptyList())
    val sessionSeries: StateFlow<List<SessionWithSeries>> = _sessionSeries.asStateFlow()

    fun setCurrentSession(sessionType: SessionType, repitition:Int){
        if (_currentSession.value == null){
            _currentSession.value = Session(type = sessionType, repetition = repitition)
        }
        else{
            _currentSession.value = _currentSession.value!!.copy(type = sessionType, repetition = repitition)
        }
    }
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
                clearCurrentSerie()
            }
        }
    }
    // Add a new SessionwithSeries to the list
    fun addSessionWithSeries() {
        viewModelScope.launch {
            val currentSess = _currentSession.value

            if (_exerciseSeries.value.isNotEmpty()) {
                val newItem = SessionWithSeries(
                    session = currentSess!!,
                    series = _exerciseSeries.value,
                )
                _sessionSeries.value = _sessionSeries.value + newItem
            }
            clearCurrentSession()
        }
    }



    // Clear current inputs
    fun clearCurrentSerie() {
        _currentExercise.value = null
        _currentSeries.value = null
    }
    // Clear current inputs
    fun clearCurrentSession() {
        _currentSession.value = null
        _exerciseSeries.value = emptyList()
    }
}