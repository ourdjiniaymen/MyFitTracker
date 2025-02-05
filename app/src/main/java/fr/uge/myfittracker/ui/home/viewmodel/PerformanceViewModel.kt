package fr.uge.myfittracker.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.uge.myfittracker.data.local.model.DailyPerformance
import fr.uge.myfittracker.data.local.model.TotalPerformance
import fr.uge.myfittracker.data.local.view.PerformanceView
import fr.uge.myfittracker.ui.home.getCurrentDate
import kotlinx.coroutines.launch

class PerformanceViewModel(private val view: PerformanceView) : ViewModel(){

    fun updatePerformance(newSteps: Int, newStars: Int, newLevel : Int) {
        val currentDate = getCurrentDate()
        viewModelScope.launch {
            val dailyPerformance = view.getDailyPerformanceByDate(currentDate)
            if (dailyPerformance == null) {
                view.insertDailyPerformance(
                    DailyPerformance(
                        date = currentDate,
                        steps = newSteps,
                        stars = newStars,
                        level = newLevel
                    )
                )
            } else {
                view.insertDailyPerformance(
                    dailyPerformance.copy(
                        steps = dailyPerformance.steps + newSteps,
                        stars = dailyPerformance.stars + newStars,
                        level = dailyPerformance.level + newLevel,
                    )
                )
            }

            val totalPerformance = view.getTotalPerformance()
            if (totalPerformance == null) {
                view.insertTotalPerformance(
                    TotalPerformance(
                        totalSteps = newSteps,
                        totalStars = newStars
                    )
                )
            } else {
                view.updateTotalPerformance(
                    totalPerformance.copy(
                        totalSteps = totalPerformance.totalSteps + newSteps,
                        totalStars = totalPerformance.totalStars + newStars
                    )
                )
            }
        }
    }

}