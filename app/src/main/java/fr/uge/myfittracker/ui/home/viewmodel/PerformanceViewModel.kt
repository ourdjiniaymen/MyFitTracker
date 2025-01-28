package fr.uge.myfittracker.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.uge.myfittracker.data.local.model.DailyPerformance
import fr.uge.myfittracker.data.local.model.TotalPerformance
import fr.uge.myfittracker.data.local.view.PerformanceView
import fr.uge.myfittracker.ui.home.getCurrentDate
import kotlinx.coroutines.launch

class PerformanceViewModel(private val view: PerformanceView) : ViewModel(){

    fun updatePerformance(newSteps: Int, newDistance: Float, newCalories: Float, newStars: Int) {
        val currentDate = getCurrentDate()
        viewModelScope.launch {
            val dailyPerformance = view.getDailyPerformanceByDate(currentDate)
            if (dailyPerformance == null) {
                view.insertDailyPerformance(
                    DailyPerformance(
                        date = currentDate,
                        steps = newSteps,
                        distance = newDistance,
                        calories = newCalories,
                        stars = newStars
                    )
                )
            } else {
                view.insertDailyPerformance(
                    dailyPerformance.copy(
                        steps = dailyPerformance.steps + newSteps,
                        distance = dailyPerformance.distance + newDistance,
                        calories = dailyPerformance.calories + newCalories,
                        stars = dailyPerformance.stars + newStars
                    )
                )
            }

            val totalPerformance = view.getTotalPerformance()
            if (totalPerformance == null) {
                view.insertTotalPerformance(
                    TotalPerformance(
                        totalSteps = newSteps,
                        totalDistance = newDistance,
                        totalCalories = newCalories,
                        totalStars = newStars
                    )
                )
            } else {
                view.updateTotalPerformance(
                    totalPerformance.copy(
                        totalSteps = totalPerformance.totalSteps + newSteps,
                        totalDistance = totalPerformance.totalDistance + newDistance,
                            totalCalories = totalPerformance.totalCalories + newCalories,
                        totalStars = totalPerformance.totalStars + newStars
                    )
                )
            }
        }
    }

}