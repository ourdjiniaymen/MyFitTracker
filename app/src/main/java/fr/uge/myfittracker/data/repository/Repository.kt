package fr.uge.myfittracker.data.repository

import android.app.Application
import fr.uge.myfittracker.data.local.AppDatabase
import fr.uge.myfittracker.data.local.dao.ExerciseDao
import fr.uge.myfittracker.data.local.dao.PlanDao
import fr.uge.myfittracker.data.local.dao.SeriesDao
import fr.uge.myfittracker.data.local.dao.StepDao
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.ExerciseType
import fr.uge.myfittracker.data.model.ExerciseWithSeries
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.PlanExerciseCrossRef
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.SeriesWithStep
import fr.uge.myfittracker.data.model.Step


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

    suspend fun insertExerciseWithSeries(exerciseWithSeries: ExerciseWithSeries): Long {
        val exerciseId = exerciseDao.insertExercise(exerciseWithSeries.exercise)
        exerciseWithSeries.series.forEach { seriesWithStep ->
            val stepId = stepDao.insertStep(seriesWithStep.step);
            seriesDao.insertSeries(
                seriesWithStep.series.copy(
                    stepId = stepId, exerciseId = exerciseId
                )
            )
        }
        return exerciseId
    }

    suspend fun insertPlanWithExercises(
        plan: Plan, exercises: List<ExerciseWithSeries>
    ): Long {
        val planId = planDao.insertPlan(plan);
        exercises.forEach { exerciseWithSeries ->
            val exerciseId = insertExerciseWithSeries(exerciseWithSeries)
            planDao.insertPlanExerciseCrossRef(
                PlanExerciseCrossRef(
                    planId = planId, exerciseId = exerciseId
                )
            )
        }
        return planId
    }

    suspend fun getAllPlans(): List<Plan> {
        return planDao.getAllPlans()
    }

    suspend fun getPlanExercises(planId: Long): List<Exercise> {
        return exerciseDao.getExercisesFromPlanId(planId)
    }

    suspend fun getPlanExercisesWithSeries(planId: Long): List<ExerciseWithSeries> {
        return getPlanExercises(planId).map { exercise: Exercise ->
            ExerciseWithSeries(
                exercise, seriesDao.getListSeriesFromExerciseId(exercise.id)
            )
        }
    }


}