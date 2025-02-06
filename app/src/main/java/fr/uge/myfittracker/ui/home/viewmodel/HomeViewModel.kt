package fr.uge.myfittracker.ui.home.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.uge.myfittracker.data.local.AppDatabase
import fr.uge.myfittracker.data.local.model.DailyPerformance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//class HomeViewModel(context: Context): ViewModel() {
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    //private val stepCounterViewModel: StepCounterViewModel = StepCounterViewModel(context)
    private val stepCounterViewModel: StepCounterViewModel = StepCounterViewModel(application)

    //added code for room----------------------------------------------------------------
    //private val database = AppDatabase.getDatabase(context.applicationContext)
    private val database = AppDatabase.getDatabase(application.applicationContext)
    private val dailyDao = database.dailyPerformanceDao()

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun saveDailySteps(steps: Int, stars: Int, levels: Int) {
        viewModelScope.launch {
            val currentDate = getCurrentDate()
            val existingEntry = dailyDao.getDailyPerformanceByDate(currentDate)
            val newEntry = DailyPerformance(
                id = existingEntry?.id ?: 0,
                date = currentDate,
                steps = steps,
                stars = stars,
                level = levels
            )

            dailyDao.insertDailyPerformance(newEntry)
            Log.d("HomeViewModel - saveDailySteps", "Updated DailyPerformance for date $currentDate with $steps steps, $stars stars, $levels levels.")
        }
    }

    private fun loadSteps() {
        viewModelScope.launch {
            val currentDate = getCurrentDate()
            val todayPerformance = dailyDao.getDailyPerformanceByDate(currentDate)

            val todaySteps = todayPerformance?.steps ?: 0
            val todayStars = todayPerformance?.stars ?: 0
            val todayLevel = todayPerformance?.level ?: 0
            val total = dailyDao.getTotalSteps() ?: 0
            val totalStars = dailyDao.getTotalStars() ?: 0

            _dailySteps.value = todaySteps
            _dailyStars.value = todayStars
            _dailyLevel.value = todayLevel
            _totalSteps.value = total
            _totalStars.value = totalStars

            // Met à jour StepCounterViewModel avec les pas sauvegardés
            stepCounterViewModel.updateSteps(todaySteps)

            Log.d("HomeViewModel - loadSteps", "Loaded steps for today ($currentDate): $todaySteps")
            Log.d("HomeViewModel - loadSteps", "Loaded stars for today ($currentDate): $todayStars")
            Log.d("HomeViewModel - loadSteps", "Loaded level for today ($currentDate): $todayLevel")
            Log.d("HomeViewModel - loadSteps", "Total steps loaded: $total")
            Log.d("HomeViewModel - loadSteps", "Total stars loaded: $totalStars")

        }
    }

    fun resetDailyPerformance() {
        viewModelScope.launch {
            val currentDate = getCurrentDate()
            dailyDao.insertDailyPerformance(DailyPerformance(date = currentDate, steps = 0, stars = 0, level = 0))
            _dailySteps.value = 0
            _dailyStars.value = 0
            _dailyLevel.value = 0
        }
    }
    //------------------------------------------------------------------------------------------

    //private val _dailyStepsGoal = MutableStateFlow(6000)
    private val _dailyStepsGoal = MutableStateFlow(200) // just for test
    val dailyStepsGoal: StateFlow<Int> = _dailyStepsGoal

    //Daily perfermances
    private val _dailySteps = MutableStateFlow(0)
    val dailySteps: StateFlow<Int> = _dailySteps

    private val _dailyStars = MutableStateFlow(0)
    val dailyStars: StateFlow<Int> = _dailyStars

    private val _dailyLevel = MutableStateFlow(0)
    val dailyLevel: StateFlow<Int> = _dailyLevel

    //Total perfermances
    private val _totalSteps = MutableStateFlow(0)
    val totalSteps: StateFlow<Int> = _totalSteps

    private val _totalStars = MutableStateFlow(0)
    val totalStars: StateFlow<Int> = _totalStars


    init {
        viewModelScope.launch {
            stepCounterViewModel.stepCount.collect { steps ->
                _dailySteps.value = steps
                saveDailySteps(steps, _dailyStars.value, _dailyLevel.value)
                Log.d("HomeViewModel - init", "Steps updated: $steps")
            }
        }

        loadSteps()
    }

    fun incrementSteps(steps: Int) {
        _dailySteps.value += steps
        _totalSteps.value += steps
    }

    fun incrementStars(stars: Int) {
        _dailyStars.value += stars
        _totalStars.value += stars
        saveDailySteps(_dailySteps.value, _dailyStars.value, _dailyLevel.value)
    }

    fun incrementLevel(level: Int) {
        _dailyLevel.value += level
        saveDailySteps(_dailySteps.value, _dailyStars.value, _dailyLevel.value)
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