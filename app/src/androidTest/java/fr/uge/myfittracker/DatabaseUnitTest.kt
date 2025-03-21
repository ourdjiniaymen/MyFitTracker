package fr.uge.myfittracker

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import fr.uge.myfittracker.data.local.AppDatabase
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.ExerciseType
import fr.uge.myfittracker.data.model.ExerciseWithSeries
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.SeriesWithStep
import fr.uge.myfittracker.data.model.Step
import fr.uge.myfittracker.data.model.StepType
import fr.uge.myfittracker.data.repository.Repository
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest


class DatabaseUnitTest {

    private lateinit var database: AppDatabase
    private lateinit var repository: Repository
    private lateinit var app: Application

    @Before
    fun setup() {
        app = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(app, AppDatabase::class.java)
            .allowMainThreadQueries() // Only for testing
            .build()

        repository = Repository(app)
    }

    @After
    fun close() {
        database.close();
    }

    @Test
    fun insertPlanWithExercises_savesCorrectly() = runTest {
        val exerciseWithSeries = ExerciseWithSeries(
            exercise = Exercise(name = "exercise test", description = null, type = ExerciseType.CARDIO),
            series = listOf(
                SeriesWithStep(
                    step = Step(
                        title = "title",
                        description = null,
                        type = StepType.LUNG,
                        duration = 30,
                        count = null

                    ), series = Series(
                        exerciseId = 0, stepId = 0, stepRepetition = 2, repetitionInExercise = 3
                    )
                )
            )
        )

        val plan = Plan(
            name = "plan",
            description = null
        );
        val id =
            repository.insertPlanWithExercises(plan = plan, exercises = listOf(exerciseWithSeries))
        val x = repository.getPlanExercisesWithSeries(id);
        assertEquals(
            repository.getPlanExercisesWithSeries(id).first().exercise.name,
            "exercise test"
        )
    }
}