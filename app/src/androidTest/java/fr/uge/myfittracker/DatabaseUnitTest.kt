package fr.uge.myfittracker

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import fr.uge.myfittracker.data.local.AppDatabase
import fr.uge.myfittracker.data.model.Exercise
import fr.uge.myfittracker.data.model.Plan
import fr.uge.myfittracker.data.model.PlanWithSessions
import fr.uge.myfittracker.data.model.Series
import fr.uge.myfittracker.data.model.SeriesWithExercise
import fr.uge.myfittracker.data.model.Session
import fr.uge.myfittracker.data.model.SessionType
import fr.uge.myfittracker.data.model.SessionWithSeries
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
    fun insertPlan() = runTest {
        // Example Exercise objects
        val pushUps = Exercise(name = "Push-up", description = "Push-up exercise description")
        val squats = Exercise(name = "Squat", description = "Squat exercise description")
        val rest = Exercise(name = "Rest", description = "Rest")

        // Example SeriesWithExercise objects
        val series1 = SeriesWithExercise(
            series = Series(
                duration = null,
                repetition = 15
            ),
            exercise = pushUps
        )
        val series2 = SeriesWithExercise(
            series = Series(
                duration = 30,
                repetition = null
            ),
            exercise = rest
        )

        val series3 = SeriesWithExercise(
            series = Series(
                duration = null,
                repetition = 15
            ),
            exercise = squats
        )


        // Example SessionWithSeries object
        val session1 = SessionWithSeries(
            session = Session(type = SessionType.FULL_BODY, repetition = 3),
            series = listOf(series1, series2, series3)
        )

        // Example PlanWithSessions object
        val planWithSessions = PlanWithSessions(
            plan = Plan(
                name = "Beginner Workout Plan",
                description = "A simple beginner workout plan."
            ),
            sessions = listOf(session1)  // Here, we are associating session1 with the plan
        )

        val planId = repository.insertPlanWithSessions(planWithSessions);
        assertEquals(
            repository.getAllPlans().first().plan.id,
            planId
        )
    }
}