package fr.uge.myfittracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import fr.uge.myfittracker.data.model.Step

@Dao
interface StepDao {

    @Insert
    suspend fun insertStep(step: Step): Long

}