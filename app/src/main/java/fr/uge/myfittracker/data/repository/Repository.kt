package fr.uge.myfittracker.data.repository

import android.app.Application
import fr.uge.myfittracker.data.local.AppDatabase
import fr.uge.myfittracker.data.local.dao.ExerciseDao
import fr.uge.myfittracker.data.local.dao.PlanDao
import fr.uge.myfittracker.data.local.dao.SeriesDao
import fr.uge.myfittracker.data.local.dao.StepDao
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.ExerciseWithSeries
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.PlanWithExercises
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.SeriesWithSteps
import fr.uge.myfittracker.data.model.Step
import kotlinx.coroutines.flow.Flow


class Repository(
    application: Application
) {
    private val stepDao: StepDao;
    private val seriesDao: SeriesDao;
    private val exerciseDao: ExerciseDao;
    private val planDao: PlanDao;

    init {
        val database = AppDatabase.getDatabase(application)
        stepDao = database.stepDao()
        seriesDao = database.seriesDao()
        exerciseDao = database.exerciseDao()
        planDao = database.planDao()
    }

    suspend fun insertPlan(plan: Plan) = planDao.insertPlan(plan)
    suspend fun getPlans(): List<PlanWithExercises> =
        planDao.getAllPlans()

    /** --- Exercise Methods --- **/
    suspend fun insertExercise(exercise: Exercise) = exerciseDao.insertExercise(exercise)


    /** --- Series Methods --- **/
    suspend fun insertSeries(series: Series) = seriesDao.insertSeries(series)


    /** --- Step Methods --- **/
    suspend fun insertStep(step: Step) = stepDao.insertStep(step)
}