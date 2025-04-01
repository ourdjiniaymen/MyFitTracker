package fr.uge.myfittracker.data.repository

import android.app.Application
import androidx.room.Transaction
import fr.uge.myfittracker.data.local.AppDatabase
import fr.uge.myfittracker.data.local.dao.ExerciseDao
import fr.uge.myfittracker.data.local.dao.PlanDao
import fr.uge.myfittracker.data.local.dao.SeriesDao
import fr.uge.myfittracker.data.local.dao.SessionDao
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.PlanWithSessions
import fr.uge.myfittracker.data.model.SeriesWithExercise
import fr.uge.myfittracker.data.model.Session
import fr.uge.myfittracker.data.model.SessionWithSeries


class Repository(
    application: Application
) {
    private val exerciseDao: ExerciseDao;
    private val seriesDao: SeriesDao;
    private val sessionDao: SessionDao;
    private val planDao: PlanDao;

    init {
        val database = AppDatabase.getDatabase(application)
        sessionDao = database.sessionDao()
        seriesDao = database.seriesDao()
        exerciseDao = database.exerciseDao()
        planDao = database.planDao()
    }

    suspend fun getAllPlans(): List<PlanWithSessions> {
        return planDao.getAllPlans()
    }
    suspend fun getAllPlansWithoutSessions(): List<Plan> {
            return planDao.getAllPlansWithoutSessions()
        }

    suspend fun getPlanSessions(planId: Long): List<SessionWithSeries> {
        return sessionDao.getSessionFromPlanId(planId);
    }

    @Transaction
    suspend fun insertPlanWithSessions(planWithSessions: PlanWithSessions): Long {
        val planId = planDao.insertPlan(planWithSessions.plan);
        planWithSessions.sessions.forEach { sessionWithSeries ->
            insertSessionWithSeries(sessionWithSeries, planId)
        }
        return planId
    }

    @Transaction
    suspend fun insertSessionWithSeries(sessionWithSeries: SessionWithSeries, planId: Long) {
        val sessionId = sessionDao.insertSession(sessionWithSeries.session.copy(planId = planId))
        sessionWithSeries.series.forEach { seriesWithExercise ->
            insertSeriesWithExercise(seriesWithExercise, sessionId)
        }
    }

    @Transaction
    suspend fun insertSeriesWithExercise(seriesWithExercise: SeriesWithExercise, sessionId: Long) {
        val exerciseId = exerciseDao.insertExercise(seriesWithExercise.exercise);
        seriesDao.insertSeries(
            seriesWithExercise.series.copy(
                exerciseId = exerciseId, sessionId = sessionId
            )
        );

    }
}