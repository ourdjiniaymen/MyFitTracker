package fr.uge.myfittracker.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel: ViewModel() {

    private val _dailyStepsGoal = MutableStateFlow(6000)
    val dailyStepsGoal: StateFlow<Int> = _dailyStepsGoal

    //Daily perfermances
    private val _dailySteps = MutableStateFlow(0)
    val dailySteps: StateFlow<Int> = _dailySteps

    private val _dailyValidatedSteps = MutableStateFlow(0)
    val dailyValidatedSteps: StateFlow<Int> = _dailyValidatedSteps

    private val _dailyDistance = MutableStateFlow(0.0)
    val dailyDistance: StateFlow<Double> = _dailyDistance

    private val _dailyCalories = MutableStateFlow(0.0)
    val dailyCalories: StateFlow<Double> = _dailyCalories

    private val _dailyStars = MutableStateFlow(0)
    val dailyStars: StateFlow<Int> = _dailyStars

    private val _dailyLevel = MutableStateFlow(0)
    val dailyLevel: StateFlow<Int> = _dailyLevel

    //Total perfermances
    private val _totalSteps = MutableStateFlow(0)
    val totalSteps: StateFlow<Int> = _totalSteps

    private val _totalDistance = MutableStateFlow(0.0)
    val totalDistance: StateFlow<Double> = _totalDistance

    private val _totalCalories = MutableStateFlow(0.0)
    val totalCalories: StateFlow<Double> = _totalCalories

    private val _totalStars = MutableStateFlow(0)
    val totalStars: StateFlow<Int> = _totalStars

    fun incrementSteps(steps: Int) {
        _dailySteps.value += steps
        _totalSteps.value += steps
    }

    fun incrementStars(stars: Int) {
        _dailyStars.value += stars
        _totalStars.value += stars
    }

    fun incrementLevel(level: Int) {
        _dailyLevel.value += level
    }

    // Daily reset (to call at midnight)
    fun resetDailyPerformance() {
        _dailySteps.value = 0
        _dailyValidatedSteps.value = 0
        _dailyDistance.value = 0.0
        _dailyCalories.value = 0.0
        _dailyStars.value = 0
        _dailyLevel.value = 0
    }

    // Standard calcul of distance and calories
    fun calculateDistance(steps: Int): Double {
        val strideLengthInMeters = 0.78 // Average step length in meters
        return (steps * strideLengthInMeters) / 1000 // Convert to kilometers
    }

    fun calculateCalories(steps: Int): Double {
        val caloriesPerStep = 0.05 // Calories burned per step
        return steps * caloriesPerStep
    }

    fun convertStepsToPercentage(currentSteps: Int, goalSteps: Int): Int {
        if (goalSteps == 0) return 0
        val percentage = (currentSteps * 100) / goalSteps
        return if (percentage > 100) 100 else percentage
    }

}