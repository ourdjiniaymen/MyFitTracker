package fr.uge.myfittracker.data.local.view

import fr.uge.myfittracker.data.local.database.DailyPerformanceDao
import fr.uge.myfittracker.data.local.model.DailyPerformance

class PerformanceView (
    private val dailyDao: DailyPerformanceDao,
    //private val totalDao: TotalPerformanceDao
) {
    suspend fun insertDailyPerformance(performance: DailyPerformance) {
        dailyDao.insertDailyPerformance(performance)
    }

    suspend fun getDailyPerformanceByDate(date: String): DailyPerformance? {
        return dailyDao.getDailyPerformanceByDate(date)
    }

    suspend fun getAllDailyPerformances(): List<DailyPerformance> {
        return dailyDao.getAllDailyPerformances()
    }

    suspend fun updateDailyPerformance(performance: DailyPerformance) {
        dailyDao.updateDailyPerformance(performance)
    }

//    suspend fun insertTotalPerformance(performance: TotalPerformance) {
//        totalDao.insertTotalPerformance(performance)
//    }
//
//    suspend fun getTotalPerformance(): TotalPerformance? {
//        return totalDao.getTotalPerformance()
//    }
//
//    suspend fun updateTotalPerformance(performance: TotalPerformance) {
//        totalDao.updateTotalPerformance(performance)
//    }
}