package fr.uge.myfittracker

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import fr.uge.myfittracker.data.local.AppDatabase
import fr.uge.myfittracker.data.model.Plan
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
        val plan = Plan(name = "plan")
        print("check one")
        repository.insertPlan(plan)
        print("check two")

        val retrieved = repository.getPlans()[0]// Implement in DAO
        //print(retrieved)
        assertEquals(plan.name, retrieved.plan.name)

    }
}